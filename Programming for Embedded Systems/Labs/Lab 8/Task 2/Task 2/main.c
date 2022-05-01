/*
 * Task 2.c
 *
 * Created: 2022-05-01 15:20:18
 * Author : SAKA0191
 */ 

#include <avr/io.h>
#include <avr/interrupt.h>

void conversion_init();

volatile unsigned int dutyCycle;

int main(void)
{
	
	
	//TIMER 0 CONFIGURATION.
	DDRD |= (1<<PORTD6); //Set pin 6 in DDRD as output.
	TCCR0A |= (1<<COM0A1) | (1<<WGM00) | (1<<WGM01); //Set Non-Inverting mode with COM0A1 and Fast PWM with WGM00 and WGM01 bits. (Mode 3)
	TIMSK0 |= (1<<TOIE0); // Enable overflow interrupt.
	
	
	//ADC CONFIGURATION.
	ADMUX = (1<<REFS0) | (1<<MUX1); //Set VREF of AVCC. ADC2 = MUX1
	ADCSRA = (1<<ADEN) | (1<<ADIE) | (1<<ADPS2) | (1<<ADPS1) | (1<<ADPS0); //Enable ADC. Enable ADC interrupt. 128 Prescaler.
	DIDR0 = (1<<ADC2D); //Disable digital input buffer for ADC2 pin.
	conversion_init();
	
	sei(); // Enable global interrupts.
	TCCR0B |= (1<<CS00); //Set Prescaler of 1 (No Prescaler) and Start Timer.
		
    while (1) 
    {
    }
}


void conversion_init(){
	ADCSRA |= (1<<ADSC); // Enable Start conversion bit.
}


ISR(TIMER0_OVF_vect){
	OCR0A = dutyCycle;
}


ISR(ADC_vect){
	dutyCycle = ADC; //Set dutyCycle to the ADC value
	conversion_init(); //Restart conversion.
}
