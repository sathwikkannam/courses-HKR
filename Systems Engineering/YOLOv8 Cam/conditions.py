from enum import Enum


class Location(Enum):
    """
    An enumeration representing different locations.
    """
    INDOOR = "Indoor"
    OUTDOOR = "Outdoor"


class Weather(Enum):
    """
    An enumeration representing different weather conditions.
    """
    RAINY = "Rainy"
    FOG = "Fog"
    SUNNY = "Sunny"
    SNOW = "Snow"


class Mode(Enum):
    """
    An enumeration representing different sensor data collection modes.
    """
    LUMINANCE = "Luminance"
    WEATHER = "Weather"
    LOCATION = "Location"
