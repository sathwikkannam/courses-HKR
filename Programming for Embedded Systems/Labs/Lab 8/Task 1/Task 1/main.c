/*
 * Task 1.c
 *
 * Created: 2022-05-01 13:36:34
 * Author : SAKA0191
 */ 

#define F_CPU 16000000UL

#include <avr/io.h>

#define dutyCycle 80

int main(void)
{
	DDRD |= (1<<PORTD5); //Set pin 5 in DDRD as output (OC0B pin).
	TCCR0A |= (1<<COM0B1) | (1<<WGM00) | (1<<WGM01); //Set Non-Inverting mode with COM0B1 and Fast PWM with WGM00 and WGM01 bits. (Mode 3)
	OCR0A = (dutyCycle/100)*255; //Set TOP value of fraction of maximum value.
	TCCR0B |= (1<<CS00); //Set Prescaler of 1 (No Prescaler).
	
	
    while (1) 
    {
    }
}
