/*
 * Task 3.c
 *
 * Created: 2022-05-01 18:05:15
 * Author : SAKA0191
 */


#include <stdio.h>
#include <stdlib.h>
#include <avr/io.h>
#include <avr/interrupt.h>
#include "Task3.h"

int main(void) {
    lcd_init();
    adc_init();
    conversion_init();
    sei();

    while (1) {

    }
}


void adc_init() {
    ADMUX = (1 << REFS0) | (1 << MUX1); //Set VREF to AVCC (+5V). Enable MUX1 to listen to ADC2.
    ADCSRA = (1 << ADEN) | (1 << ADIE) | (1 << ADPS2) | (1 << ADPS1) | (1 << ADPS0); //Enable ADC. Enable ADC conversion complete interrupt. 128 Prescaler.
    DIDR0 = (1 << ADC2D); //Disable digital input buffer for ADC2 pin.
}


void conversion_init() {
    ADCSRA |= (1 << ADSC); // Enable Start conversion bit.
}


unsigned int toVoltage(unsigned int adc) {
    return (adc * 5) / 1024; //5V is the reference Voltage.
}


unsigned char *toCharArray(unsigned int number) {
    char *numberArray = calloc(log10(number) + 1, sizeof(char));

    for (int i = log10(number); i >= 0; --i, number /= 10) {
        numberArray[i] = (number % 10) + '0';
    }

    return numberArray;
}


ISR(ADC_vect) {
    lcd_puts(toCharArray(toVoltage(ADC)));
    conversion_init(); //Restart conversion.
}
