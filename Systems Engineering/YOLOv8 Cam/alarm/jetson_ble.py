import asyncio
import threading
import time
import log
from bleak import BleakClient, BleakScanner
from bleak.exc import BleakError

# Mac address of the BLE device (Model: HM-650)
ADDRESS = '3C:A5:19:7B:14:87'

# One of characteristic/UUID of the above device.
UUID = '0000ffe1-0000-1000-8000-00805f9b34fb'

# Timestamp of the latest-sent message.
last_send_time = time.time()

# Flag indicating whether to send a message based on time-based logic.
TIME_BASED = True

# Interval for sending data (in seconds) between each message.
DATA_SEND_INTERVAL = 5

# Timeout of the bluetooth discovery.
TIMEOUT = 10.0

# Keep track of the previous thread
previous_thread = None

# Whether to display a log whenever a message is sent
ON_SEND_LOG = True


async def connect_to_device(mac_address=ADDRESS, max_attempts=5):
    """
    tries to connect to the device using the mac_address for five attempts until it connects.
    :param mac_address: mac address of the BLE module.
    :param max_attempts: maximum number of attempts to find the device.
    :return: connected device or None if not found.
    """
    log.info(
        f"Searching for {mac_address}. Will use {max_attempts} attempts, each lasting for {TIMEOUT} seconds"
    )
    for attempt in range(max_attempts):
        try:
            device = await BleakScanner.find_device_by_address(mac_address, timeout=TIMEOUT)
            if device:
                log.success(f'Client found at address: {mac_address}')
                return device
            else:
                log.warn(f'Attempt {attempt + 1}: Device not found. Retrying...')
        except BleakError as e:
            log.error(f'Error while connecting to {mac_address}: {e}')
        # await asyncio.sleep(2)  # Wait before the next attempt

    log.error(f'Unable to find the device after {max_attempts} attempts.')
    return None


async def _send_message(device, message):
    """
    sends a message to the remote BLE device
    :param device: remote device BLE object from the Bleak discovery scanner.
    :param message: a string message to send to the remote BLE device
    :except BleakError: error sending the message.
    """
    global last_send_time

    message += "\n"

    try:
        async with BleakClient(device) as client:
            data_to_send = message.encode()
            await client.write_gatt_char(UUID, data_to_send)
            last_send_time = time.time()

            if ON_SEND_LOG:
                log.success(f'Notified alarm system to ring alarm')
    except BleakError as e:
        log.error(f'Error while sending message: {e}')


def message_thread(device, message):
    """
    creates a new thread to send a message to the remote device
    only if the last message was sent more than 60 seconds,
    and the previous thread is not running or not ended.
    :param device: remote device BLE object from the Bleak discovery scanner.
    :param message: a string message to send to the remote BLE device
    :return:
    """
    global previous_thread, last_send_time

    if previous_thread and previous_thread.is_alive():
        return

    if ((time.time() - last_send_time) < DATA_SEND_INTERVAL if TIME_BASED else False) or not device:
        return

    thread = threading.Thread(target=asyncio.run, args=(_send_message(device, message),))
    thread.start()
    previous_thread = thread
