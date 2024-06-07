# YOLO related libraries
from ultralytics import YOLO
import cv2
import torch
import subprocess
import zmq
import base64
import numpy as np

# Data collection related libraries
import csv
import time
import os
from python_tsl2591 import tsl2591
import datetime

# Threading related libraries
import asyncio
import threading

# Custom libraries
import log
from alarm.jetson_ble import connect_to_device, message_thread
from image import clear_images, count_jpgs, save_img, OUTPUT_PATH
from ir_led import set_leds_mode, activate_leds, set_leds_state, OUT, HIGH, LOW
from log import web_logs
from conditions import Weather, Location, Mode

# Flask related libraries
from flask import Flask, request, redirect, url_for, render_template, make_response, jsonify, Response, \
    send_from_directory
from flask_login import LoginManager, UserMixin, login_user, logout_user, login_required
from werkzeug.security import generate_password_hash, check_password_hash
import secrets

# -------------Configuration--------------------
# Path to the model (e.g., yolov8n.pt, yolov8x.pt)
# YOLO() function defaults to yolov8n.pt, and downloads it automatically.
# However, use this path for other models.
MODEL_PATH = "models/yolov8n.pt"

# How long should we detect people for? (in minutes)
MINUTES = 10

# Converted to seconds.
RUNTIME = MINUTES * 60

# Camera location, and weather conditions. These are its default values if none are selected in the flask server.
location, weather = Location("Indoor"), Weather("Sunny")

# Data collection mode
mode = None

# Flag indicating whether to clear the CSV/results files.
clear = False

# Is the IR LEDs attached/enabled.
IR_ATTACHED = False

# Number of JPEG files already present in the OUTPUT_PATH
initial_images_in_person_clips = 0

# A flag indicating whether to clear the images in the OUTPUT_PATH at the start of each session (video streaming).
INITIALLY_CLEAR_IMAGES = True

# Minimum luminance required to activate the IR LEDs.
MINIMUM_LUMINANCE = 30

# -------------Flask related--------------------

# A flag that controls the main loop of the video processing and detection functionality.
# When running is True, the application will continuously capture frames from the camera,
# detect people using the YOLO model, and save frames containing detected people.
# When running is False, the application will stop capturing frames and processing the video stream.
running = False

# Define a Flask app name
app = Flask(__name__)

# Stores a secret key used for cryptographic purposes within the Flask application.
app.config['SECRET_KEY'] = secrets.token_urlsafe(32)

# Holds an instance of the Flask-Login extension's LoginManager class.
login_manager = LoginManager()
login_manager.init_app(app)


class User(UserMixin):
    def __init__(self, username, password):
        """
        create a new user object that is able to log in the Flask server.
        :param username: username to log in
        :param password: password to log in
        """
        self.username = username
        self.password = password

    def get_id(self):
        """
        returns a unique identifier for the user.
        :return: username of the user.
        """
        return self.username


def update_value(value_name, enum_type):
    """
    updates a global variable with the given name and type using data from a JSON request.

    :param value_name: the name of the global variable to update.
    :param enum_type: an enum type representing valid values for the variable.
    :returns: a success message or an error message along with a 400 status code.
    """
    try:
        data = request.get_json()
        if data and value_name in data:
            selected_value = data[value_name]

            if value_name == 'mode' and selected_value == "None":
                globals()[value_name] = None
            else:
                enum_type(selected_value)  # Validate by attempting conversion
                globals()[value_name] = enum_type(selected_value)

            log.success(f'{value_name.capitalize()} is set to {selected_value.capitalize()}')
            return f"{value_name.capitalize()} updated successfully."
    except ValueError:
        return f"Invalid {value_name} selection.", 400


@app.route('/set_location', methods=['POST'])
@login_required
def set_location():
    """
    updates the 'location' global variable using the update_value function with Location enum.
    :return: the response from the update_value function.
    """
    return update_value('location', Location)


@app.route('/set_weather', methods=['POST'])
@login_required
def set_weather():
    """
    updates the 'weather' global variable using the update_value function with Weather enum.
    :return: the response from the update_value function.
    """
    return update_value('weather', Weather)


@app.route('/set_mode', methods=['POST'])
@login_required
def set_mode():
    """
    updates the 'mode' global variable using the update_value function with Mode enum.
    :return: the response from the update_value function.
    """
    return update_value('mode', Mode)


