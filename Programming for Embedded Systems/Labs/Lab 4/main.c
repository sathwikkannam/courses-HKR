#include <stdio.h>
#include <stdlib.h>


#define numberOfQuestions 10
#define numberofTestQuestions 15

int getInput();
void doTest();
long int getRandomNum();
void addition();
void subtraction();
int getRandomSign();
void both();

int main() {
    int choice = getInput();
    int practiceChoice;

    if(choice == 1){
        scanf("1. Additions\n2. Subtractions\n3. Additions and Subtraction\nEnter your choice:%d", &practiceChoice);

        if(practiceChoice == 1){
            addition();
        }else if(practiceChoice == 2){
            subtraction();
        }else if(practiceChoice == 3){
            both();
        }

    }else if(choice == 2){
        doTest();
    }
    return 0;
}


int getInput(){
    int name;
    int choice;

    scanf("Enter name: %s\n", &name);
    printf("Welcome, %s\n!", name);

    scanf("1. Practices\n2. Test\n3. Exit\nEnter your choice:%d", &choice);
    printf("\n");
    return choice;
}


void doTest(){


}

long int getRandomNum(){
    return rand() %60;
}

int getRandomSign(){
    return rand() %1;
}



void addition(){
    int correctAnswer = 0;
    int answer;
    int sum;
    int firstNum;
    int secondNum;

    while(correctAnswer != numberOfQuestions){
        firstNum = getRandomNum();
        secondNum = getRandomNum();
        sum = firstNum + secondNum;
        printf("\n\t%d. \t%d + %d = \n", correctAnswer+1, firstNum, secondNum);
        scanf("Enter your answer: %d", &answer);

        if(answer == sum){
            correctAnswer++;
        }else{
            scanf("No. Please try again. Enter ");
        }
    }
}

void subtraction(){
    unsigned int correctAnswer;

}
void both(){

}

