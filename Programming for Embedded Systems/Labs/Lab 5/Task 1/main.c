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
    double height;
    double weight;
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

typedef struct HealthRecord{
    struct Patient patient;
    struct VaccinationHistory VaccinationHistory;
    struct Levels levels;

}HealthRecord;

int main() {
    HealthRecord healthRecords[numberOfPatients];
    int size = sizeof(healthRecords)/sizeof(healthRecords[0]);

    //getPatientProfile
    //savePatientProfile
    //readPatientProfile
    //Sort their BMI
    //savePatientProfile with sorted.
    //showPatientProfile one at a time.

    return 0;
}


double calculateBMI(const double * height, const double * weight){
    return *weight/((*height/100) * (*height/100));
}

void getPatientProfile(char * healthRecords){
    //make patient from data given from keyboard
    int i = 0;
    HealthRecord * healthRecord;

    while(i != numberOfPatients){
        printf("First Name: ");
        scanf("%s", )
        healthRecords + i = healthRecord;
        i++;
    }


}

void savePatientProfile(HealthRecord * healthRecords, const int * size){
    //write to file.
    FILE * pOutput = fopen(FILE_NAME, "w");

    if(pOutput != NULL){
        for (int i = 0; i < *size; i++) {
            fwrite(&healthRecords + i, sizeof(HealthRecord), 1,  pOutput);
            fprintf(pOutput, "\n");
        }
    }

    fclose(pOutput);
}


void showPatientProfile(HealthRecord * healthRecords, const int * size){
    for (int i = 0; i < *size; i++) {
        printf("Name: %s %s\nDOB: %s\nGender: %s\nHeight: %f\nWeight: %f\nBMI: %f\n");

    }

}

HealthRecord * readPatientProfile(){
    //read from file
    int i;
    HealthRecord * healthRecord;
    HealthRecord * fileHealthRecords;
    FILE * pInput = fopen(FILE_NAME, "r");

    while(pInput != NULL && fread(&healthRecord, sizeof(HealthRecord), 1, pInput)){
        *(fileHealthRecords + i) = *healthRecord;
        i++;
    }
    fclose(pInput);

    return fileHealthRecords;
}


void sortBMI(HealthRecord * healthRecords, const int * size){
    HealthRecord temp;

    for (int i = 0; i < *size-1; i++) {
        for (int j = 0; j < *size-1-i; j++) {
            if((healthRecords + j)->patient.BMI > (healthRecords + j+1)->patient.BMI){
                temp = *(healthRecords + j);
                *(healthRecords + j) = *(healthRecords + j+1);
                *(healthRecords + j+1) = temp;
            }
        }
    }


}

