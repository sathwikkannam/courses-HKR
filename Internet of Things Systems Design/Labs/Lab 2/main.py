import os
import random
import time
from azure.iot.device import IoTHubDeviceClient, Message

CONNECTION_STRING = os.getenv("CONNECTION_STRING")

TEMPERATURE = 20.0
JSON_MESSAGE = '{{"temperature": {temperature}}}'


def main():
    client = IoTHubDeviceClient.create_from_connection_string(CONNECTION_STRING)

    try:
        print("Starting to send telemetry")
        send_temperature_telemetry(client)
    except KeyboardInterrupt:
        print("Stopped sending telemetry")
    finally:
        client.shutdown()


def send_temperature_telemetry(client):
    """
    This function sends a temperature (simulated) to the Azure IoT Hub repeatedly.
    Sending the message in JSON enables the routing pipelines to auto-parse the body of the message.
    :param client: Azure IoT hub client initialized with a primary connecting string of this device.
    """

    client.connect()

    while True:
        temperature = TEMPERATURE + (random.random() * 15)
        msg_txt_formatted = JSON_MESSAGE.format(temperature=temperature)
        message = Message(msg_txt_formatted)

        print("Sending message: {}".format(message))
        client.send_message(message)
        print("Message successfully sent")
        time.sleep(10)


if __name__ == '__main__':
    main()
