/*
 * Task 3.c
 *
 * Created: 2022-05-01 18:05:15
 * Author : SAKA0191
 */ 


#define  F_CPU 16000000UL

#include <avr/io.h>
#include <avr/interrupt.h>
#include "lcd.h"
#include "Task3.h"

volatile unsigned int adc;
volatile char displayValue[16];


int main(void)
{
	lcd_init();
	adc_init();
	conversion_init();
	sei();
	
    while (1) 
    {
    }
}


void adc_init(){
	ADMUX = (1<<REFS0) | (1<<MUX1); //Set VREF to AVCC (+5V). Enable MUX1 to listen to ADC2.
	ADCSRA = (1<<ADEN) | (1<<ADIE) | (1<<ADPS2) | (1<<ADPS1) | (1<<ADPS0); //Enable ADC. Enable ADC conversion complete interrupt. 128 Prescaler.
	DIDR0 = (1<<ADC2D); //Disable digital input buffer for ADC2 pin.	
}


void conversion_init(){
	ADCSRA |= (1<<ADSC); // Enable Start conversion bit.	
}


uint16_t toVoltage(uint16_t adc){
	return (adc * 5)/1024; //5V is the reference Voltage.
}


void toCharArray(uint16_t number){
	
}


ISR(ADC_vect){
	lcd_puts(toCharArray(toVoltage(ADC)));
	conversion_init(); //Restart conversion.
}


