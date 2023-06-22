//
// Created by Sathwik kannam on 3/14/2023.
//

#ifndef ROBOTPROJECT_UART_H
#define ROBOTPROJECT_UART_H

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


void uart_init(void);

void uart_send_char(uint8_t data);

void uart_send_8bit(uint8_t data);

void uart_send_16bit(uint16_t data);

uint8_t uart_recieve();


#endif //ROBOTPROJECT_UART_H
