from mpu6050 import mpu6050
import pandas as pd
import time

FREQUENCY = 50
_class = "fast-walking"

sensor = mpu6050(0x68)

start_time = time.time()
end_time = start_time + 60

data = []

while time.time() < end_time:
    ax, ay, az = sensor.get_accel_data()
    gx, gy, gz = sensor.get_gyro_data()
    data.append([ax, ay, az, gx, gy, gz])
    time.sleep(1 / FREQUENCY)

df = pd.DataFrame(data, columns=['ax', 'ay', 'az', 'gx', 'gy', 'gz'])
df['class'] = _class
