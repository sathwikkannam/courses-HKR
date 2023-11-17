import os
import asyncio
from azure.iot.device import IoTHubDeviceClient
import time
import random

CONNECTION_STRING = os.getenv('CONNECTION_STRING')


async def main():
    device_client = IoTHubDeviceClient.create_from_connection_string(CONNECTION_STRING)

    await device_client.connect()

    messageCounter = 0

    while True:
        msg = "Message: " + str(messageCounter) + " --- Temp. " + str(20 + random.randint(1, 6))

        print("Sending message...", msg)
        await device_client.send_message(msg)
        print("Message successfully sent!")

        time.sleep(5 + random.randint(1, 5))
        messageCounter += 1
    await device_client.shutdown()


if __name__ == "__main__":
    asyncio.run(main())
