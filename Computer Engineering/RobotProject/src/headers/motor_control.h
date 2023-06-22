//
// Created by Sathwik kannam on 3/14/2023.
//

#ifndef ROBOTPROJECT_MOTOR_CONTROL_H
#define ROBOTPROJECT_MOTOR_CONTROL_H

#include <avr/io.h>
#include <avr/sfr_defs.h>

#ifndef F_CPU
#define F_CPU 16000000UL
#endif

#define LEFT_MOTOR_PIN PIND5
#define RIGHT_MOTOR_PIN PIND6
#define MOTOR_PWM_TIMER_TOP 255
#define MOTORS_DDRx DDRD

#define MOTOR_MINIMUM_SPEED 0
#define MOTOR_MAXIMUM_SPEED 100

#ifdef LEFT_MOTOR_PIN
#define LEFT_MOTOR_OCnx OCR0B
#endif


#ifdef RIGHT_MOTOR_PIN
#define RIGHT_MOTOR_OCnx OCR0A
#endif

#define PRESCALER 8

void start_timer(void);

void set_speed(int left_motor_speed, int right_motor_speed);

void motors_init(void);


#endif //ROBOTPROJECT_MOTOR_CONTROL_H
