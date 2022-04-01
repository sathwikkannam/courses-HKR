#include <stdio.h>
#include <stdbool.h>
#include "Task1.h"

typedef union Address {
    char city[20];
    char streetName[20];
    unsigned int postCode;
}Address;

typedef union Patient {
    char firstName[20];
    char lastName[20];
    char dateOfBirth[9];
    char gender[15];
    int age;
    double height;
    double weight;
    double BMI;
    char school[20];
    Address address;

} Patient;

typedef union VaccinationHistory{
    bool yellowFever;
    bool hepatitis;
    bool malaria;
    bool birdFlue;
    bool polio;
}VaccinationHistory;

typedef union Levels{
    unsigned int sodiumLevel;
    unsigned int potassiumLevel;
}Levels;

typedef struct HealthRecord{
    Patient patient;
    VaccinationHistory vaccinationHistory;
    Levels levels;

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
    return *weight/((*height)*(*height))/100;
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


void getPatientProfile(HealthRecord * healthRecords){
    int i = 0;
    HealthRecord healthRecord;

    while (i != numberOfPatients) {
        printf("\nFirst Name, Last Name, DOB, Gender, Age, Height, Weight: ");
        scanf("%s %s %s %s %d %ld %ld",
              &healthRecord.patient.firstName, &healthRecord.patient.lastName,
              &healthRecord.patient.dateOfBirth, &healthRecord.patient.gender,
              &healthRecord.patient.age, &healthRecord.patient.height,
              &healthRecord.patient.weight);

        healthRecord.patient.BMI = calculateBMI(&(healthRecord.patient.height), &(healthRecord.patient.weight));

        if (healthRecord.patient.age < 16) {
            printf("\nSchool: ");
            scanf("%s", &healthRecord.patient.school);
        }

        printf("\nCity, StreetName, Postcode: ");
        scanf("%s %s %d", &healthRecord.patient.address.city, &healthRecord.patient.address.streetName,
              &healthRecord.patient.address.postCode);

        printf("\nYellow Fever, Hepatitis, Malaria, BirdFlue, Polio: ");
        scanf("%d %d %d %d %d", &healthRecord.vaccinationHistory.yellowFever,
              &healthRecord.vaccinationHistory.hepatitis,
              &healthRecord.vaccinationHistory.malaria,
              &healthRecord.vaccinationHistory.birdFlue,
              &healthRecord.vaccinationHistory.polio);

        printf("\nPotassium Level (normal range 2.5 - 3.5), Sodium Level (135 - 145): ");
        scanf("%d %d", &healthRecord.levels.potassiumLevel, &healthRecord.levels.sodiumLevel);

        *(healthRecords + i) = healthRecord;
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
            printf("\n-----------------------------");
            printf("\n\tName: %s %s", (healthRecords + i)->patient.firstName, (healthRecords + i)->patient.lastName);
            printf("\n\tDOB: %s", (healthRecords + i)->patient.dateOfBirth);
            printf("\n\tGender: %s", (healthRecords + i)->patient.gender);
            printf("\n\tHeight (cm): %ld", (healthRecords + i)->patient.height);
            printf("\n\tWeight (kg): %ld", (healthRecords + i)->patient.weight);
            printf("\n\tBMI: %ld", (healthRecords + i)->patient.BMI);
            printf("\n\tCity: %s", (healthRecords + i)->patient.address.city);
            printf("\n\tStreet Name: %s", (healthRecords + i)->patient.address.streetName);
            printf("\n\tPostcode: %d", (healthRecords + i)->patient.address.postCode);
            printf("\n\tYellow Fever: %d", (healthRecords + i)->vaccinationHistory.yellowFever);
            printf("\n\tHepatitis: %d", (healthRecords + i)->vaccinationHistory.hepatitis);
            printf("\n\tMalaria: %d", (healthRecords + i)->vaccinationHistory.malaria);
            printf("\n\tBird Flue: %d", (healthRecords + i)->vaccinationHistory.birdFlue);
            printf("\n\tPolio: %d", (healthRecords + i)->vaccinationHistory.polio);
        }

    }

}
