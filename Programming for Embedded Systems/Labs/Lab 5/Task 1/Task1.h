//
// Created by SAKA0191 on 2022-03-25.
//

#ifndef TASK_1_TASK1_H
#define TASK_1_TASK1_H

#define FILE_NAME "healthrecord.dat"
#define numberOfPatients 6

typedef struct HealthRecord HealthRecord;

double calculateBMI(const double * height, const double * weight);
void getPatientProfile(HealthRecord * healthRecords);
void showPatientProfile(HealthRecord * healthRecords, const int * size);
void savePatientProfile(HealthRecord * healthRecords, const int * size);
void readPatientProfile(HealthRecord * healthRecords);
void sortBMI(HealthRecord * healthRecords, const int * size);


#endif //TASK_1_TASK1_H
