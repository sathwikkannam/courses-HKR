/*
 * Lab 9.c
 *
 * Created: 2022-05-15 16:40:31
 * Author : SAKA0191
 */ 


#include "main.h"

volatile unsigned int mode = 0;
volatile bool isError = false;

int main(void)
{

	DDR_init();
	button_init();
	lcd_init(); //Initialize LCD.
	sei();
	
    while (1) 
    {
		activate_LED(isError);	
    }
}


void DDR_init(void){
	DDRD ^= (1<<3); //Set Button as Output.
	RED_LED_DIRECTION |= (1<<RED_LED_PIN); //Set Red LED as Output.
	BLUE_LED_DIRECTION |= (1<<BLUE_LED_PIN); //Set Blue LED as Output.

}


void button_init(void){
	EICRA |= (1<<ISC01) | (1<<ISC00); //Rising edge for INT0 Pin.
	EIMSK |=(1<<INT0); //Set external interrupt for PIND 2.
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


unsigned char * toCharArray(unsigned int number){
	char * numberArray = calloc(log10(number) + 1, sizeof(char));
	
	for (int i = log10(number); i >= 0; --i, number /= 10){
		numberArray[i] = (number % 10) + '0';
	}
	
	return numberArray;
}

ISR(INT0_vect){
	++mode;
	
	if(mode > 3){
		mode = 1;
	}
	adc_init(mode); //Initialize ADC conversion.
	conversion_init();
	lcd_clear_row(2);	
}


ISR(ADC_vect){
	//Determine if it is considered as Error.
	isError = (ADC == MAX_ADC_VALUE)? true : false;
	
	lcd_putcmd(FIRST_ROW_POSITION_1);
	if(mode == 1){
		lcd_puts("Voltage          ");
	}else if(mode == 2){
		lcd_puts("Humidity         ");
	}else if(mode == 3){
		lcd_puts("Light            ");
	}
	
			
	lcd_putcmd(SECOND_ROW_POSITION_1); //Switch to second line
	lcd_puts(toCharArray(ADC)); //Display to second line.
	
		
	_delay_ms(1000);
	conversion_init(); //Restart conversion.

}
