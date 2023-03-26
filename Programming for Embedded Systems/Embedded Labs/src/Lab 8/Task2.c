/*
 * Task 2.c
 *
 * Created: 2022-05-01 15:20:18
 * Author : SAKA0191
 */

#include <avr/io.h>
#include <avr/interrupt.h>

void conversion_init();

void map_to_8bits(uint16_t adc);

volatile unsigned int dutyCycle;

int main(void) {


    //TIMER 2 CONFIGURATION.
    DDRB |= (1 << PORTB3); //Set pin 3 in DDRB as output (0C2A).
    TCCR2A |= (1 << COM2A1) | (1 << WGM20) | (1 << WGM21); //Set Non-Inverting mode with COM2A1 and Fast PWM with WGM20 and WGM21 bits. (Mode 3)

    //ADC CONFIGURATION.
    ADMUX = (1 << REFS0) | (1 << MUX1); //Set VREF to AVCC (+5V). Enable MUX1 to listen to ADC2.
    ADCSRA = (1 << ADEN) | (1 << ADIE) | (1 << ADPS2) | (1 << ADPS1) | (1 << ADPS0); //Enable ADC. Enable ADC conversion complete interrupt. 128 Prescaler.
    DIDR0 = (1 << ADC2D); //Disable digital input buffer for ADC2 pin.
    conversion_init(); //Begin conversion.

    sei(); // Enable global interrupts.
    TCCR2B |= (1 << CS20); //Set no Prescaler, and Start Timer.

    while (1) {
        OCR2A = dutyCycle;

    }
}


void conversion_init() {
    ADCSRA |= (1 << ADSC); // Enable Start conversion bit.
}


void map_to_8bits(uint16_t adc) {
    dutyCycle = adc / 4; //Convert adc to 8-bit and set it to dutyCycle.


}


ISR(ADC_vect) {
    map_to_8bits(ADC); //Map ADC, a 10-bit value to 8-bit.
    conversion_init(); //Restart conversion.
}