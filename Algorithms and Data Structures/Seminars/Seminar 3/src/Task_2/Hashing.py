from itertools import chain


class Hashing:
    CHAINING = 1
    LINEAR_PROBING = 2
    QUADRATIC_PROBING = 3

    def __init__(self, size, mode):
        self.__hash_table = [[] if mode == self.CHAINING else None for _ in range(size)]
        self.__size = size
        self.__chaining = Chaining()
        self.__linear = LinearProbing()
        self.__quadratic = QuadraticProbing()
        self.__mode = mode

    def insert(self, value):
        index = self.__hash__(value)
        match self.__mode:
            case self.CHAINING:
                self.__chaining.insert(self.__hash_table, value, index)
            case self.LINEAR_PROBING:
                self.__linear.insert(self.__hash_table, value, index, self.__size)
            case self.QUADRATIC_PROBING:
                self.__quadratic.insert(self.__hash_table, value, index, self.__size)

    def __hash__(self, key=None):
        """
        Here any hash functions can be placed.
        :param key: A key
        :return: A hash value as a function of 'key'
        """
        return key % self.__size

    def get_mode(self):
        return self.__mode

    def __str__(self):
        return "".join(f"{x} --> {self.__hash_table[x].__str__()}\n" for x in range(len(self.__hash_table)))


class Chaining:
    def insert(self, hash_table, value, index):
        hash_table[index].append(value)


class LinearProbing:
    def insert(self, hash_table, value, index, size):
        """
        Iterates from index -> size then 0 -> index
        :param hash_table:
        :param value: A value of key
        :param index: Hash value of the key
        :param size: Size of hashtable
        :return: None
        """

        for i in chain(range(index, size), range(0, index + 1)):
            if hash_table[i] is None:
                hash_table[i] = value
                return


class QuadraticProbing:
    def insert(self, hash_table, value, index, size):
        if hash_table[index] is None:
            hash_table[index] = value
            return

        for i in range(size):
            index = self.__quad_hash(i, value, size)

            if hash_table[index] is None:
                hash_table[index] = value
                return

    def __quad_hash(self, i, value, size):
        return (value + pow(i, 2)) % size