@app.route('/set_clear', methods=['POST'])
@login_required
def set_clear():
    """
    updates the 'clear' global variable based on the value received in a JSON request.
    :return: a success message or an error message along with a 400 status code.
    """
    global clear
    try:
        data = request.get_json()
        if data and "clear" in data:
            selected_value = data["clear"]

            if selected_value == "Yes":
                clear = True
                log.warn("Emptying the CSV file (based on the mode) before appending new data")
            else:
                clear = False
            return f"Clear variable set to {clear}"
    except ValueError:
        return f"Error modifying clear variable", 400


@login_manager.user_loader
def load_user(username):
    """
    function to load the user object by username (Flask-Login requirement)
    :param username: the entered username.
    :return: the only user
    """
    global user
    return user if username == user.username else None


@app.route('/', methods=['GET', 'POST'])
def login():
    """
    Handles login requests for the application.

    - GET: Renders the login form template (login.html).
    - POST:
        1. Extracts username and password from the form submission.
        2. Validates user credentials:
            - Checks if a user is already stored in the global variable `user`.
            - Compares the submitted username with the stored user's username.
            - Verifies the password using the `check_password_hash` function.
        3. If credentials are valid:
            - Calls `login_user` function.
            - Redirects the user to the main page (`video` route).
        4. If credentials are invalid:
            - Returns an error message indicating invalid username or password.
    """
    global user

    if request.method == 'POST':
        username = request.form['username']
        password = request.form['password']
        # Validate user credentials
        if user and username == user.username and check_password_hash(user.password, password):
            login_user(user)
            log.success('Successfully logged in')
            return redirect(url_for('video'))  # Redirect to the main page after successful login
        return 'Invalid username or password'  # Error message on failed login
    return render_template('login.html')  # Render login form


@app.route('/logout')
@login_required
def logout():
    """
    logs out the user and redirects them to the login page.
    :return: flask.redirect: A redirect object to the login page.
    """
    logout_user()
    return redirect(url_for('login'))


@app.route('/video')
@login_required
def video():
    """
    renders the 'video.html' template, potentially logging a warning message.
    the warning message details the default behavior when no options are selected in the Location and Weather forms
    :return: flask.render_template: The rendered 'video.html' template.
    """
    log.warn("""
If you don't select any option in either Location and Weather form,
it will default to Location: Indoor, and Weather: Sunny
""")
    return render_template('video.html')


@app.route('/video_feed')
@login_required
def video_feed():
    """
    streams the video feed with detected people using the stream_detect_people function.
    :return: flask.Response: A video feed response with the specified mimetype for image updates.
    """
    return Response(stream_detect_people(), mimetype='multipart/x-mixed-replace; boundary=frame')


@app.route('/stop')
@login_required
def stop():
    """
    stops the server by setting the 'running' global variable to False and logs a success message.
    the success message indicates the number of images saved during the server's runtime.
    :return: a message indicating the server has been stopped.
    """
    global running
    running = False
    log.success(f"Successfully saved {abs(initial_images_in_person_clips - count_jpgs())} images!")

    return "Server stopped."


@app.route('/images')
@login_required
def get_images():
    """
    this function retrieves the image paths from the output directory and returns them as a JSON response.
    """
    image_paths = [os.path.join(OUTPUT_PATH, filename) for filename in os.listdir(OUTPUT_PATH) if
                   filename.endswith(".jpg")]
    return jsonify(image_paths)


@app.route('/start')
@login_required
def start():
    """
    starts the server by setting the 'running' global variable to True.
    :return: a message indicating the server has been started.
    """
    global running
    running = True
    return "Server started."


@app.route('/logs')
@login_required
def get_logs():
    """
    retrieves the application logs, formats them with newlines, and returns them as a response.
    the logs are cleared after retrieval.
    :return: flask.response: A response object containing the application logs as text with the
    'Content-Type' header set to 'text/html'.
    """
    logs = '\n'.join(list(web_logs))  # Join logs using newline character
    response = make_response(logs)
    response.headers['Content-Type'] = 'text/html'
    web_logs.clear()
    return response


