class Pair:

    def __init__(self, key, value):
        self.__key = key
        self.__value = value
        self.__i = None

    def get_i(self):
        return self.__i

    def set_i(self, i):
        self.__i = i

    def get_key(self):
        return self.__key

    def get_value(self):
        return self.__value

    def set_key(self, key):
        self.__key = key

    def set_value(self, value):
        self.__value = value

    def __str__(self):
        return "".join(f"Key: {self.__key}, Value: {self.__value}")
