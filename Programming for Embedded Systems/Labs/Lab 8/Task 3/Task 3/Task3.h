/*
 * Task3.h
 *
 * Created: 2022-05-01 18:15:49
 *  Author: SAKA0191
 */ 


#ifndef TASK3_H_
#define TASK3_H_

#include "lcd.c"
#include "lcd.h"

void adc_init(void);
void conversion_init(void);
unsigned int toVoltage(unsigned int adc);
unsigned char * toCharArray(unsigned int number);

#endif /* TASK3_H_ */