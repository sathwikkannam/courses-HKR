from itertools import chain


class Chaining:
    def __init__(self, size):
        self.hashTable = [[] for _ in range(size)]
        self.size = size

    def insert(self, value):
        self.hashTable[self.__hash__(value)].append(value)

    def __hash__(self, value):
        return value % self.size

    def __str__(self):
        elements = ""
        for i in self.hashTable:
            elements.join(f"{i} ")
            for j in i:
                elements.join(f"--> {j} ")


class LinearProbing:
    def __init__(self, size):
        self.hashTable = [None] * size
        self.size = size

    def insert(self, value):
        index = self.__hash__(value)

        if self.hashTable[index] is None:
            self.__insert(index, value)
            return

        # Iterates from index -> size then 0 -> index
        for i in chain(range(index, self.size), range(0, index + 1)):
            if self.hashTable[i] is None:
                self.__insert(i, value)
                return

    def __insert(self, index, value):
        self.hashTable[index] = value

    def __hash__(self, value):
        return value % self.size

    def __str__(self):
        return "".join(f"{i}, " for i in self.hashTable)


class QuadraticProbing:
    def __init__(self, size):
        self.hashTable = [None] * size
        self.size = size

    def insert(self, value):
        index = self.__hash__(value)

        if self.hashTable[index] is None:
            self.hashTable[index].append(value)
            return

        for i in range(self.size):
            index = self.__quad_hash(i, value)

            if self.hashTable[index] is None:
                self.hashTable[index].append(value)
                return

    def __hash__(self, value):
        return value % self.size

    def __quad_hash(self, i, value):
        return (value + pow(i, 2)) % self.size

    def __str__(self):
        return "".join(f"{i}, " for i in self.hashTable)
