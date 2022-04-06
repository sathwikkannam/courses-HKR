//
// Created by SAKA0191 on 2022-04-05.
//

#ifndef LAB_5_LAB5_H
#define LAB_5_LAB5_H

#define FILE_NAME "healthrecord.dat"
#define numberOfPatients 6
#define childAge 16

typedef struct HealthRecord HealthRecord;

void calculateBMI(HealthRecord * healthRecord);
void getPatientProfile(HealthRecord * healthRecords);
void showPatientProfile(HealthRecord * healthRecords, const int * size);
void savePatientProfile(HealthRecord * healthRecords, const int * size, FILE * pOutput);
void readPatientProfile(HealthRecord * healthRecords, FILE * pInput);
void sortBMI(HealthRecord * healthRecords, const int * size);
char * isVaccine(const bool * vaccine);

#endif //LAB_5_LAB5_H