@app.route('/download_csv')
@login_required
def download_csv():
    """
    downloads the CSV file corresponding to the current mode as an attachment.
    uses the 'send_from_directory' function to serve the file from the 'results' directory.
    :return: flask.send_from_directory: A response object set to download the specified CSV file as an attachment.
    """
    global mode
    return send_from_directory("results", f"{mode.value}.csv", as_attachment=True)


# --------------------------------END FLASK ROUTES--------------------------------

def stream_detect_people():
    """
    Streams video frames from the camera, detects people, and saves detections based on mode.

    This function continuously captures frames from a video camera, runs a YOLO object detection model on each frame
    to detect people, and saves the detections based on the specified mode.
    The function supports three modes:

    * Luminance: In this mode, the function continuously measures the luminance whenever a person is detected.
        It saves the measured luminance value along with the corresponding confidence score of the detection.
        This mode runs indefinitely until manually stopped.

    * Location and Weather: In these modes, the function captures frames whenever a person is detected and saves them.
        The function runs for a specified duration (in minutes) as defined by the `MINUTES` constant.

    Whenever a person is detected,
    the function calls send_message(device, 'ring') to trigger an alarm on the specified device

    The function exits when either the specified duration elapses in Location or Weather mode,
    or it is manually stopped by the user.
    """

    global MINUTES, RUNTIME, INITIALLY_CLEAR_IMAGES, \
        initial_images_in_person_clips, mode, location, weather, clear, IR_ATTACHED, MINIMUM_LUMINANCE, camera_process

    detected_people = []
    confidences = []
    luminance_data = []  # list to store luminance and accuracy as a tuple.

    # Attempts to connect the bluetooth module.
    # This will NOT run on a separate thread.
    log.info(f"Trying to connect to the BLE device. Don't click start until you get an BLE error or client found log")
    device = asyncio.run(connect_to_device())

    if INITIALLY_CLEAR_IMAGES:
        clear_images()
    else:
        initial_images_in_person_clips = count_jpgs()

    model = YOLO(MODEL_PATH)
    model.classes = [0]  # Only detect people.

    if torch.cuda.is_available():
        model.to('cuda')
        log.success("Running using CUDA!")
    else:
        log.error("CUDA is not available")

    light_sensor = init_light_sensor()
    log.success(f"Initial Luminance: {measure_luminance(light_sensor)}")

    if IR_ATTACHED:
        activate_leds()
        set_leds_state(OUT)

    # start the IR LED thread, so it checks luminance every 1 minute, and take necessary actions based on it.
    # ir_activation_thread = threading.Thread(target=ir_leds_thread, args=(light_sensor,))
    # ir_activation_thread.start()

    while not running:
        continue

    if mode == Mode.LUMINANCE:
        log.info("Running in Luminance mode (infinite loop), only stopped when the STOP button is pressed")
    elif mode is None:
        log.error("Running in default mode. Select a mode!")
    elif mode is not None:
        log.info(f"Running in {mode.value} mode ({MINUTES} minutes)")

    req_socket.send(b'next')
    req_socket.send(b'next')
    start_time = time.time()

    while running and (time.time() - start_time < RUNTIME if mode in [Mode.LOCATION, Mode.WEATHER] else True):

        if IR_ATTACHED:
            set_leds_mode(HIGH if measure_luminance(light_sensor) <= MINIMUM_LUMINANCE else LOW)

        # Read a frame from the camera connection
        img = get_frame_from_stream()

        # Run object detection on the frame
        results = model(img, stream=True, verbose=False, classes=0)

        # Update detected people and their confidence scores
        update_detected_people(results, detected_people, confidences)

        if detected_people:
            if mode == Mode.LUMINANCE:
                # Save image and measure luminance for each detection
                for confidence, _ in detected_people:
                    save_img(img, cv2)
                    luminance = measure_luminance(light_sensor)  # Measure luminance for each detection
                    current_time = datetime.datetime.now().strftime("%m-%d %H: %M: %S")
                    luminance_data.append([luminance, confidence, current_time])
            else:
                # Save frames with detected people (Location or Weather mode)
                save_img(img, cv2)

            # Rin the alarm
            message_thread(device, "ring")

        # Draw detections and information on the frame
        draw_detections_and_info(img, detected_people)

        # Encode the frame as JPEG for streaming
        _, buffer = cv2.imencode('.jpg', img)
        bytes_frame = buffer.tobytes()

        # Yield the encoded frame for streaming
        yield (b'--frame\r\n'
               b'Content-Type: image/jpeg\r\n\r\n' + bytes_frame + b'\r\n')

        # Clear the detected people for the next frame
        detected_people.clear()
        # confidences.clear()

    # Wrap-up
    if mode == Mode.LUMINANCE:
        log.info(f"{mode.value} data collection mode over by manually stopped. Saving results....(will take some time)")
        log.success(f"{mode.value} mode collected {len(luminance_data)} samples")
        for luminance, confidence, timestamp in luminance_data:
            save_results("Luminance", luminance, confidence, timestamp)
            clear = False

    elif mode in (Mode.LOCATION, Mode.WEATHER):
        log.info(
            f"{mode.value} data collection mode over. Either {MINUTES} minutes is over or manually stopped. "
            f"Saving results...."
        )

        # Calculate average accuracy for Location and Weather mode
        confidences = remove_zeros(confidences)
        average_accuracy = sum(confidences) / len(confidences) if confidences else 0
        current_time = datetime.datetime.now().strftime("%m-%d %H: %M: %S")

        for confidence in confidences:
            save_results("Location" if mode == Mode.LOCATION else "Weather",
                         location.value if mode == Mode.LOCATION else weather.value, confidence, current_time)
            clear = False

        # if you want to just save the results as average accuracy and parameter value, replace the above for loop
        # with the following code.
        # if mode == Mode.LOCATION:
        #     save_results("Location", location.value, average_accuracy, current_time)
        # elif mode == Mode.WEATHER:
        #     save_results("Weather", weather.value, average_accuracy, current_time)

        log.success(f"Average accuracy: {average_accuracy} for mode: {mode.value}")

    socket.close()
    req_socket.close()
    camera_process.kill()
    log.error("OpenCV destroyed all windows (ignore if not opened)")


