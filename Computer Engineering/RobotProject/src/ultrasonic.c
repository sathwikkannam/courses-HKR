//
// Created by Sathwik kannam on 3/17/2023.
//

#include "headers/ultrasonic.h"

void ultrasonic_init() {
    // Set Trigger pin as OUTPUT, and ECHO pin as INPUT.
    TRIGGER_DDRx |= _BV(TRIGGER_PIN);
    ECHO_DDRx &= ~_BV(ECHO_PIN);

    // Enable PORTB pins to interrupt.
    PCICR |= _BV(ECHO_INTERRUPT_REGISTER);

    // Enable ECHO pin to interrupt.
    ECHO_PIN_CHANGE_REGISTER |= _BV(ECHO_INTx);

    // Enable global interrupts.
    sei();

    // Automatic mode.
    if (TIMER2_OVERFLOW != 0) {
        // Set TOP value.
        OCR2A = 255;

        // Enable CTC interrupt.
        TIMSK2 |= _BV(OCIE2A);

        // Enable CTC Mode.
        TCCR2B |= _BV(WGM12);

        // Set MAX (1024) Prescaler.
        TCCR2B |= TIMER2_PRESCALER;
    }

}


void trigger(void) {
    TRIGGER_PORTx |= _BV(TRIGGER_PIN);
    _delay_ms(TRIGGER_FREQUENCY);
    TRIGGER_PORTx &= ~_BV(TRIGGER_PIN);

}


ISR(ECHO_INTx_VECTOR) {
    // ECHO pin goes from HIGH to LOW, when signal received.
    if ((ECHO_PORTx & _BV(ECHO_PIN)) == 0) {
        pulse_width = TCNT2;

        if (pulse_width == 35) {
            pulse_width = 80;
        }

        // Start auto-triggering again.
        TCCR2B |= _BV(WGM12);

        // The ECHO pin is set HIGH when triggered. So, the timer starts here.
    } else {
        // Stop auto-triggering.
        TCCR2B &= ~_BV(WGM12);
    }

    // Reset ticks.
    TCNT2 = 0;


}

// Auto-triggering every ~ 0.19s.
ISR(TIMER2_COMPA_vect) {
    i = (i > TIMER2_OVERFLOW) ? 0 : i + 1;

    if (i == 0) {
        TRIGGER_PORTx |= _BV(TRIGGER_PIN);

    } else if (i == TIMER2_OVERFLOW) {
        TRIGGER_PORTx &= ~_BV(TRIGGER_PIN);

    }

}