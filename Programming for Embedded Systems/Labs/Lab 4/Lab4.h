//
// Created by SAKA0191 on 2022-03-20.
//

#ifndef LAB_4_LAB4_H
#define LAB_4_LAB4_H

#define numberOfPracticeQuestions 10
#define numberOfTestQuestions 15

int getRandomNum();
void doQuestions(bool isSub, bool isAdd, bool isTest);
int getRandomSignValue();
void generateNumbers(bool isSub, bool isAdd, int numbers[4]);
void printResponse(bool isGood, char goodResponses[6][24], char badResponses[4][24]);
int promptForAnswer();
void printTestResults(int first[], int second[], int correct[], int input[], char signs[], int percent);
void storeValues(int firstNum, int secondNum, char sign, int sum, int input, int index);
void printHeader(int percent);
void loadResponses(char goodResponses[6][24], char badResponses[4][24]);

#endif //LAB_4_LAB4_H
