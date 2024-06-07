from collections import deque

web_logs = deque()
FLASK_MODE = True


def info(message):
    """
    Adds an informational message to the web logs queue.
    :param message: The message to be logged.
    """
    if not FLASK_MODE:
        print(message)
        return

    web_logs.append(message)


def error(message):
    """
    Adds an error message to the web logs queue, wrapped in HTML to display it in red.
    :param message: The message to be logged.
    """
    if not FLASK_MODE:
        print(message)
        return

    web_logs.append(f"<span style='color:red'>{message}</span>")


def success(message):
    """
    Adds a success message to the web logs queue, wrapped in HTML to display it in green.
    :param message: The message to be logged.
    """
    if not FLASK_MODE:
        print(message)
        return

    web_logs.append(f"<span style='color:green'>{message}</span>")


def warn(message):
    """
    Adds a warning message to the web logs queue, wrapped in HTML to display it in orange.
    :param message: The message to be logged.
    """
    if not FLASK_MODE:
        print(message)
        return

    web_logs.append(f"<span style='color:orange'>{message}</span>")
