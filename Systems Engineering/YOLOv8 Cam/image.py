import os
import time
from pathlib import Path
import log

# Directory where images are stored.
OUTPUT_PATH = "static/person_clips"


def save_img(img, cv):
    """
    saves an image to the `OUTPUT_PATH` directory.
    :param img: (numpy.ndarray) The image to be saved.
    :param cv: (OpenCV library) The OpenCV library instance used for image
    :except Exception If there is an error saving the image.
    """
    global OUTPUT_PATH

    if not os.path.exists(OUTPUT_PATH):
        log.warn(f"{OUTPUT_PATH} directory doesn't exist. Creating it...")
        os.makedirs(OUTPUT_PATH)  # Create the directory if it doesn't exist

    filename = f"{OUTPUT_PATH}/person_{int(time.time())}.jpg"

    try:
        cv.imwrite(filename, img)
    except Exception as e:
        log.error(f"Error saving image: {e}")


def count_jpgs():
    """
    Counts the number of JPG files in a given directory.
    :return: The number of JPG files found in the directory.
    """
    global OUTPUT_PATH
    return len(list(Path(OUTPUT_PATH).rglob("*.jpg")))


def clear_images():
    """
    removes all JPEG images from the `OUTPUT_PATH` directory.
    logs warnings or errors related to file access or removal.

    :except OSError: error removing images or accessing the 'OUTPUT_PATH' directory.
    """
    global OUTPUT_PATH

    try:
        os.listdir(OUTPUT_PATH)
    except OSError as e:
        log.error(f"Error accessing output path: {OUTPUT_PATH}. Reason: {e}")
        return  # Exit the function if the path is invalid

    num_files_removed = 0
    for filename in os.listdir(OUTPUT_PATH):
        file_path = os.path.join(OUTPUT_PATH, filename)
        try:
            os.remove(file_path)
            num_files_removed += 1
        except OSError as e:
            log.error(f"Error removing file: {file_path}. Reason: {e}")

    if num_files_removed > 0:
        log.warn(f"Successfully removed {num_files_removed} previous images.")
    else:
        log.warn(f"No images in {OUTPUT_PATH}")
