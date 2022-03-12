#include <stdio.h>

unsigned int getInput();
void setBinary(unsigned int stat, char result[]);
void printBinary(char * result);
void checkConditions(const char result[]);


int main() {
    unsigned int n = getInput();
    char result[16];

    setBinary(n, result);
    printBinary(result);
    checkConditions(result);

    return 0;
}


unsigned int getInput(){
    unsigned int stat = 0;

    printf("Enter the bit positions (ended with -1): ");
    scanf("%d", &stat);

    return stat;

}


void setBinary(unsigned int stat, char result[]){
    while (stat) {
        result[sizeof(result)-1 - stat%10] = '1';
        stat /= 10;
    }

}

void printBinary(char result[]){
    printf("\nBit positions as 8-bit: ");
    for (int i = 0; i < sizeof(result); i++) {
        if(result[i] != '1'){
            result[i] = '0';
        }
        printf("%c", result[i]);

    }
}

void checkConditions(const char result[]){

    int size = sizeof(result)-1;
    int count = 0;

    int fuel = size-4;
    int weather = size-5;
    int engineTemp = size-2;
    int brakeFluid = size-1;

    for (int i = (size/2)-1; i <= 0; i++) {
        if(result[i] == '0'){
            count++;
        }
    }

    if(count == size/2){
        printf("\nEverything in the car is working normally");

    }else{
        printf("\nConditions:\n");

        if(result[fuel] == '1'){
            printf("\tFuel is too low\n");
        }
        if(result[weather] == '1' && result[engineTemp] == '1'){
            printf("\tIt's snowing outside and Engine is too hot\n");
        }
        if(result[brakeFluid] == '1' || result[size] == '1'){
            if(result[brakeFluid] == '1'){
                printf("\tBrake Fluid is too low\n");
            }
            if(result[size] == '1'){
                printf("\tBrake pad are worn out");
            }

        }
    }

}
