/*
 * Lab 9.c
 *
 * Created: 2022-05-15 16:40:31
 * Author : SAKA0191
 */ 


#include "main.h"

volatile unsigned int mode = 0;
volatile bool isError = false;
volatile uint8_t adc_string[4];

int main(void)
{

	DDR_init(); //Initialize LED and Button registers.
	button_init(); //Initialize Button.
	lcd_init(); //Initialize LCD.
	sei(); //Enable global interrupts.
	
    while (1) 
    {
		activate_LED(isError);	
    }
}


void DDR_init(void){
	BUTTON_DIRECTION ^= (1<<PIND3); //Set Button as Output.
	RED_LED_DIRECTION |= (1<<RED_LED_PIN); //Set Red LED as Output.
	BLUE_LED_DIRECTION |= (1<<BLUE_LED_PIN); //Set Blue LED as Output.

}


void button_init(void){
	EICRA |= (1<<ISC01) | (1<<ISC00); //Rising edge for INT0 Pin.
	EIMSK |=(1<<BUTTON_INTERRUPT); //Set external interrupt for PIND 2.
	_delay_ms(50); //Delay 25 milliseconds.
	
}


void activate_LED(bool error){
	if(error){
		RED_LED_PORT ^= (1<<RED_LED_PIN); //Toggle Red LED.
		BLUE_LED_PORT &= ~(1<<BLUE_LED_PIN); //Unset Blue LED.
	}else{
		RED_LED_PORT &= ~(1<<RED_LED_PIN); //Unset Red LED.
		BLUE_LED_PORT ^= (1<<BLUE_LED_PIN); //Toggle Blue lED.
	}
}


ISR(INT0_vect){
	++mode;
	
	if(mode > 3){
		mode = 1; //Reset mode to volt meter.
	}
	
	adc_init(mode); //Initialize ADC conversion.
	conversion_init(); //Start conversion
}


ISR(ADC_vect){	
	//Determine if it is considered as Error.
	isError = (ADC == MAX_ADC_VALUE)? true : false;
	
	lcd_putcmd(FIRST_ROW_POSITION_1); //Point cursor to first row.
	if(mode == 1){
		lcd_puts("Voltage          ");
	}else if(mode == 2){
		lcd_puts("Humidity         ");
	}else if(mode == 3){
		lcd_puts("Light            ");
	}
	
	sprintf(adc_string, "%d", ADC); //Store buffer into a char array.
		
	lcd_putcmd(SECOND_ROW_POSITION_1); //Switch to second line
	lcd_puts(adc_string); //Display to second line.
	
	_delay_ms(1000); //Delay 1000ms
	
	conversion_init(); //Restart conversion.

}
