/*
 * Lab 9.c
 *
 * Created: 2022-05-15 16:40:31
 * Author : SAKA0191
 */ 


#include "main.h"

int main(void)
{
	DDR_init();
	button_init(); //Initialize button.
	lcd_init(); //Initialize LCD.
	sei(); //Enable global interrupts (Set I bit in SREG).
	
    while (1) 
    {
				
    }
}


void DDR_init(){
	BUTTON_DIRECTION |= (1<<BUTTON_PIN); //Set Button as Output.
	RED_LED_DIRECTION | = (1<<RED_LED_PIN); //Set Red LED as Output.
	BLUE_LED_DIRECTION |= (1<<BLUE_LED_PIN); //Set Blue LED as Output.
}


void button_init(){
	EICRA |= (1<<ISC01) | (1<<ISC00); //Rising edge of INT0.
	EIMSK |=(1<<BUTTON_INTERRUPT); //Set external interrupt for PIND 2.
	_delay_ms(25); //Delay 25 milliseconds.
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


unsigned char * toCharArray(unsigned int number){
	char * numberArray = calloc(log10(number) + 1, sizeof(char));
	
	for (int i = log10(number); i >= 0; --i, number /= 10){
		numberArray[i] = (number % 10) + '0';
	}
	
	return numberArray;
}


ISR(INT0_vect){
	mode = (mode > 2)? 0 : mode++; //Increment or reset mode.
	adc_init(mode); //Initialize ADC conversion.
	conversion_init();	//Start Conversion 
}


ISR(ADC_vect){
	
	//Print mode/instrument name in the first row.
	lcd_putcmd(LCD_SET_FRIST_LINE);
	if(mode == 0){
		lcd_puts("Voltmeter");
	}else if(mode == 1){
		lcd_puts("Soilmeter");
	}else if(mode == 2){
		lcd_puts("Lightmeter");
	}
	
	if(ADC > 255 | (mode == 0 && ADC == 0))){
		isError = true;
	}else{
		isError = false;
	}
	
	//Print quantity in the second row.
	lcd_putcmd(LCD_SET_SECOND_LINE); //Switch to second line
	lcd_puts(toCharArray(ADC/(4*5))); //Display to second line. Convert 8-bit ADC to percentage, where 5V is reference voltage.
	
	activate_LED(isError);

}

