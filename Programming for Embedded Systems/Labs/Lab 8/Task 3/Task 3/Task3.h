/*
 * Task3.h
 *
 * Created: 2022-05-01 18:15:49
 *  Author: SAKA0191
 */ 


#ifndef TASK3_H_
#define TASK3_H_

void adc_init(void);
void conversion_init(void);
unsigned int toVoltage(unsigned int adc);
void displayNumber(unsigned int number);


#endif /* TASK3_H_ */