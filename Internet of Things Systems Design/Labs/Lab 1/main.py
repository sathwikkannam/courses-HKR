import os
import asyncio
from azure.iot.device.aio import IoTHubDeviceClient
import time
import random


async def send_telemetry(device_client):
    while True:
        # Simulate the sensors.
        temperature = random.randint(25, 100)
        humidity = random.randint(50, 100)

        # Send the telemetry.
        print(f"Sending temp: {temperature}")
        await device_client.send_message({"temp": temperature})
        time.sleep(10)  # Wait for 10 seconds

        print(f"Sending humidity: {humidity}")
        await device_client.send_message({"humidity": humidity})
        time.sleep(20)  # Wait for 20 seconds


async def main():
    # Fetch the connection string from an environment variable
    conn_str = os.getenv("CONNECTION_STRING")

    # Create an instance of the device client using the connection string
    device_client = IoTHubDeviceClient.create_from_connection_string(conn_str)

    # Start sending telemetry
    await send_telemetry(device_client)


if __name__ == "__main__":
    asyncio.run(main())
