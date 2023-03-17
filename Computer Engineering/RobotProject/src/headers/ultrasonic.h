//
// Created by Sathwik kannam on 3/14/2023.
//

#ifndef ROBOTPROJECT_ULTRASONIC_H
#define ROBOTPROJECT_ULTRASONIC_H

#include <avr/io.h>
#include <avr/sfr_defs.h>
#include <util/delay.h>
#include <avr/interrupt.h>

#define TRIGGER_DDRx DDRB
#define TRIGGER_PORTx PORTB
#define TRIGGER_PIN PINB4
#define TRIGGER_FREQUENCY 15 // Manual trigger delay between Trigger HIGH and LOW.

#define ECHO_DDRx DDRB
#define ECHO_PORTx PORTB
#define ECHO_INTx PCINT5
#define ECHO_PIN PINB5
#define ECHO_INTERRUPT_REGISTER PCIE0

#if ECHO_INTERRUPT_REGISTER == PCIE0
#	define ECHO_PIN_CHANGE_REGISTER PCMSK0
#	define ECHO_INTx_VECTOR PCINT0_vect

#elif ECHO_INTERRUPT_REGISTER == PCIE1
#	define ECHO_PIN_CHANGE_REGISTER PCMSK1
#	define ECHO_INTx_VECTOR PCINT1_vect

#elif ECHO_INTERRUPT_REGISTER == PCIE2
#	define ECHO_PIN_CHANGE_REGISTER PCMSK2
#	define ECHO_INTx_VECTOR PCINT2_vect

#endif

// MAX Prescaler is necessary as the timer overflows even for very close obstacle.
#define TIMER2_PRESCALER 1024

#if TIMER2_PRESCALER == 0
#	define TIMER2_PRESCALER (_BV(CS20))

#elif TIMER2_PRESCALER == 8
#	define TIMER2_PRESCALER (_BV(CS21))

#elif TIMER2_PRESCALER == 32
#	define TIMER2_PRESCALER (_BV(CS21) | _BV(CS20))

#elif TIMER2_PRESCALER == 64
#	define TIMER2_PRESCALER (_BV(CS22))

#elif TIMER2_PRESCALER == 128
#	define TIMER2_PRESCALER (_BV(CS22) | _BV(CS21))

#elif TIMER2_PRESCALER == 256
#	define TIMER2_PRESCALER (_BV(CS22) | _BV(CS21))

#elif TIMER2_PRESCALER == 1024
#	define TIMER2_PRESCALER (_BV(CS22) | _BV(CS21) | _BV(CS20))
#endif

#define TIMER2_OVERFLOW 10

volatile uint16_t pulse_width = 0; // Stores the time taken to reach the receiver.
volatile uint8_t i = 0;

void ultrasonic_init();
void trigger(void);

#endif //ROBOTPROJECT_ULTRASONIC_H
