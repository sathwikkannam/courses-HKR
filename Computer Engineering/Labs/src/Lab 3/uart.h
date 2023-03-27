//
// Created by Sathwik kannam on 3/27/2023.
//

#ifndef LABS_UART_H
#define LABS_UART_H

#include <avr/io.h>
#include <stdlib.h>

#ifndef F_CPU
#define F_CPU 16000000UL
#endif

#define UART_BAUDRATE 9600
#define UART_BAUD_SELECT ((F_CPU / (UART_BAUDRATE * 16L))-1)


void initUART() {
    UBRR0H = (UART_BAUD_SELECT >> 8);
    UBRR0L = UART_BAUD_SELECT;
    UCSR0B |= (1 << TXEN0) | (1 << RXEN0);
    UCSR0C |= (1 << UCSZ01) | (1 << UCSZ00);
    UCSR0C &= ~(1 << UMSEL00) & ~(1 << UPM00) & ~(1 << USBS0);
}


void uart_putc(unsigned char c) {
    while (!(UCSR0A & (1 << UDRE0)));
    UDR0 = c;
}


void uart_puts(char *s) {
    while (*s) {
        uart_putc(*s++);
    }
}


void uart_puti16(uint16_t value) {
    char _buffer[6];
    itoa(value, _buffer, 10);
    uart_puts(_buffer);
}


void uart_puti16B(uint16_t value) {
    char _buffer[17];
    itoa(value, _buffer, 2);
    uart_puts(_buffer);
}

#endif //LABS_UART_H
