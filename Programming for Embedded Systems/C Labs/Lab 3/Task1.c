#include "Headers/Task1.h"


int main() {
    int size = getSize();
    printTable(size);

    return 0;
}

int getSize() {
    int size;
    printf("Enter the size of the table: ");
    scanf("%d", &size);
    printf("\n");

    return size;
}


void printTable(int size) {
    for (int i = 0; i <= size; i++) {
        if (i == 0) {
            printHeader(size);
        } else {
            printf("%3d  |", i);
            for (int j = 1; j <= size; j++) {
                printf("%7d", i * j);
            }
        }

        printf("\n");

    }
}


void printHeader(int size) {
    printf("%3c  |", 'X');
    for (int j = 1; j <= size; j++) {
        printf("%7d", 1 * j);
    }
    printf("\n");
    for (int j = 0; j < size + (6 * (size + 1)); j++) {
        printf("%c", '-');
    }
}