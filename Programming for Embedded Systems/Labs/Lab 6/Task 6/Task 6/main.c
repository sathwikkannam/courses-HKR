/*
 * Task 6.c
 *
 * Created: 2022-04-11 13:12:15
 * Author : SAKA0191
 */ 

#include <avr/io.h>
#include <avr/interrupt.h>


// 1= red
// 2 = red and yellow
// 3 = blue
// 4 = yellow 

// for 4 = 62500
// for 2 = 31250;

volatile int i = 1;
volatile int forSixSeconds = 0;

int main(void)
{
	DDRB = 0b00000111;
    /* Replace with your application code */
	
	TCCR1A = 0x00;
	TCCR1B |= (1<<WGM12);
	TIMSK1 = (1<<OCIE1A);
	sei();
	OCR1A = 62500; //4 seconds
	TCCR1B = (1<<CS02) | (1<<CS00);	
		
    while (1) 
    {
    }
}

ISR(TIMER1_COMPA_vect){
	PORTB = 0b00000000;
	
	//each if statement's OCR1A refers to the next LED with their respective delay. 
	if(i == 1){
		PORTB ^= (1<<2);
		OCR1A = 62500; // 2 seconds
		i++;
	}else if(i == 2){
		PORTB = 0b00000101;
		OCR1A = 31250;
		i++;
	}else if(i == 3){
		PORTB ^= (1<<1);
		OCR1A = 31250; // 2 seconds
		i++;
		forSixSeconds = 0;
	}else if(i == 4){
		PORTB ^= (1<<0);
		OCR1A = 62500;
		i = 1;
	}
	
	
}



