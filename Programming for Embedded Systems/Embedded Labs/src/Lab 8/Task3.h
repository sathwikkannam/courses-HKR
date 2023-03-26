/*
 * Task3.h
 *
 * Created: 2022-05-01 18:15:49
 *  Author: SAKA0191
 */


#ifndef EMBEDDED_LABS_TASK3_H
#define EMBEDDED_LABS_TASK3_H


#include "Utils/lcd.c"
#include "Utils//lcd.h"

void adc_init(void);
void conversion_init(void);
unsigned int toVoltage(unsigned int adc);
unsigned char * toCharArray(unsigned int number);

#endif //EMBEDDED_LABS_TASK3_H
