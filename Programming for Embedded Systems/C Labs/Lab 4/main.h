//
// Created by Sathwik kannam on 3/26/2023.
//

#ifndef C_LABS_MAIN_H
#define C_LABS_MAIN_H

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>
#include <time.h>

#define numberOfPracticeQuestions 10
#define numberOfTestQuestions 15

void doQuestions(bool isSub, bool isAdd, bool isTest);

void generateNumbers(bool isSub, bool isAdd, int numbers[4]);

void printResponse(bool isGood, char goodResponses[6][24], char badResponses[4][24]);

void printTestResults(int *first, int *second, int *correct, int *input, char *signs);

void printHeader(int percent);

void loadResponses(char goodResponses[6][24], char badResponses[4][24]);

int getRandomNumber(int size);

void selectCategory(void);


#endif //C_LABS_MAIN_H
