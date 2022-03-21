#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include "Lab4.h"


int main() {
    int practiceChoice;
    int choice = getInput();

    if(choice == 1){
        printf("\n1. Additions\n2. Subtractions\n3. Additions and Subtraction\nEnter your choice:");
        scanf("%d", &practiceChoice);

        if(practiceChoice == 1){
            doPractice(false, true);
        }else if(practiceChoice == 2){
            doPractice(true, false);
        }else if(practiceChoice == 3){
            doPractice(true, true);
        }
        main();
    }else if(choice == 2){
        doTest();
        main();
    }
    return 0;
}


int getInput(){
    int choice;

    printf("1. Practices\n2. Test\n3. Exit\nEnter your choice:");
    scanf("%d", &choice);


    return choice;
}


void doPractice(bool isSub, bool isAdd){
    int totalCorrectAnswers = 0;
    int answer;
    int sum;
    int firstNum;
    int secondNum;
    int signValue;
    char sign;
    int numbers[4];

    while(totalCorrectAnswers != numberOfQuestions){
        generateNumbers(isSub, isAdd, numbers);
        firstNum = numbers[0];
        secondNum = numbers[1];
        signValue = numbers[2];
        sum = numbers[3];
        sign = (signValue == 1)? '+' : '-';

        printf("\n\t%d.\t%d %c %d = \n", totalCorrectAnswers+1, firstNum, sign, secondNum);

        while(answer != sum){
            printf("Enter your answer: ");
            scanf("%d", &answer);
            printf("\n");

            if(answer == sum){
                totalCorrectAnswers++;
            }else{
                printf("No. Please try again.\n");
            }
        }

    }

}

void generateNumbers(bool isSub, bool isAdd, int numbers[4]){
    int signValue = getRandomSignValue();
    int firstNum = getRandomNum();
    int secondNum = getRandomNum();
    int sum;


    if((isSub && !isAdd) || isAdd && isSub){
        if(isAdd){
            signValue = getRandomSignValue();
        }else{
            signValue = 0;
        }
        if(secondNum > firstNum){
            //swap numbers
            if(secondNum > firstNum){
                int temp = firstNum;
                firstNum = secondNum;
                secondNum = temp;
            }
        }
    }else if(isAdd){
        signValue = 1;
    }

    sum = (signValue == 1)? firstNum + secondNum : firstNum - secondNum;

    numbers[0] = firstNum;
    numbers[1] = secondNum;
    numbers[2] = signValue; //if 1 then it's addition else if 0 it's subtraction
    numbers[3] = sum;

}

long int getRandomNum(){
    return rand() %100;
}

int getRandomSignValue(){
    return rand() %2;
}
