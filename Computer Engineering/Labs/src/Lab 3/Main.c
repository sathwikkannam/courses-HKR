//
// Created by Sathwik kannam on 3/27/2023.
//

#include "Main.h"

int main(void) {
    initUART();
    DDRB = 0xFF;
    DDRD &= ~(1 << BUTTON);
    button_init();
    sei();

    while (1) {
        PORTB = (1 << RED);
    }
}


void button_init() {
    EICRA |= (1 << ISC01) | (1 << ISC00); //Rising edge for INT0 Pin.
    EIMSK |= (1 << INT0); //Set external interrupt for PIND 2.
    _delay_ms(100); //Delay 100 milliseconds.

}


ISR(INT0_vect) {
    uart_putc('A');
    _delay_ms(1300);
    PORTB = (1 << RED);
    _delay_ms(1000);
    PORTB = (1 << YELLOW) | (1 << RED);
    _delay_ms(1000);
    PORTB = (1 << GREEN);
    _delay_ms(5000);
    PORTB = (1 << YELLOW);
    _delay_ms(1000);
    PORTB = (1 << RED);
    _delay_ms(500);
    uart_putc('B');

}


