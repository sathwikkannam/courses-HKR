//
// Created by Sathwik kannam on 3/17/2023.
//


#include "headers/servo.h"

void servo_init(void) {
    // Set Servo pin as OUTPUT.
    SERVO_DDRx |= _BV(SERVO_PIN);

    // Timer 1 - PWM - Mode 14 - PRESCALER 8 - 50 Hz
    TCCR1A |= _BV(WGM11) | _BV(COM1A1);
    TCCR1B |= _BV(WGM13) | _BV(WGM12) | _BV(CS11);
    ICR1 = TOP;
}


void set_angle(int angle) {
    //			   (SERVO_MAX - SERVO_MIN)
    // SERVO_MIN + ----------------------- x angle
    //                        180
    SERVO_OCnx = SERVO_MIN + ((int) RANGE * angle);
}
