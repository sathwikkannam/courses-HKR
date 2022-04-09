/*
 * Task 4.c
 *
 * Created: 2022-04-09 14:26:02
 * Author : SAKA0191
 */ 

#include <avr/io.h>
#include <avr/interrupt.h>
#include <avr/delay.h>

#define F_CPU 16000000UL

int main(void)
{
    /* Replace with your application code */
	
	DDRB ^= (1<<0); //set PINB 0 as output.
	DDRD ^= (1<<3); //set PIND 2 as input;
	sei(); //enable global interrupt by setting I bit in SREG.
	EICRA|= (1<<ISC01) | (1<<ISC00);
	EIMSK|=(1<<INT0); //set external interrupt for PIND 2
	_delay_ms(100); //delay 100
	
    while (1) 
    {
		
    }
}

 ISR(INT0_vect)
 {
	 PORTB ^= (1<<0); //toggle PINB 0
 }

