/*
 * Task 6.c
 *
 * Created: 2022-04-11 13:12:15
 * Author : SAKA0191
 */ 

#include <avr/io.h>
#include <avr/interrupt.h>
#include <stdbool.h>

volatile int i = 1;
volatile int f = 1;

void yellow(void);
void redAndYellow(void);
void red(void);
void blue(void);

int main(void)
{
	DDRB = 0b00000111;
    /* Replace with your application code */
	
	TCCR1A = 0x00; 
	TCCR1B |= (1<<WGM12); //set CTC mode.
	TIMSK1 = (1<<OCIE1A); //enable interrupt for CTC mode
	sei(); //enable global interrupts
	TCCR1B = (1<<CS02) | (1<<CS00);	//prescaler of 1028.
		
    while (1) 
    {
    }
}

ISR(TIMER1_COMPA_vect){
	PORTB = 0b00000000; //reset PORTB.
	TCNT1 = 0; //reset TCNT1
	
	if(i == 1){
		red();
	}else if(i == 2){		
		redAndYellow();
	}else if(i == 3){
		blue();
	}else if(i == 4){
		yellow();
	}
	
	
}

void red(){
	OCR1A = 62500; //4 seconds
	PORTB ^= (1<<2); //turn on pin 2.
	i++; //next LED.

}


void redAndYellow(){
	PORTB ^= (1<<2) | (1<<0); //turn on pin 0 and pin 2.	
	OCR1A = 31250; //2 seconds.
	i++; //next LED.
}


void yellow(void){
	PORTB ^= (1<<0); //turn on pin 0.
	OCR1A = 31250; //2 seconds.
	i = 1;	//reset i
	f = 1; //reset f for blue led.
}


void blue(){	
	if (f == 1 || f == 2 || f == 3){
		PORTB ^= (1<<1); //turn on pin 1.
		OCR1A = 31250; //2 seconds * 3  = 6 seconds.
		
		if(f != 3){
			f++; //increment f
		}else{
			f = 3; //change f so it doesn't continue repeating the blue LED.
			i++; //next LED.
		}
	}
}