def ir_leds_thread(light_sensor, interval=60):
    """
    Continuously monitors light sensor luminance and adjusts LEDs accordingly.

    This function runs in a separate thread and checks the luminance level at regular
    intervals.

    If the measured luminance is less than or equal to the defined minimum
    luminance (`MINIMUM_LUMINANCE`), it sets the LED mode to high.

    Otherwise, it sets the LED mode to low.
    :param interval: Number of minutes between each check.
    :param light_sensor: TSL2591 object.
    """
    log.info("Running IR LEDs thread...")
    while running:
        luminance = measure_luminance(light_sensor)
        set_leds_mode(HIGH if luminance <= MINIMUM_LUMINANCE else LOW)
        # Add a delay of 60 seconds (1 minute)
        time.sleep(interval)


def get_frame_from_stream():
    """
    receives, decodes, and returns a single frame from a camera stream socket.
    sends request for next frame after decoding.
    :return: decoded frame (OpenCV image, uint8 color).
    """

    # receive frame
    frame_data = socket.recv()
    np_arr = np.frombuffer(base64.b64decode(frame_data), np.uint8)

    # decode
    frame = cv2.imdecode(np_arr, cv2.IMREAD_COLOR)

    # request next frame
    req_socket.send(b'next')
    return frame


def remove_zeros(data):
    """
    only retains non-zeros values given a list.
    :param data: a list of confidences
    :return: a list of only non-zero values.
    """
    return [x for x in data if x != 0]


def measure_luminance(light_sensor):
    """
    measures luminance using the Adafruit TSL2591
    :return: lux
    """
    return light_sensor.get_current().get('lux')


def init_light_sensor(gain=0x18):
    """
    initializes the I2C communication with the TSL291 sensor.
    :param gain: of the sensor (GAIN_LOW, GAIN_MED, GAIN_HIGH, GAIN_MAX) or any custom value.
    """
    light_sensor = tsl2591()
    light_sensor.set_gain(gain)
    light_sensor.enable()
    log.success(f"Initialized light sensor with gain: {gain}")
    return light_sensor


def draw_detections_and_info(img, detected_people, display_detections=False):
    """
    draws bounding boxes and confidence information for detected people on the image.
    :param display_detections: prints number of detected people if True.
    :param img: the image to draw on.
    :param detected_people: list storing detected people data (ID, confidence, bounding box).
    """
    for confidence, (x1, y1, x2, y2) in detected_people:
        cv2.rectangle(img, (x1, y1), (x2, y2), (255, 0, 255), 3)
        cv2.putText(img, f"Person - Conf: {confidence:.2f}", (x1, y1),
                    cv2.FONT_HERSHEY_SIMPLEX, 1, (255, 0, 0), 2, cv2.LINE_AA)

    if detected_people and display_detections:
        log.success(f"Number of people detected: {len(detected_people)}")


