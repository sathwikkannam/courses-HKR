//
// Created by Sathwik kannam on 3/17/2023.
//


#include "headers/motor_control.h"

void start_timer(void) {
    switch(PRESCALER){ // Sets MUX values based on pins.
        case 0:
            TCCR0B |= _BV(CS00);
            break;
        case 8:
            TCCR0B |= _BV(CS01);
            break;
        case 64:
            TCCR0B |= _BV(CS01) | _BV(CS00);
            break;
        case 256:
            TCCR0B |= _BV(CS02);
            break;
        case 1024:
            TCCR0B |= _BV(CS00) | _BV(CS02);
            break;
        default:
            break;
    }
}


void set_speed(int left_motor_speed, int right_motor_speed) {
    if (left_motor_speed >= MOTOR_MINIMUM_SPEED && left_motor_speed <= MOTOR_MAXIMUM_SPEED &&
        right_motor_speed >= MOTOR_MINIMUM_SPEED && right_motor_speed <= MOTOR_MAXIMUM_SPEED){


        //Set OCnx values
        LEFT_MOTOR_OCnx = (left_motor_speed * MOTOR_PWM_TIMER_TOP) / 100;
        RIGHT_MOTOR_OCnx = (right_motor_speed * MOTOR_PWM_TIMER_TOP) / 100;


    }
}

void motors_init(void) {
    //Just to make sure the pins are set as OUTPUT.
    MOTORS_DDRx |= _BV(LEFT_MOTOR_PIN) | _BV(RIGHT_MOTOR_PIN);

    //Enable PWM for PIND5 and PIND6. Set timer to FAST PWM mode.
    TCCR0A |= _BV(COM0A1) | _BV(COM0B1) | _BV(WGM00) | _BV(WGM01);

    //Start Timer by setting Prescaler.
    start_timer();
}