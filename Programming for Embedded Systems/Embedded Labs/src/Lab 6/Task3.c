/*
 * Task 3.c
 *
 * Created: 2022-04-08 18:00:56
 * Author : SAKA0191
 */

#define F_CPU 16000000UL //16MHz

#include <avr/io.h>
#include <avr/delay.h>
#include <stdbool.h>

bool isPressed(void);

volatile bool isLED = false;

int main(void) {

    DDRB = 0xFFu; // set PORTB as output.
    DDRB &= ~(1 << 0); //unset pin 0 to input.

    while (1) {

        if (isPressed() && !isLED) { //if button pressed and led is not turned on.
            PORTB ^= (1 << 1); //5V to pin 1.
            isLED = true; //set led is turned on.
        } else if (isPressed() && isLED) { //if button pressed and led is turned on.
            PORTB = 0x00; //0V to PORTB.
            isLED = false; //set led is turned off.
        }
        _delay_ms(25); //delay 35ms to reduce flickering.

    }

    return 0;
}

bool isPressed(void) {
    bool pressed = false;

    while (!(PINB & (1 << 0))) { //button pressed?
        pressed = true;
        break;
    }

    _delay_ms(35);

    return pressed;

}