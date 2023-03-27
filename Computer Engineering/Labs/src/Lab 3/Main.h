//
// Created by Sathwik kannam on 3/27/2023.
//

#ifndef LABS_MAIN_H
#define LABS_MAIN_H

#ifndef F_CPU
#define F_CPU 16000000UL
#endif

#include <avr/io.h>
#include <util/delay.h>
#include <avr/interrupt.h>
#include "uart.h"

#define RED 3
#define YELLOW 4
#define GREEN 5
#define BUTTON PORTD2

void button_init(void);


#endif //LABS_MAIN_H
