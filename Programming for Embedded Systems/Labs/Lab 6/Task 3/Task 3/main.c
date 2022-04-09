/*
 * Task 3.c
 *
 * Created: 2022-04-08 18:00:56
 * Author : SAKA0191
 */ 

#include <avr/io.h>
#include <avr/delay.h>
#include <stdbool.h>

#define F_CPU 16000000

bool isPressed(void);
bool isLED = false;

int main(void)
{
    /* Replace with your application code */
	
	DDRB = 0b00000010;
	
    while (1) 
    {
		
		if(isPressed() && !isLED){
			PORTB ^= (1<<1);
			isLED = true;
		}else if(isPressed() && isLED){
			PORTB = 0x00;
			isLED = false;
		}
		_delay_ms(150);

    }
	
	return 0;
}

bool isPressed(void){
	bool returnVal = false;
	
	if ((PINB & (1<<0))){
		_delay_ms(25);
		
		if ((PINB & (1<<0))){
			returnVal = true;
		}
	}
	
	return returnVal;
	
}

