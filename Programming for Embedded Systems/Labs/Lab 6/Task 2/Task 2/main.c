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
	
	DDRB ^= (1<<1); // set pin 0 as output.
	TCCR1B |= (1<<WGM12); //set CTC mode.
	TIMSK1 |= (1<<OCIE1A); //enable interrupt for CTC mode.
	sei(); //enable global interrupts.
	OCR1A = 62500; //set TOP value of 62500.
	TCCR1B = (1<<CS02); //set prescaler of 256.
	
	while(1)
	{
		
	}
}

ISR(TIMER1_COMPA_vect){
	PORTB ^= (1<<1); //turn on LED on pin 0.
	
}



