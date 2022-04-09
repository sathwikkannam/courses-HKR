/*
 * Task 5.c
 *
 * Created: 2022-04-09 14:58:20
 * Author : SAKA0191
 */ 

#define F_CPU 16000000UL

#include <avr/io.h>
#include <avr/delay.h>


int main(void)
{
    /* Replace with your application code */
	DDRB = 0b00000111;
    while (1) 
    {
		PORTB = 0b00000100;
		_delay_ms(4000);
		PORTB = 0b00000101;
		_delay_ms(2000);
		PORTB = 0b00000010;
		_delay_ms(6000);
		PORTB = 0b00000001;
		_delay_ms(2000);
    }
}

