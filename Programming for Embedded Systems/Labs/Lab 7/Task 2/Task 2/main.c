/*
 * Task 2.c
 *
 * Created: 2022-04-18 14:20:27
 * Author : SAKA0191
 */ 

#define F_CPU 16000000UL

#include <avr/io.h>
#include <avr/delay.h>
#include <util/setbaud.h>
#include <avr/interrupt.h>

void configUSART0(void);
volatile unsigned char number;


int main(void)
{
	configUSART0();
	while(!(UCSR0A & (1<<UDRE0))); //wait until UDR0 is empty
	UDR0 = number; //set UDR0 with the number.
	_delay_ms(50);
	
    while (1) 
    {
		
    }
	
}

void configUSART0(){
	UCSR0C = (0<<UPM00) | (3<<UCSZ00) | (0<<USBS0); //UPM00 to set no parity. UCSZ00 to set 8-bit frame. 1 stop bit.
	UCSR0B = (1<<TXEN0) | (1<<RXEN0); //enable receive and transmit/
	UCSR0B = (1<<RXCIE0); //enable on receive complete interrupt. 
	sei(); // enable global interrupts
	UBRR0L = 0x67 //eight least significant bits of 103.
	UBRR0H = 0x00; // 4 significant bits of 103.
	
}


ISR(USART_RX_vect){
	if(UDR0 == 1){
		PORTB ^= (1<<0);
	}else{
		PORTB &= ~(1<<0);
	}
}

