import time
import os
import RPi.GPIO as GPIO
from flask import Flask, jsonify, render_template, request
# from mpu6050 import mpu6050
from azure.iot.device import IoTHubDeviceClient, Message
import logging
import cv2

CONNECTION_STRING = os.getenv("CONNECTION_STRING")
JSON_MSG = '{{"Red LED": {red}, "Yellow LED": {yellow}}}'

app = Flask(__name__)

GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)

# define sensors GPIOs
button = 20
sen_PIR = 16

# define actuators GPIOs
led_red = 13
led_ylw = 19

# initialize GPIO status variables
button_sts = 0
sen_PIR_sts = 0
led_red_sts = 0
led_ylw_sts = 0

# Define button and PIR sensor pins as an input
GPIO.setup(button, GPIO.IN)
GPIO.setup(sen_PIR, GPIO.IN)

# Define led pins as output
GPIO.setup(led_red, GPIO.OUT)
GPIO.setup(led_ylw, GPIO.OUT)

# turn LEDs OFF
GPIO.output(led_red, GPIO.LOW)
GPIO.output(led_ylw, GPIO.LOW)


def capture():
    cap = cv2.VideoCapture(1)
    ret, frame = cap.read()

    cv2.imwrite("static/boogeraids.jpg", frame)

    cap.release()


def run_telemetry_sample(ledRedSts, ledYlwSts):
    client = IoTHubDeviceClient.create_from_connection_string(CONNECTION_STRING)
    client.connect()
    msg_txt_formatted = JSON_MSG.format(red=ledRedSts, yellow=ledYlwSts)
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
    GPIO.output(led_ylw, GPIO.HIGH)
    GPIO.output(led_red, GPIO.LOW)
    return "Access Allowed"


@app.route("/deny")
def deny():
    GPIO.output(led_ylw, GPIO.LOW)
    GPIO.output(led_red, GPIO.HIGH)
    return "Access Denied"


@app.route("/")
def index():
    # Read GPIO Status
    buttonSts = GPIO.input(button)
    senPIRSts = GPIO.input(sen_PIR)
    ledRedSts = GPIO.input(led_red)
    ledYlwSts = GPIO.input(led_ylw)

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
        actuator = led_red
    if deviceName == 'ledYlw':
        actuator = led_ylw

    if action == "on":
        GPIO.output(actuator, GPIO.HIGH)
    if action == "off":
        GPIO.output(actuator, GPIO.LOW)

    buttonSts = GPIO.input(button)
    senPIRSts = GPIO.input(sen_PIR)
    ledRedSts = GPIO.input(led_red)
    ledYlwSts = GPIO.input(led_ylw)

    templateData = {
        'button': buttonSts,
        'senPIR': senPIRSts,
        'ledRed': ledRedSts,
        'ledYlw': ledYlwSts,
    }

    run_telemetry_sample(ledRedSts, ledYlwSts)
    return render_template('index.html', **templateData)


if __name__ == "__main__":
    capture()
    log = logging.getLogger('werkzeug')
    log.setLevel(logging.ERROR)  # Set to ERROR to only log errors, not every request

    app.run(host='0.0.0.0', port=80, debug=True)
