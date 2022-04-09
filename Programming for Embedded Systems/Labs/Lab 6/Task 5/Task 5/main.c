/*
 * Task 5.c
 *
 * Created: 2022-04-09 14:58:20
 * Author : SAKA0191
 */ 

#define F_CPU 16000000UL //set ATmega328p clock frequency.

#include <avr/io.h>
#include <avr/delay.h>


int main(void)
{
	DDRB = 0b00000111; //set pins 0, 1, 2 as OUTPUT.
    while (1) 
    {
		PORTB = 0b00000100; //toggle Red LED.
		_delay_ms(4000); 
		PORTB = 0b00000101; //toggle Red and Yellow LED.
		_delay_ms(2000);
		PORTB = 0b00000010; //toggle Green LED.
		_delay_ms(6000);
		PORTB = 0b00000001; //toogle Yellow LED.
		_delay_ms(2000);
    }
}

