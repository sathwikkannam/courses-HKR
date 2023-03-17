//
// Created by Sathwik kannam on 3/14/2023.
//

#ifndef ROBOTPROJECT_ADC_H
#define ROBOTPROJECT_ADC_H

#include <avr/io.h>
#include <avr/sfr_defs.h>

#ifndef F_CPU
#define F_CPU 16000000UL
#endif

#define PRESCALER 4
#define AREF REFS0

void set_prescaler(void);
void adc_init();
void pin_to_mux(uint8_t pin);
void conversion_init(void);
uint16_t read_adc(uint8_t pin);


#endif //ROBOTPROJECT_ADC_H
