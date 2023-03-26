//
// Created by Sathwik kannam on 3/26/2023.
//

#ifndef EMBEDDED_LABS_ADC_H
#define EMBEDDED_LABS_ADC_H

#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>
#include <stdbool.h>


void adc_init(unsigned int mode);
void conversion_init(void);


#endif //EMBEDDED_LABS_ADC_H
