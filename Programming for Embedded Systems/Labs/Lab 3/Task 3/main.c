#include <stdio.h>

void getInput();

int main() {
    char input[16];
    char result[16];

    getInput(input);

    int fuelIndex = 15-4;
    int weatherIndex = 15-5;
    int engineIndex = 15-2;
    int brakeFluidIndex = 15-1;
    int brakePadIndex = 15;

    for (int i = 0; i < sizeof(input); ++i) {
        printf("%c", input[i]);
    }
    printf("\n");

    if(input[fuelIndex] == '1'){

    }
    if (input[weatherIndex] == '1' && input[engineIndex] == '1'){

    }
    if(input[brakeFluidIndex] == '1' || input[brakePadIndex] == '1') {
        if (input[brakeFluidIndex] == '1') {

        }
        if (input[brakePadIndex] == '1') {
        }

    }
    return 0;
}


void getInput(char input[]){
    printf("Enter the value of x: ");
    scanf("%s", input);

}