def save_results(data_type, data, accuracy, timestamp):
    """
    saves specified data and average accuracy to a CSV file.
    :param timestamp: timestamp at the given accuracy/average accuracy.
    :param data_type: type of data being saved (e.g., "Luminance", "Weather", "Location")
    :param data: Data to be saved (e.g., luminance value, weather information, location information)
    :param accuracy: average accuracy or confidence.
    """
    global clear

    output_file = f"results/{data_type}.csv"
    effectiveness = "Average Accuracy" if data_type in ["Location", "Weather"] else "Accuracy"

    if not os.path.exists(output_file):
        with open(output_file, 'w', newline='') as csvfile:
            writer = csv.writer(csvfile)
            header = [data_type, effectiveness, "Timestamp"]
            writer.writerow(header)
    elif clear:
        # Clear existing data (except header)
        with open(output_file, 'w', newline='') as csvfile:
            writer = csv.writer(csvfile)
            writer.writerow([data_type, effectiveness, "Timestamp"])
    with open(output_file, 'a', newline='') as csvfile:
        writer = csv.writer(csvfile)
        writer.writerow([data, accuracy, timestamp])


def update_detected_people(results, detected_people, confidences):
    """
    updates detected_people dictionary with detections and their confidence.
    :param results: list of detection results from the model.
    :param detected_people: a list storing detected people data (ID, confidence, bounding box).
    :param confidences: list to store confidence values of unique detections.
    """
    for r in results:
        for box in r.boxes:
            x1, y1, x2, y2 = coordinates_in_int(box)
            confidence = _calculate_confidence(box)
            confidences.append(confidence)
            detected_people.append((confidence, (x1, y1, x2, y2)))


def coordinates_in_int(box):
    """
    converted the coordinates to integers
    :param box: bounding box
    :return: x, y coordinates in integers.
    """
    x1, y1, x2, y2 = box.xyxy[0]
    return int(x1), int(y1), int(x2), int(y2)


def _calculate_confidence(box):
    """
    Calculates and rounds the confidence value from the detection based on YOLO's output format.

    Mean Average Precision (mAP): This is a widely used metric that measures both precision and recall across all
    detected classes in our dataset.
    However, mAP might not be the most informative metric for our specific use case as it considers all classes,
    whereas we're only interested in "person" detection.

    Average Precision (AP): Similar to mAP but calculated for a specific class (e.g., "person").
    This provides a more focused evaluation of YOLO's effectiveness in detecting people under various conditions.

    Therefore, we use confidence/accuracy as it is the same as average precision when detecting only one class.

    :param box: Bounding box
    :return: confidence/accuracy
    """
    return (int(box.conf[0] * 100)) / 100


if __name__ == "__main__":
    # Create subprocess for running the Python 3.6.7 csicam.py
    camera_process = subprocess.Popen("/usr/bin/python3 /home/michel/YOLOTHESIS/csicam.py", shell=True, bufsize=1)

    # Run the socket connection for Python 3.6.7 camera stream
    show_stream = True

    context = zmq.Context()
    # video stream socket
    socket = context.socket(zmq.PAIR)
    socket.connect("tcp://localhost:5555")

    # frame request socket
    req_socket = context.socket(zmq.PAIR)
    req_socket.bind("tcp://*:5556")
    req_socket.send(b'next')

    print("Socket created!")

    # Extracts the username and password from the user.txt file.
    with open("security/user.txt", "r") as f:
        word1, word2 = f.readline().strip().split(",")
        user = User(str(word1).replace(" ", ""), generate_password_hash(str(word2).replace(" ", "")))

    # Tells flask about the login functionality.
    login_manager.login_view = 'login'

    # Runs the flask server using localhost, and self-signed credentials using OpenSSL.
    # Command to generate the files:
    # openssl req -x509 -newkey rsa:4096 -nodes -out flask_cert.pem -keyout flask_key.pem -days 365
    app.run(host='0.0.0.0', ssl_context=('security/flask_cert.pem', 'security/flask_key.pem'))
