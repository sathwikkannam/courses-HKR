/*
 * Task 1.c
 *
 * Created: 2022-04-07 16:58:30
 * Author : SAKA0191
 */

#include <avr/io.h>

void toDelay(void);

int main(void) {

    DDRB = 0x01;

    while (1) {
        PORTB = 0x01;
        toDelay();
        PORTB = 0x00;
        toDelay();
    }
}

void toDelay(void) {
    TCNT1 = 0xBDC; //set initial value of TCNT1
    TCCR1A = 0x00; //Set normal mode in TCCR1A
    TCCR1B = (1 << CS02); //Set prescaler to 256.
    while ((TIFR1 & (1 << TOV1)) == 0); //Wait for overflow flag.
    TCCR1B = 0x00; //Reset TCCR1B
    TIFR1 = (1 << TOV1); //Reset overflow flag.

}