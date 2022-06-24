/*
 * adc.h
 *
 * Created: 2022-05-15 17:57:13
 *  Author: SAKA0191
 */ 


#ifndef ADC_H_
#define ADC_H_

#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>
#include <stdbool.h>


void adc_init(unsigned int mode);
void conversion_init(void);


#endif /* ADC_H_ */