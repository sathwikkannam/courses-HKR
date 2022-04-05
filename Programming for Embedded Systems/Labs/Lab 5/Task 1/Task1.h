//
// Created by SAKA0191 on 2022-03-25.
//

#ifndef TASK_1_TASK1_H
#define TASK_1_TASK1_H

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
char * isVaccine(bool vaccine);


#endif //TASK_1_TASK1_H
