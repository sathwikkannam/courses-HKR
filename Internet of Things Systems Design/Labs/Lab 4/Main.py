import time
import RPi.GPIO as GPIO
from flask import Flask, jsonify, render_template, request
from azure.iot.device import IoTHubDeviceClient, Message
import cv2
import os
import AWS_Rekognition_Face

CONNECTION_STRING = os.getenv("CONNECTION_STRING")
MSG_TXT = '{{"Red LED": {red}, "Yellow LED": {yellow}}}'

app = Flask(__name__)

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

# define sensors GPIOs
button = 20
senPIR = 16

# define actuators GPIOs
ledRed = 13
ledYlw = 19

# initialize GPIO status variables
buttonSts = 0
senPIRSts = 0
ledRedSts = 0
ledYlwSts = 0

# Define button and PIR sensor pins as an input
GPIO.setup(button, GPIO.IN)
GPIO.setup(senPIR, GPIO.IN)

# Define led pins as output
GPIO.setup(ledRed, GPIO.OUT)
GPIO.setup(ledYlw, GPIO.OUT)

# turn leds OFF 
GPIO.output(ledRed, GPIO.LOW)
GPIO.output(ledYlw, GPIO.LOW)


def capture():
    cap = cv2.VideoCapture(0)
    ret, frame = cap.read()
    if not ret:
        print("Frame could not be read")
        exit()
    cv2.imwrite("static/boogeraids.jpg", frame)
    cap.release()


def run_telemetry_sample(ledRedSts, ledYlwSts):
    client = IoTHubDeviceClient.create_from_connection_string(CONNECTION_STRING)
    client.connect()
    msg_txt_formatted = MSG_TXT.format(red=ledRedSts, yellow=ledYlwSts)
    message = Message(msg_txt_formatted)
    client.send_message(message)
    client.disconnect()
    print('Message sent')


@app.route('/get_update')
def get_update():
    capture()
    button_status = GPIO.input(button)
    return jsonify({'button_status': button_status})


@app.route("/allow")
def allow():
    GPIO.output(ledYlw, GPIO.HIGH)
    GPIO.output(ledRed, GPIO.LOW)
    time.sleep(8)
    GPIO.output(ledYlw, GPIO.LOW)
    print("Access Allowed")
    return index()


@app.route("/deny")
def deny():
    GPIO.output(ledYlw, GPIO.LOW)
    GPIO.output(ledRed, GPIO.HIGH)
    time.sleep(8)
    GPIO.output(ledRed, GPIO.LOW)
    print("Access Denied")
    return index()


@app.route("/")
def index():
    # Read GPIO Status
    buttonSts = GPIO.input(button)
    senPIRSts = GPIO.input(senPIR)
    ledRedSts = GPIO.input(ledRed)
    ledYlwSts = GPIO.input(ledYlw)

    templateData = {
        'button': buttonSts,
        'senPIR': senPIRSts,
        'ledRed': ledRedSts,
        'ledYlw': ledYlwSts,
    }

    return render_template('index.html', **templateData)


# The function below is executed when someone requests a URL with the actuator name and action in it:
@app.route("/<deviceName>/<action>")
def action(deviceName, action):
    if deviceName == 'ledRed':
        actuator = ledRed
    if deviceName == 'ledYlw':
        actuator = ledYlw

    if action == "on":
        GPIO.output(actuator, GPIO.HIGH)
    if action == "off":
        GPIO.output(actuator, GPIO.LOW)

    buttonSts = GPIO.input(button)
    senPIRSts = GPIO.input(senPIR)
    ledRedSts = GPIO.input(ledRed)
    ledYlwSts = GPIO.input(ledYlw)

    templateData = {
        'button': buttonSts,
        'senPIR': senPIRSts,
        'ledRed': ledRedSts,
        'ledYlw': ledYlwSts,
    }

    run_telemetry_sample(ledRedSts, ledYlwSts)
    return render_template('index.html', **templateData)


def check_button_and_capture():
    while True:
        button_status = GPIO.input(button)
        if not button_status:
            print("Bell rang, starting recognition...")
            capture()
            try:
                if AWS_Rekognition_Face.detect_face():
                    allow()
                else:
                    deny()
            except:
                print("No face detected")
                deny()
            time.sleep(0.5)  # Debounce delay


if __name__ == "__main__":
    # Start the button check in a separate thread
    from threading import Thread

    button_thread = Thread(target=check_button_and_capture)
    button_thread.start()

    # Start the Flask app
    app.run(host='0.0.0.0', port=80, debug=True)
