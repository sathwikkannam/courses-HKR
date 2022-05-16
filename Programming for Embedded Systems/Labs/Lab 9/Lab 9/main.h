/*
 * main.h
 *
 * Created: 2022-05-15 18:16:19
 *  Author: SAKA0191
 */ 


/*

--------------------SETUP------------------------------------------- 

		MUX REGISTER LAYOUT:
		覧覧覧覧覧覧覧覧覧覧覧覧覧覧�
		| MUX3 | MUX2 | MUX1 | MUX0 |
		覧覧覧覧覧覧覧覧覧覧覧覧覧覧�
		
	
		MODE          |  PIN   | MUX 3 -> 0 
		覧覧覧覧覧覧覧|覧覧覧覧|覧覧覧覧覧�
	0.	VOLT METER    |  ADC2  | 0010
	1.	SOIL HUMIDITY |  ADC1  | 0001
	2.	LIGHT METER   |  ADC0  | 0000	
		
		
		INSTRUMENT    |  PIN   | Direction register
		覧覧覧覧覧覧覧|覧覧覧覧|覧覧覧覧覧覧覧覧覧�
		BUTTON        |	 PIND2 | DDRD
		BLUE LED      |  PIND7 | DDRD
		RED LED       |  PIND6 | DDRD
		
		
Connection Diagram:
                 -----------                   ----------
                |ATmega328p |                 |   LCD    |
                |           |                 |          |
                |         5V|---------------->|A         |	
                |        GND|---------------->|K         |									
                |        PB5|---------------->|D7        |
                |        PB4|---------------->|D6        |
                |        PB3|---------------->|D5        |
                |        PB2|---------------->|D4        |
                |           |                 |D3        |
                |           |                 |D2        |
                |           |                 |D1        |
                |           |                 |D0        |
                |           |                 |          |
                |        PB1|---------------->|E         |
                |           |         GND --->|RW        |
                |        PB0|---------------->|RS        |
                 -----------                   ----------
*/


#ifndef MAIN_H_
#define MAIN_H_

#define F_CPU 16000000UL

#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>
#include <stdbool.h>
#include "adc.h"
#include "adc.c"
#include "lcd.h"
#include "lcd.c"

volatile unsigned short mode = 0;
volatile bool isError = false;

#define BUTTON_DIRECTION DDRD
#define BUTTON_PIN PIND2
#define BUTTON_PORT PORTD
#define BUTTON_INTERRUPT INT0

#define BLUE_LED_DIRECTION DDRD
#define BLUE_LED_PORT PORTD
#define BLUE_LED_PIN PIND7

#define RED_LED_DIRECTION DDRD
#define RED_LED_PORT PORTD
#define RED_LED_PIN PIND6


void button_init();
unsigned char * toCharArray(unsigned int number);
void activate_LED(bool error);
void DDR_init();


#endif /* MAIN_H_ */