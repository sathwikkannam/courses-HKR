/*
 * Task 2.c
 *
 * Created: 2022-04-08 13:35:35
 * Author : SAKA0191
 */ 

#include <avr/io.h>
#include <avr/interrupt.h>

int main(void)
{

	DDRB = 0x001;
	TCCR1B |= (1<<WGM12);
	TIMSK1 |= (1<<OCIE1A);
	sei();
	OCR1A = 0xBDC; 
	TCCR1B = (1<<CS02);
	
	while(1)
	{
		
	}
}

ISR(TIMER1_COMPA_vect){
	PORTB ^= (1<<0); //turn on LED.
	
}



