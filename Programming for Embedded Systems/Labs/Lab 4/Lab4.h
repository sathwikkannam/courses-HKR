//
// Created by SAKA0191 on 2022-03-20.
//

#ifndef LAB_4_LAB4_H
#define LAB_4_LAB4_H

#define numberOfPracticeQuestions 10
#define numberOfTestQuestions 15

void doQuestions(bool isSub, bool isAdd, bool isTest);
void generateNumbers(bool isSub, bool isAdd, int numbers[4]);
void printResponse(bool isGood, char goodResponses[6][24], char badResponses[4][24]);
void printTestResults(int* first, int* second, int* correct, int* input, char* signs);
void printHeader(int percent);
void loadResponses(char goodResponses[6][24], char badResponses[4][24]);
int getRandomNumber(int size);

#endif //LAB_4_LAB4_H
