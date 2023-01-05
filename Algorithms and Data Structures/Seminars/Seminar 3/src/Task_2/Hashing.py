from itertools import chain

from Task_2.Pair import Pair


class Hashing:
    CHAINING, LINEAR_PROBING, QUADRATIC_PROBING = 1, 2, 3

    def __init__(self, size, mode):
        """
        We can rehash the hashtable with different sizes by creating a new Hashing object and providing different size.
        :param size: Size of the hash table
        :param mode: To select the collision avoided.
        """
        self.__hash_table = [[] if mode == self.CHAINING else None for _ in range(size)]
        self.__size = size

        match mode:
            case self.CHAINING:
                self.__collision_handler = Chaining()
            case self.LINEAR_PROBING:
                self.__collision_handler = LinearProbing()
            case self.QUADRATIC_PROBING:
                self.__collision_handler = QuadraticProbing()

    def get_mode(self) -> str:
        return type(self.__collision_handler).__name__

    def insert(self, pair: Pair):
        index = self.__hash__(pair.get_key())

        if self.__collision_handler:
            self.__collision_handler.insert(self.__hash_table, pair.get_value(), index, self.__size)

    def __hash__(self, key=None) -> int:
        """
        Here any hash functions can be placed.
        :param key: A key
        :return: A hash value as a function of 'key'
        """
        return key % self.__size

    def __str__(self) -> str:
        return "".join(f"{i} -> {node.__str__().__str__()}\n" for i, node, in enumerate(self.__hash_table))


class Chaining:
    def insert(self, hash_table, value, index, size):
        hash_table[index].append(value)


class LinearProbing(Chaining):
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

        raise IndexError


class QuadraticProbing(Chaining):
    def insert(self, hash_table, value, index, size):
        if hash_table[index] is None:
            hash_table[index] = value
            return

        for i in range(size):
            index = self.__quad_hash(i, value, size)

            if hash_table[index] is None:
                hash_table[index] = value
                return

        raise IndexError

    def __quad_hash(self, i, key, size) -> int:
        return (key + pow(i, 2)) % size
