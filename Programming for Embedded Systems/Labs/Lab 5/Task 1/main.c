#include <stdio.h>
#include <stdbool.h>
#include "Task1.h"

struct Address {
    char city[20];
    char streeName[20];
    int postCode;
};

struct Patient {
    char firstName[20];
    char lastName[20];
    char dateOfBirth[9];
    char gender[15];
    float height;
    float weight;
    double BMI;
    struct Address address;

};

struct VaccinationHistory{
    bool yellowFever;
    bool hepatitis;
    bool malaria;
    bool birdFlue;
    bool polio;
};

struct Levels{
    int sodiumLevel;
    int potassiumLevel;
    char school[20];
};

struct healthRecord{
    struct Patient patient;
    struct VaccinationHistory VaccinationHistory;
    struct Levels levels;

};

int main() {


    return 0;
}


float calculateBMI(float height, float weight){
    return weight/((height/100) * (height/100));
}

void getPatientProfile(){
    //make patient from data given from keyboard
}

void savePatientProfile(struct healthRecord healthRecord){
    //write to file.
    FILE * pOutput = fopen(FILE_NAME, "w");
    fwrite(&healthRecord, sizeof(struct healthRecord), 1,  pOutput);
    fclose(pOutput);
}


void showPatientProfile(struct healthRecord healthRecord){
    //print profile to console.
}

void readPatientProfile(){
    //read from file
    FILE *pInput = fopen(FILE_NAME, "r");

    fclose(pInput);
}

