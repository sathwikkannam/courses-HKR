/*
 * Lab 9.c
 *
 * Created: 2022-05-15 16:40:31
 * Author : SAKA0191
 */ 

 /* 
		MUX REGISTER LAYOUT:
		—————————————————————————————
		| MUX3 | MUX2 | MUX1 | MUX0 |
		—————————————————————————————
	
		MODE          |  PIN   | MUX 3 -> 0 
		——————————————|————————|———————————
		VOLT METER    |  ADC2  | 0010
		SOIL HUMIDITY |  ADC1  | 0001
		LIGHT METER   |  ADC0  | 0000		
		
		INSTRUMENT    |  PIN   | Direction register
		——————————————|————————|———————————————————
		BUTTON        |	 PIND2 | DDRD
		LED           |  PINB2 | DDRB
*/

#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>
#include "lcd.h"
#include "lcd.c"
#include "adc.h"
#include "adc.c"

volatile unsigned int mode = 0;

int main(void)
{
	DDRB |= (1<<PINB2);
	
    while (1) 
    {
		button_init();
		sei(); //Enable global interrupts (Set I bit in SREG).
		adc_init(mode);
		conversion_init();
		
		if(mode > 3){
			mode = 0;
		}
    }
}


void button_init(){
	DDRD ^= (1<<3); //set PIND 2 as output;
	EICRA|= (1<<ISC01) | (1<<ISC00); // rising edge of INT0.
	EIMSK|=(1<<INT0); //set external interrupt for PIND 2.
	_delay_ms(25); //delay 25.
}


unsigned int toVoltage(unsigned int adc){
	return (adc * 5)/1024; //5V is the reference Voltage.
}


unsigned char * toCharArray(unsigned int number){
	char * numberArray = calloc(log10(number) + 1, sizeof(char));
	
	for (int i = log10(number); i >= 0; --i, number /= 10){
		numberArray[i] = (number % 10) + '0';
	}
	
	return numberArray;
}




ISR(ADC_vect){
	//lcd puts.
	conversion_init(); //Restart conversion.
}


ISR(INT0_vect){
	 mode++;
}

