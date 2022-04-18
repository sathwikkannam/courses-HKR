/*
 * Task 1.c
 *
 * Created: 2022-04-18 13:53:46
 * Author : SAKA0191
 */ 

#define F_CPU 16000000UL

#include <avr/io.h>
#include <avr/delay.h>
#include <util/setbaud.h>

void configUSART0(void);
unsigned int recieveData(void);
volatile unsigned char number;

int main(void)
{
	DDRB = 0x01;
	configUSART0();
	number = 1;
	
    while (1) 
    {
		while(1(UCSR0A & (1<<UDRE0))); //wait until UDR0 is empty
		UDR0 = number; //set UDR0 with the number.

		if(recieveData() == 1){
			PORTB ^= (1<<0);
		}else{
			PORTB &= ~(1<<0);
		}
		_delay_ms(50); 
		
    }
}

void configUSART0(){
	UCSR0C = (0<<UPM00) | (3<<UCSZ00) | (0<<USBS0); //UPM00 to set no parity. UCSZ00 to set 8-bit frame.
	UBRR0L = 103 //set 9600 baud rate.
	UCSR0B = (1<<TXEN0) | (1<<RXEN0); //enable receive and transmit/
	
}

unsigned int recieveData(){
	while (!(UCSR0A & (1<<RXC0))); //wait for the data to be received. 
				
	return UDR0;	
}

