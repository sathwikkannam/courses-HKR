/*
 * Task 1.c
 *
 * Created: 2022-04-07 16:58:30
 * Author : SAKA0191
 */ 

#include <avr/io.h>


int main(void)
{	
	void toDelay(void);
	DDRB = 0x01;
    while (1) 
    {
		PORTB = 0x01;
		toDelay();
		PORTB = 0x00;
		toDelay();
    }
}

void toDelay(void){
	TCNT1 = 0x85EE;
	TCCR1A = 0x00;
	TCCR1B = 0x04;
	while((TIFR1&(1<<TOV1)) == 0);
	TCCR1B = 0;
	TIFR1 = (1<<TOV1);


}

