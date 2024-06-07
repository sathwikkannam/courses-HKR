print("Importing...")
import subprocess

cameraprocess = subprocess.Popen("/usr/bin/python3 /home/michel/YOLOTHESIS/csicam.py", shell=True, bufsize=1)
from ultralytics import YOLO
import cv2
import zmq
import base64
import numpy as np
import csv
import time
import os
import log
from python_tsl2591 import tsl2591
from image import clear_images, count_jpgs, save_img
from conditions import Weather, Location, Mode
import torch
import datetime

print("Configuring...")

# -------------Configuration--------------------
# Path to the model (e.g., yolov8n.pt, yolov8x.pt)
# YOLO() function defaults to yolov8n.pt, and downloads it automatically.
# However, use this path for other models.
MODEL_PATH = "models/yolov8n.pt"

# Light sensor
LS_GAIN = 0x18  # 24

# How long should we detect people for? (in minutes)
MINUTES = 10

# Converted to seconds.
RUNTIME = MINUTES * 60

# Threshold for considering bounding boxes as overlapping, we have to fine-tune this (P.S 0.5 doesn't work)
OVERLAP_THRESHOLD = 0.5

# Camera location, and weather conditions. These are its default values if none are selected in the flask server.
location, weather = Location("Indoor"), Weather("Sunny")

# Data collection mode
mode = Mode("Luminance")

# Flag indicating whether to clear the CSV/results files.
clear = False

# Classes that YOLO model is limited to detect.
class_names = ["person"]

# Number of JPEG files already present in the OUTPUT_PATH
initial_images_in_person_clips = 0

# A flag indicating whether to clear the images in the OUTPUT_PATH at the start of each session (video streaming).
INITIALLY_CLEAR_IMAGES = True

# TSL2591 sensor
light_sensor = None

# Camera Stream
showstream = True
print("Initializing camera stream...")

context = zmq.Context()
# video stream socket
socket = context.socket(zmq.PAIR)
socket.connect("tcp://localhost:5555")

# frame request socket
req_socket = context.socket(zmq.PAIR)
req_socket.bind("tcp://*:5556")
req_socket.send(b'next')

print("Done! Startig main loop.\n\n")


def stream_detect_people():
    """
    Streams video frames from the camera, detects people, and saves detections based on mode.
    :except RuntimeError: If an error occurs while reading a frame from the camera.
    """

    global MINUTES, RUNTIME, INITIALLY_CLEAR_IMAGES, \
        initial_images_in_person_clips, mode, location, weather, clear, light_sensor, cameraprocess

    detected_people = []
    confidences = []
    luminance_data = []  # list to store luminance as a key and accuracy as value

    if INITIALLY_CLEAR_IMAGES:
        clear_images()
    else:
        initial_images_in_person_clips = count_jpgs()

    model = YOLO(MODEL_PATH)
    model.classes = [0]

    if torch.cuda.is_available():
        model.to('cuda')
        log.success("Running using CUDA!")
    else:
        log.error("CUDA is not available")

    if mode == Mode.LUMINANCE:
        log.info("Running in Luminance mode (infinite loop), only stopped when the STOP button is pressed")
        init_light_sensor()
        log.success(f"Initial Luminance: {measure_luminance():.3f}")
    elif mode is None:
        log.warn("Running in default mode")
    elif mode is not None:
        log.info(f"Running in {mode.value} mode ({MINUTES} minutes)")

    frames = 0
    req_socket.send(b'next')
    req_socket.send(b'next')
    start_time = time.time()
    frame_time = time.time()
    while time.time() - start_time < RUNTIME if mode in [Mode.LOCATION, Mode.WEATHER] else True:
        # Read a frame from the camera
        img = getFrameFromStream()
        frames += 1

        # print luminance
        if frames % 10 == 0:
            print("Current luminance:", measure_luminance())
            print("Average FPS:", frames / (time.time() - frame_time))
            frames = 0
            frame_time = time.time()

            # Run object detection on the frame
        results = model(img, stream=True, verbose=False, classes=0)

        # Update detected people and their confidence scores
        update_detected_people(results, detected_people, confidences)

        if detected_people:
            if mode == Mode.LUMINANCE:
                # Save image and measure luminance for each detection
                for confidence, _ in detected_people:
                    save_img(img, cv2)
                    luminance = measure_luminance()  # Measure luminance for each detection
                    current_time = datetime.datetime.now().strftime("%m-%d %H: %M: %S")
                    luminance_data.append([luminance, confidence, current_time])
            else:
                # Save frames with detected people (Location or Weather mode)
                save_img(img, cv2)

        if cv2.waitKey(1) == ord('q'):
            log.error("cv2 stopped (pressed 'q')")
            break

        # Draw detections and information on the frame
        if showstream:
            draw_detections_and_info(img, detected_people, display_detections=False)
            cv2.imshow('Webcam', img)
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
        if mode == Mode.LOCATION:
            save_results("Location", location.value, average_accuracy, current_time)
        elif mode == Mode.WEATHER:
            save_results("Weather", weather.value, average_accuracy, current_time)

        log.success(f"Average accuracy: {average_accuracy} for mode: {mode.value}")
    if showstream: cv2.destroyAllWindows()
    log.error("OpenCV destroyed all windows (ignore if not opened)")
    socket.close()
    req_socket.close()
    cameraprocess.kill()


def getFrameFromStream():
    """
    Get camera stream from socket
    :return: frame
    """
    # receive frame
    frameData = socket.recv()
    nparr = np.frombuffer(base64.b64decode(frameData), np.uint8)
    # decode
    frame = cv2.imdecode(nparr, cv2.IMREAD_COLOR)
    # request next frame
    req_socket.send(b'next')
    return frame


def remove_zeros(data):
    """
    Only retains non-zeros values given a list.
    :param data: A list of confidences
    :return: A list of only non-zero values.
    """
    return [x for x in data if x != 0]


def measure_luminance():
    """
   measures luminance using the Adafruit TSL2591
    :return: lux
    """
    global light_sensor
    # just to be sure...
    light_sensor.enable()
    return light_sensor.get_current().get('lux')


def init_light_sensor():
    """
    initializes the I2C communication with the TSL291 sensor.
    """
    global light_sensor
    light_sensor = tsl2591()
    light_sensor.set_gain(LS_GAIN)
    print("Light sensor gain:", light_sensor.get_gain())


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
    Saves specified data and average accuracy to a CSV file.
    :param data_type: Type of data being saved (e.g., "Luminance", "Weather", "Location")
    :param data: Data to be saved (e.g., luminance value, weather information, location information)
    :param accuracy: Average accuracy or confidence.
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
    updates detected_people dictionary with unique detections and their confidence.
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
    Converted the coordinates to integers
    :param box: Bounding box
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
    log.FLASK_MODE = False
    stream_detect_people()
