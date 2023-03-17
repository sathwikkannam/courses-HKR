//
// Created by Sathwik kannam on 3/14/2023.
//

#ifndef ROBOTPROJECT_USART_H
#define ROBOTPROJECT_USART_H

#include <avr/io.h>
#include <stdlib.h>
#include <avr/sfr_defs.h>
#include <avr/interrupt.h>

#ifndef F_CPU
#define F_CPU 16000000UL
#endif
#define BAUD 9600
#define MYUBRR F_CPU/16/BAUD-1

volatile uint8_t command;


void usart_init(void);
void usart_send_char(uint8_t data);
void usart_send_8bit(uint8_t data);
void usart_send_16bit(uint16_t data);
uint8_t usart_recieve();


#endif //ROBOTPROJECT_USART_H
