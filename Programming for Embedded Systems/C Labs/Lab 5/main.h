//
// Created by Sathwik kannam on 3/26/2023.
//

#ifndef C_LABS_MAIN_H
#define C_LABS_MAIN_H

#include <stdio.h>
#include <stdbool.h>
#include <string.h>

typedef struct Address {
    char city[20];
    char streetName[20];
    unsigned short streetNumber: 5;
    unsigned int postCode;
} Address;

typedef struct Date {
    unsigned int date: 5;
    unsigned int month: 4;
    unsigned int year;
} Date;

typedef struct Patient {
    char firstName[20];
    char lastName[20];
    bool gender;
    float height;
    float weight;
    float BMI;
    char school[21];
    Date dateOfBirth;
    Address address;
} Patient;

typedef struct VaccinationHistory {
    bool yellowFever;
    bool hepatitis;
    bool malaria;
    bool birdFlue;
    bool polio;
} VaccinationHistory;

typedef struct Levels {
    unsigned short sodiumLevel: 8;
    float potassiumLevel;
} Levels;

struct HealthRecord {
    Patient patient;
    VaccinationHistory vaccinationHistory;
    Levels levels;
};

#define FILE_NAME "healthrecord.dat"
#define NUMBER_OF_PATIENTS 6
#define CHILD_AGE 16

typedef struct HealthRecord HealthRecord;

void calculateBMI(HealthRecord *healthRecord);

void getPatientProfile(HealthRecord *healthRecords);

void showPatientProfile(HealthRecord *healthRecords, const int *size);

void savePatientProfile(HealthRecord *healthRecords, const int *size, FILE *pOutput);

void readPatientProfile(HealthRecord *healthRecords, FILE *pInput);

void sortBMI(HealthRecord *healthRecords, const int *size);

char *isVaccine(const bool *vaccine);

char *toGender(const bool *gender);


#endif //C_LABS_MAIN_H
