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
    int size;
    //getPatientProfile();
    size = sizeof(healthRecords)/sizeof(healthRecords[0]);
    //savePatientProfile
    //readPatientProfile
    //Sort their BMI
    //showPatientProfile one at a time.

    return 0;
}


double calculateBMI(const double * height, const double * weight){
    return *weight/((*height/100) * (*height/100));
}


void getPatientProfile(HealthRecord * healthRecords){
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


void showPatientProfile(HealthRecord * healthRecords, const int * size){
    char enter;
    for (int i = 0; i < *size; i++) {
        if(i >= 1){
            scanf("%c", &enter);
        }
        if(i == 0 || enter == '\n'){
            printf("Name: %s %s BMI: %f\n", (healthRecords + i)->patient.firstName,
                   (healthRecords + i)->patient.lastName,
                   (healthRecords + i)->patient.BMI);
        }

    }

}


void savePatientProfile(HealthRecord * healthRecords, const int * size){
    //write to file.
    FILE * pOutput = fopen(FILE_NAME, "wb");

    if(pOutput != NULL){
        for (int i = 0; i < *size; i++) {
            fwrite(healthRecords + i, sizeof(HealthRecord), 1,  pOutput);
        }
    }else{
        printf("Error creating the '%s' file", FILE_NAME);
        return;
    }

    fclose(pOutput);
}


void readPatientProfile(HealthRecord * healthRecords){
    //read from file
    int i = 0;
    HealthRecord healthRecord;
    FILE * pInput = fopen(FILE_NAME, "rb");

    if(pInput != NULL){
        while(fread(&healthRecord, sizeof(HealthRecord), 1, pInput) == 1){
            *(healthRecords + i) = healthRecord;
            i++;
        }
    }else{
        printf("Error opening the '%s' file", FILE_NAME);
        return;
    }

    fclose(pInput);

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

