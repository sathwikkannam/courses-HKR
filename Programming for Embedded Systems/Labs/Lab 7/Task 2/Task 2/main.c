/*
 * Task 2.c
 *
 * Created: 2022-04-18 14:20:27
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
#include <avr/interrupt.h>

void configUSART0(void);

int main(void)
{
	configUSART0();
	DDRB |= (1 << PINB0);
	
    while (1) 
    {
		
    }
	
}

void configUSART0(void){
	UBRR0H = MYUBRR >> 8; //eight least significant bits of BAUD.
	UBRR0L = MYUBRR; // 4 significant bits of BAUD.
	UCSR0B |= (1<<RXEN0); //enable receive.
	sei(); // enable global interrupts
	UCSR0B |= (1<<RXCIE0); //enable on receive complete interrupt.
	UCSR0C |= (3<<UCSZ00); //UCSZ00 to set 8-bit frame. 1 stop bit by default. No parity by default. 
	
}


ISR(USART_RX_vect){
	unsigned char data = UDR0;
	
	if(data == '1'){
		PORTB |= (1<<PINB0);
	}else if (data == '0'){
		PORTB &= ~(1<<PINB0);
	}
}

