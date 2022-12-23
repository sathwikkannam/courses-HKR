from itertools import chain


class Hashing:
    def __init__(self, size, mode):
        self.hash_table = [[] if mode == "CHAINING" else None for _ in range(size)]
        self.size = size
        self.chaining = Chaining()
        self.linear = LinearProbing()
        self.quadratic = QuadraticProbing()
        self.mode = mode.lower()

    def insert(self, key):
        index = self.__hash__(key)
        if self.mode == "CHAINING".lower():
            self.chaining.insert(self.hash_table, key, index)
        elif self.mode == "LINEAR".lower():
            self.linear.insert(self.hash_table, key, index, self.size)
        elif self.mode == "QUADRATIC".lower():
            self.quadratic.insert(self.hash_table, key, index, self.size)

    def __hash__(self, key=None):
        return key % self.size

    def get_mode(self):
        return self.mode

    def __str__(self):
        return "".join(f"{x} --> {self.hash_table[x].__str__()}\n" for x in range(len(self.hash_table)))


class Chaining:
    def insert(self, hash_table, value, index):
        hash_table[index].append(value)


class LinearProbing:
    def insert(self, hash_table, value, index, size):
        if hash_table[index] is None:
            hash_table[index] = value
            return

        # Iterates from index -> size then 0 -> index
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
