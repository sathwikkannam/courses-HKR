/*
 * adc.c
 *
 * Created: 2022-05-15 17:56:58
 *  Author: SAKA0191
 */ 


#include "adc.h"

//Initialize ADC conversion based on the mode by changing the channels. 
void adc_init(unsigned int mode){
	ADMUX = (1<<REFS0); //Set VREF to AVCC (+5V). 
	ADCSRA = (1<<ADEN) | (1<<ADIE) | (1<<ADPS2) | (1<<ADPS1) | (1<<ADPS0); //Enable ADC. Enable ADC conversion complete interrupt. 128 Prescaler.
		
	if(mode == 0){
		ADMUX |= (1<<ADC2_MUX); //Enable MUX1 to listen to ADC2.
		DIDR0 = (1<<ADC2D); //Disable digital input buffer for ADC2 pin.
	}else if(mode == 1){
		ADMUX |= (1<<ADC1_MUX); //Enable MUX1 to listen to ADC1.
		DIDR0 = (1<<ADC1D); //Disable digital input buffer for ADC1 pin.
	}else if(mode == 2){
		DIDR0 = (1<<ADC0D); //Disable digital input buffer for ADC0 pin.
	}
		
}

void conversion_init(){
	ADCSRA |= (1<<ADSC); // Enable Start conversion bit.
}



