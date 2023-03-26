/*
 * Task 1.c
 *
 * Created: 2022-05-01 13:36:34
 * Author : SAKA0191
 */

#define F_CPU 16000000UL

#include <avr/io.h>

volatile unsigned int dutyCycle = 80; //duty cycle of 80%, 50%, 40%, and 20%.

int main(void) {
    DDRB |= (1 << PORTB3); //Set pin 3 in DDRB as output (OC2A pin).
    TCCR2A |= (1 << COM2A1) | (1 << WGM21) | (1 << WGM20); //Set Non-Inverting mode with COM2A1 and Fast PWM with WGM21 and WGM20 bits. (Mode 3)
    OCR2A = (dutyCycle * 255) / 100; //Set TOP value to a fraction of maximum value.
    TCCR2B |= (1 << CS20); //Set no Prescaler.


    while (1) {


    }
}