import log
import subprocess

# src: https://medium.com/@a.vanmalleghem/test-gpio-and-pwm-pins-on-jetson-nano-bd9cb07667d0

# Pin number (Linux pin numbering) - Board pin is D18
IR_LEDS_PIN = 79

HIGH = 1
LOW = 0
OUT = 'out'
IN = 'in'

# Keep track of the previous state
previous_state = None


def activate_leds(gpio_pin=IR_LEDS_PIN):
    """
    activates the IR LEDs by exporting it.
    :param gpio_pin: the Linux number of GPIO/
    """
    subprocess.run(f"echo {str(gpio_pin)} > /sys/class/gpio/export", shell=True, check=True)


def set_leds_state(state, gpio_pin=IR_LEDS_PIN):
    """
    sets the IR LEDs pin to either OUTPUT or INPUT.
    :param gpio_pin: the Linux number of GPIO.
    :param state: state of pin - input (in) or output (out).
    """
    if state not in (IN, OUT):
        return

    subprocess.run(f"echo {state} > /sys/class/gpio/gpio{str(gpio_pin)}/direction", shell=True, check=True)


def set_leds_mode(mode, gpio_pin=IR_LEDS_PIN):
    """
    sets the IR LEDs to HIGH or LOW mode.
    :param gpio_pin: the Linux number of GPIO.
    :param mode: (1) to set HIGH, (0) to set LOW.
    """
    global previous_state

    if mode not in (LOW, HIGH) or previous_state == mode:
        return

    subprocess.run(f"echo {mode} > /sys/class/gpio/gpio{str(gpio_pin)}/value", shell=True, check=True)
    previous_state = mode

    if mode == LOW:
        log.warn("IR LEDs deactivated")
    else:
        log.success("IR LEDs activated")

# same functions but uses the files and write it to it instead of running commands to the terminal
# if the above functions don't work, replace the above functions with the following.

# def activate_leds(gpio_pin=IR_LEDS_PIN):
#     """
#     activates the IR LEDs by exporting it.
#     :param gpio_pin: the Linux number of GPIO/
#     """
#     with open("/sys/class/gpio/export", "w") as export_file:
#         export_file.write(str(gpio_pin))
#
#
# def set_leds_state(state, gpio_pin=IR_LEDS_PIN):
#     """
#     sets the IR LEDs pin to either OUTPUT or INPUT.
#     :param gpio_pin: the Linux number of GPIO.
#     :param state: state of pin - input (in) or output (out).
#     """
#     with open(f"/sys/class/gpio/gpio{gpio_pin}/direction", "w") as direction_file:
#         direction_file.write(str(state))
#
#
# def set_leds_mode(mode, gpio_pin=IR_LEDS_PIN):
#     """
#     sets the IR LEDs to HIGH or LOW mode.
#     :param gpio_pin: the Linux number of GPIO.
#     :param mode: (1) to set HIGH, (0) to set LOW.
#     """
#     global previous_state
#
#     if previous_state == mode:
#         return
#     with open(f"/sys/class/gpio/gpio{gpio_pin}/value", "w") as value_file:
#         value_file.write(str(mode))
#         previous_state = mode
