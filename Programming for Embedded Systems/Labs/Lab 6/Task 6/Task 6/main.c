/*
 * Task 6.c
 *
 * Created: 2022-04-11 13:12:15
 * Author : SAKA0191
 */ 

#include <avr/io.h>
#include <avr/interrupt.h>
#include <stdbool.h>

#define fourSeconds 62500;
#define twoSeconds 31250;

// 1= red for 4
// 2 = red and yellow for 2
// 3 = blue for 6
// 4 = yellow for 2

volatile int i = 1;
volatile int f = 1;

void yellow(void);
void redAndYellow(void);
void red(void);
void blue();

int main(void)
{
	DDRB = 0b00000111;
    /* Replace with your application code */
	
	TCCR1A = 0x00;
	TCCR1B |= (1<<WGM12);
	TIMSK1 = (1<<OCIE1A);
	sei();
	TCCR1B = (1<<CS02) | (1<<CS00);	
		
    while (1) 
    {
    }
}

ISR(TIMER1_COMPA_vect){
	PORTB = 0b00000000;
	TCNT1 = 0;
	
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
	OCR1A = 62500;
	PORTB ^= (1<<2);
	i++;	

}


void redAndYellow(){
	PORTB ^= (1<<2) | (1<<0);	
	OCR1A = 31250;
	i++;	
}


void yellow(void){
	PORTB ^= (1<<0);
	OCR1A = 31250;;
	i = 1;	
	f = 1;
}


void blue(){	
	if (f == 1 || f == 2 || f == 3){
		PORTB ^= (1<<1);
		OCR1A = 31250;
		
		if(f != 3){
			f++;	
		}else{
			f = 3;
			i++;
		}
	}
}
