/*
 * Task 3.c
 *
 * Created: 2022-04-18 14:31:15
 * Author : SAKA0191
 */ 

#define F_CPU 16000000UL

#include <avr/io.h>
#include <util/setbaud.h>
#include <avr/interrupt.h>

void configUSART0();
volatile unsigned int secondCount  = 0;

int main(void)
{
	DDRB ^= (1<<1); // set pin 0 as output.
	TCCR1B |= (1<<WGM12); //set CTC mode.
	TIMSK1 |= (1<<OCIE1A); //enable interrupt for CTC mode.
	sei(); //enable global interrupts.
	OCR1A = 62500; //set TOP value of 62500.
	TCCR1B = (1<<CS02); //set prescaler of 256.
	
    while (1) 
    {
    }
}


ISR(TIMER1_COMPA_vect){
	configUSART0();
	while(!(UCSR0A & (1<<UDRE0))); //wait until UDR0 is empty
	UDR0 = (secondCount % 2 == 0)? 0 : 1;
	secondCount ++;

}

void configUSART0(){
	UCSR0C |= (0<<UPM00) | (3<<UCSZ00) | (0<<USBS0); //UPM00 to set no parity. UCSZ00 to set 8-bit frame. 1 stop bit.
	UBRR0L |= 0x67 //eight least significant bits of 103.
	UBRR0H |= 0x00; // 4 significant bits of 103.
	UCSR0B |= (1<<TXEN0); //enable transmit
	
}
