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

	DDRB ^= (1<<0); // set pin 0 as output.
	TCCR1B |= (1<<WGM12); //set CTC mode
	TIMSK1 |= (1<<OCIE1A); //enable interrupt for CTC mode
	sei(); //enable global interrupts
	OCR1A = 0xBDC; //set TOP value
	TCCR1B = (1<<CS02); //set prescaler of 256.
	
	while(1)
	{
		
	}
}

ISR(TIMER1_COMPA_vect){
	PORTB ^= (1<<0); //turn on LED.
	
}



