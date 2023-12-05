import os
import random
import time
from azure.iot.device import IoTHubDeviceClient, Message

CONNECTION_STRING = os.getenv("CONNECTION_STRING")

TEMPERATURE = 20.0
MSG_TXT = '{{"temperature": {temperature}}}'


def run_telemetry_sample(client):
    # This sample will send temperature telemetry every second
    print("IoT Hub device sending periodic messages")

    # Connect to Azure client
    client.connect()

    while True:
        # Build the message with simulated telemetry values.
        temperature = TEMPERATURE + (random.random() * 15)
        msg_txt_formatted = MSG_TXT.format(temperature=temperature)
        message = Message(msg_txt_formatted)

        # Send the message.
        print("Sending message: {}".format(message))
        client.send_message(message)
        print("Message successfully sent")
        time.sleep(10)


def main():
    print("IoT Hub Quickstart #1 - Simulated device")
    print("Press Ctrl-C to exit")

    # Instantiate the client. Use the same instance of the client for the duration of
    # your application
    client = IoTHubDeviceClient.create_from_connection_string(CONNECTION_STRING)

    # Run Sample
    try:
        run_telemetry_sample(client)
    except KeyboardInterrupt:
        print("IoTHubClient sample stopped by user")
    finally:
        # Upon application exit, shut down the client
        print("Shutting down IoTHubClient")
        client.shutdown()


if __name__ == '__main__':
    main()
