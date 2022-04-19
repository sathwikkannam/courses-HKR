/*
 * Task 3.c
 *
 * Created: 2022-04-18 14:31:15
 * Author : SAKA0191
 */ 

#ifndef F_CPU
#define F_CPU 16000000UL
#endif
#define BAUD 9600
#define MYUBRR F_CPU/16/BAUD-1

#include <avr/io.h>
#include <avr/interrupt.h>

void configUSART0(void);
void configTimer1(void);
void transmit(void);
volatile unsigned int secondCount = 0;

int main(void)

{
	configTimer1();
	configUSART0();
	
	
    while (1) 
    {
		
    }
}


ISR(TIMER1_COMPA_vect){
	transmit();
}

void configUSART0(void){
	UBRR0H = MYUBRR >> 8; //eight least significant bits of BAUD.
	UBRR0L = MYUBRR; // 4 significant bits of BAUD.
	UCSR0B = (1<<TXEN0); //enable transmit
	UCSR0C = (3<<UCSZ00); //UCSZ00 to set 8-bit frame. 1 stop bit by default. No parity by default.
}


void configTimer1(void){
	TCCR1B |= (1<<WGM12); //set CTC mode.
	TIMSK1 |= (1<<OCIE1A); //enable interrupt for CTC mode.
	sei(); //enable global interrupts.
	OCR1A = 62500; //set TOP value of 62500.
	TCCR1B |= (1<<CS02); //set prescaler of 256.
}

void transmit(void){
	while(!(UCSR0A & (1<<UDRE0))); //wait until UDR0 is empty
	UDR0 = (secondCount % 2 == 0)? '0' : '1';
	secondCount ++;
}