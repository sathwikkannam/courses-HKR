/*
 * Task 3.c
 *
 * Created: 2022-04-08 18:00:56
 * Author : SAKA0191
 */ 

#include <avr/io.h>
#include <avr/delay.h>
#include <stdbool.h>

#define F_CPU 16000000UL

bool isPressed(void);
bool isLED = false;

int main(void)
{
    /* Replace with your application code */
	
	DDRB = 0b00000010; //set pin 1 as output and pin 0 as input
	
    while (1) 
    {
		
		if(isPressed() && !isLED){ //if button pressed and led is not turned on.
			PORTB ^= (1<<1); //5V to pin 1.
			isLED = true; //set led is turned on.
		}else if(isPressed() && isLED){ //if button pressed and led is turned on.
			PORTB = 0x00; //0V to PORTB.
			isLED = false; //set led is turned off.
		}
		_delay_ms(150); //delay 150ms.

    }
	
	return 0;
}

bool isPressed(void){
	bool returnVal = false;
	
	if ((PINB & (1<<0))){ //button pressed?
		_delay_ms(25); //delay 25
		
		if ((PINB & (1<<0))){ //check again.
			returnVal = true; //its pressed.
		}
	}
	
	return returnVal;
	
}

