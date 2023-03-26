#include "main.h"

int main() {
    HealthRecord healthRecords[NUMBER_OF_PATIENTS];
    FILE * file = NULL;
    int size;

    getPatientProfile(healthRecords);
    size = sizeof(healthRecords)/sizeof(healthRecords[0]);
    savePatientProfile(healthRecords, &size, file);
    readPatientProfile(healthRecords, file);
    sortBMI(healthRecords, &size);
    showPatientProfile(healthRecords, &size);

    return 0;
}


void calculateBMI(HealthRecord * healthRecord){
    float height = healthRecord->patient.height/100;
    healthRecord->patient.BMI = healthRecord->patient.weight/(height * height);
}


void savePatientProfile(HealthRecord * healthRecords, const int * size, FILE * pOutput){
    pOutput = fopen(FILE_NAME, "wb");

    if(pOutput != NULL){
        for (int i = 0; i < *size; i++) {
            fwrite(healthRecords + i, sizeof(HealthRecord), 1,  pOutput);
        }
    }else{
        printf("\nError creating the '%s' file", FILE_NAME);
    }

    fclose(pOutput);
}


void readPatientProfile(HealthRecord * healthRecords, FILE * pInput){
    int i = 0;
    HealthRecord healthRecord;
    pInput = fopen(FILE_NAME, "rb");

    if(pInput != NULL){
        while(fread(&healthRecord, sizeof(HealthRecord), 1, pInput) == 1){
            *(healthRecords + i) = healthRecord;
            i++;
        }
    }else{
        printf("\nError opening the '%s' file", FILE_NAME);
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
    HealthRecord healthRecord;
    unsigned int temp = 0;
    char stringTemp[7];

    for (int j = 0; j < NUMBER_OF_PATIENTS; j++) {
        printf("\nFirst Name, Last Name, Gender, Height, Weight: ");
        scanf("%s %s %s %f %f",
              &healthRecord.patient.firstName, &healthRecord.patient.lastName,
              &stringTemp, &healthRecord.patient.height, &healthRecord.patient.weight);

        printf("\nDate: ");
        scanf("%u", &temp);
        healthRecord.patient.dateOfBirth.date = temp;
        printf("\nMonth: ");
        scanf("%u", &temp);
        healthRecord.patient.dateOfBirth.month = temp;
        printf("\nYear: ");
        scanf("%u", &temp);
        healthRecord.patient.dateOfBirth.year = temp;

        healthRecord.patient.gender = (strcmp(stringTemp, "Male") == 0 || strcmp(stringTemp, "male") == 0)? 1:0;
        calculateBMI(&healthRecord);

        if (healthRecord.patient.dateOfBirth.year > 2022 - CHILD_AGE) {
            printf("\nSchool: ");
            scanf("%s", &healthRecord.patient.school);
        }else{
            strcpy(healthRecord.patient.school, "Null");
        }

        printf("\nCity, StreetName, StreetNumber, Postcode: ");
        scanf("%s %s %u %u", &healthRecord.patient.address.city, &healthRecord.patient.address.streetName,
              &temp, &healthRecord.patient.address.postCode);

        healthRecord.patient.address.streetNumber = temp;

        printf("\nMalaria: ");
        scanf("%s", &stringTemp);
        healthRecord.vaccinationHistory.malaria = (strcmp(stringTemp, "Yes") == 0)? 1:0;
        printf("\nPolio: ");
        scanf("%s", &stringTemp);
        healthRecord.vaccinationHistory.polio = (strcmp(stringTemp, "Yes") == 0)? 1:0;
        printf("\nYellow Fever: ");
        scanf("%s", &stringTemp);
        healthRecord.vaccinationHistory.yellowFever = (strcmp(stringTemp, "Yes") == 0)? 1:0;
        printf("\nHepatitis: ");
        scanf("%s", &stringTemp);
        healthRecord.vaccinationHistory.hepatitis = (strcmp(stringTemp, "Yes") == 0)? 1:0;
        printf("\nBird Flue: ");
        scanf("%s", &stringTemp);
        healthRecord.vaccinationHistory.birdFlue = (strcmp(stringTemp, "Yes") == 0)? 1:0;

        printf("\nPotassium Level (normal range 2.5 - 3.5), Sodium Level (normal range 135 - 145): ");
        scanf("%f %u", &healthRecord.levels.potassiumLevel, &temp);
        healthRecord.levels.sodiumLevel = temp;

        *(healthRecords + j) = healthRecord;
    }
}


void showPatientProfile(HealthRecord * healthRecords, const int * size){
    getchar();
    int enter;
    for (int i = 0; i < *size; i++) {
        if(i >= 1){
            printf("\nNext record?");
            scanf("%c", &enter);
        } if(enter == (int) '\n' || i == 0){
            printf("-------------------------------------");
            printf("\n\tName: %s %s", (healthRecords + i)->patient.firstName, (healthRecords + i)->patient.lastName);
            printf("\n\tDOB: %u-%u-%u", (healthRecords + i)->patient.dateOfBirth.date, (healthRecords + i)->patient.dateOfBirth.month,
                   (healthRecords + i)->patient.dateOfBirth.year);
            printf("\n\tGender: %s", toGender(&(healthRecords + i)->patient.gender));
            printf("\n\tSchool: %s", (healthRecords + i)->patient.school);
            printf("\n\tHeight (cm): %f", (healthRecords + i)->patient.height);
            printf("\n\tWeight (kg): %f", (healthRecords + i)->patient.weight);
            printf("\n\tBMI: %f", (healthRecords + i)->patient.BMI);
            printf("\n\tCity: %s", (healthRecords + i)->patient.address.city);
            printf("\n\tStreet Name: %s", (healthRecords + i)->patient.address.streetName);
            printf("\n\tStreet Number: %u", (healthRecords + i)->patient.address.streetNumber);
            printf("\n\tPostcode: %u", (healthRecords + i)->patient.address.postCode);
            printf("\n\tYellow Fever: %s", isVaccine(&(healthRecords + i)->vaccinationHistory.yellowFever));
            printf("\n\tHepatitis: %s", isVaccine(&(healthRecords + i)->vaccinationHistory.hepatitis));
            printf("\n\tMalaria: %s", isVaccine(&(healthRecords + i)->vaccinationHistory.malaria));
            printf("\n\tBird Flue: %s", isVaccine(&(healthRecords + i)->vaccinationHistory.birdFlue));
            printf("\n\tPolio: %s", isVaccine(&(healthRecords + i)->vaccinationHistory.polio));
            printf("\n\tPotassium Level: %0.2f", (healthRecords + i)->levels.potassiumLevel);
            printf("\n\tSodium Level: %u\n", (healthRecords + i)->levels.sodiumLevel);
        }

    }
}


char * isVaccine(const bool * vaccine){
    return (*vaccine == 1)? "Yes":"No";
}


char * toGender(const bool * gender){
    return (*gender == 1)? "Male":"Female";
}