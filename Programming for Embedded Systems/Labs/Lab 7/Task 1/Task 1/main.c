/*
 * Task 1.c
 *
 * Created: 2022-04-18 13:53:46
 * Author : SAKA0191
 */ 

#ifndef F_CPU
#define F_CPU 16000000UL
#endif
#define BAUD 9600
#define MYUBRR F_CPU/16/BAUD-1

#include <avr/io.h>
#include <avr/delay.h>
#include <util/setbaud.h>

void configUSART0(void);
unsigned char recieveData(void);

int main(void)
{
	unsigned char number;
	DDRB = 0x01; //set pin 0 in DDRB to output.
	configUSART0();
	
    while (1) 
    {
        number = recieveData();
		
        if (number == '1'){
			PORTB |= (1 << PINB0);
		}else if (number == '0'){
			PORTB &= ~(1 << PINB0);
		}
		_delay_ms(50); 
		
    }
}

void configUSART0(void){
	UBRR0H = MYUBRR >> 8; //eight least significant bits of BAUD.
	UBRR0L = MYUBRR; // 4 significant bits of BAUD.
	UCSR0B = (1<<RXEN0); //enable transmit
	UCSR0C = (3<<UCSZ00); //UCSZ00 to set 8-bit frame. 1 stop bit. No parity.
	
}

unsigned char recieveData(){
	while (!(UCSR0A & (1<<RXC0))); //wait for the data to be received. 
	
	return UDR0; //return the data received by the controller.	
}

