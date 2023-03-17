//
// Created by Sathwik kannam on 3/14/2023.
//

#ifndef ROBOTPROJECT_SERVO_H
#define ROBOTPROJECT_SERVO_H

#include <avr/io.h>
#include <avr/sfr_defs.h>
#include <util/delay.h>

#ifndef F_CPU
#define F_CPU 16000000UL
#endif

#define SERVO_DDRx DDRB

#ifdef SERVO_DDRx
#	define SERVO_PIN PINB1
#	define SERVO_PORT PORTB
#	define SERVO_OCnx OCR1A
#endif

#define TOP 20000
#define SERVO_MIN 950
#define SERVO_MAX 4700
#define RANGE (SERVO_MAX - SERVO_MIN)/180


void servo_init(void);
void set_angle(int angle);

#endif //ROBOTPROJECT_SERVO_H
