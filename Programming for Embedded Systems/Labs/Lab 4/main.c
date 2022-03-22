#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include "Lab4.h"


int main() {
    int practiceChoice;
    int choice;

    printf("1. Practices\n2. Test\n3. Exit\nEnter your choice:");
    scanf("%d", &choice);


    if(choice == 1){
        printf("\n1. Additions\n2. Subtractions\n3. Additions and Subtraction\nEnter your choice:");
        scanf("%d", &practiceChoice);

        if(practiceChoice == 1){
            doQuestions(false, true, false);
        }else if(practiceChoice == 2){
            doQuestions(true, false, false);
        }else if(practiceChoice == 3){
            doQuestions(true, true, false);
        }
        main();
    }else if(choice == 2){
        doQuestions(true, true, true);
        main();
    }
    return 0;
}


void doQuestions(bool isSub, bool isAdd, bool isTest){
    int totalQuestions = (isTest)? numberOfTestQuestions : numberOfPracticeQuestions;
    int i = 0; //represents how many questions have passed or how many correct answers;
    int answer;
    int generatedNumbers[4];
    int firstNums[totalQuestions];
    int secondNums[totalQuestions];
    int sums[totalQuestions];
    char signs[totalQuestions];
    int inputs[totalQuestions];
    char goodResponses[6][24];
    char badResponses[4][24];

    if(!isTest){
        loadResponses(goodResponses, badResponses);
    }

    while(i != totalQuestions){
        generateNumbers(isSub, isAdd, generatedNumbers);
        firstNums[i] = generatedNumbers[0];
        secondNums[i] = generatedNumbers[1];
        signs[i] = (char) generatedNumbers[2];
        sums[i] = generatedNumbers[3];

        printf("\n\t%d.\t%d %c %d = \n", i+1, firstNums[i], signs[i], secondNums[i]);

        while(true){
            printf("Enter your answer: ");
            scanf("%d", &answer);
            printf("\n");

            if((isTest) || (answer == sums[i])){
                inputs[i] = answer;
                i++;
                if(!isTest){
                    printResponse(true, goodResponses, badResponses);
                }
                break;
            }else if(answer != sums[i]){
                printResponse(false, goodResponses, badResponses);
            }
        }

    }

    if(isTest){
        printTestResults(firstNums, secondNums, sums, inputs, signs);
    }
}


void generateNumbers(bool isSub, bool isAdd, int numbers[4]){
    int signValue = getRandomNumber(2);
    int firstNum = getRandomNumber(100);
    int secondNum = getRandomNumber(100);

    if((isSub && !isAdd) || isAdd && isSub){
        if(isAdd){
            signValue = getRandomNumber(2);
        }else{
            signValue = 0;
        }
        if(secondNum > firstNum){
            //swap numbers
            int temp = firstNum;
            firstNum = secondNum;
            secondNum = temp;
        }
    }else if(isAdd){
        signValue = 1;
    }

    numbers[0] = firstNum;
    numbers[1] = secondNum;
    numbers[2] = (signValue == 1)? (int) '+' : (int) '-'; //if 1 then it's addition else if 0 it's subtraction
    numbers[3] = (signValue == 1)? firstNum + secondNum : firstNum - secondNum;

}


void printResponse(bool isGood, char goodResponses[6][24], char badResponses[4][24]){
    if(isGood){
        printf("%s\n", (goodResponses + getRandomNumber(6)));
    }else{
        printf("%s\n", (badResponses + getRandomNumber(4)));
    }
}


void printTestResults(int* first, int* second, int* correct, int* input, char* signs){
    int correctAnswers = 0;
    for (int i = 0; i < numberOfTestQuestions; i++) {
        if(correct[i] == input[i]){
            correctAnswers++;
        }
    }

    printHeader((correctAnswers*100)/(int)numberOfTestQuestions);
    for (int i = 0; i < numberOfTestQuestions; i++) {
        printf("%d\t%d %c %d\t\t%d\t\t%d\n", i+1, first[i], signs[i], second[i], correct[i], input[i]);
    }
    printf("\n");
}


void printHeader(int percent){
    printf("Your test result is %d (percentage)\nDetailed questions and answers:\n\n", percent);
    printf("%s\t%s\t%s\t%s\n", "Nr", "Question", "Correct Answer", "Your answer");
}


void loadResponses(char goodResponses[6][24], char badResponses[4][24]){
    strcpy(goodResponses[0], "Very good!");
    strcpy(goodResponses[1], "Excellent!");
    strcpy(goodResponses[2], "Nice work!");
    strcpy(goodResponses[3], "Well done!");
    strcpy(goodResponses[4], "Great!");
    strcpy(goodResponses[5], "Keep up the good work!");

    strcpy(badResponses[0], "No. Please try again.");
    strcpy(badResponses[1], "Wrong. Try once again.");
    strcpy(badResponses[2], "Don't give up!");
    strcpy(badResponses[3], "No. Keep trying.");
}


int getRandomNumber(int size){
    return rand() %size;
}
