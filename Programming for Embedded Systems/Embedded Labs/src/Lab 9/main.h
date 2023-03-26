/*
 * main.h
 *
 * Created: 2022-05-15 18:16:19
 *  Author: SAKA0191
 */


#ifndef EMBEDDED_LABS_MAIN_H
#define EMBEDDED_LABS_MAIN_H


/*
--------------------SETUP-------------------------------------------
		MUX REGISTER LAYOUT:
		—————————————————————————————
		| MUX3 | MUX2 | MUX1 | MUX0 |
		—————————————————————————————


		MODE          |  PIN   | MUX 3 -> 0
		——————————————|————————|———————————
	0.	VOLT METER    |  ADC2  | 0010
	1.	SOIL HUMIDITY |  ADC1  | 0001
	2.	LIGHT METER   |  ADC0  | 0000


		INSTRUMENT    |  PIN   | Direction register
		——————————————|————————|———————————————————
		BUTTON        |	 PIND2 | DDRD
		RED LED       |  PIND7 | DDRD
		BLUE LED      |  PIND6 | DDRD


Connection Diagram:
                 -----------                   ----------
                |ATmega328p |                 |   LCD    |
                |           |                 |          |
                |         VC|---------------->|A         |
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

#define F_CPU 16000000UL

#include <avr/io.h>
#include <avr/interrupt.h>
#include <util/delay.h>
#include <stdlib.h>
#include <stdbool.h>
#include "Utils/lcd.h"
#include "adc.h"

#define BUTTON_DIRECTION DDRD
#define BUTTON_PIN PIND3
#define BUTTON_PORT PORTD
#define BUTTON_INTERRUPT INT0

#define BLUE_LED_DIRECTION DDRD
#define BLUE_LED_PORT PORTD
#define BLUE_LED_PIN PIND6

#define RED_LED_DIRECTION DDRD
#define RED_LED_PORT PORTD
#define RED_LED_PIN PIND7

#define MAX_ADC_VALUE 1024


void button_init(void);
void activate_LED(bool error);
void DDR_init(void);

#endif //EMBEDDED_LABS_MAIN_H
