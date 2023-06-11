//
// Created by Sathwik kannam on 6/5/2023.
//

#ifndef TEMPERATURE_NODE_MAIN_H
#define TEMPERATURE_NODE_MAIN_H

#include <avr/io.h>
#include <string.h>
#include <stdlib.h>
#include "uart.h"

#ifndef F_CPU
#define F_CPU 16000000UL
#endif

#define START_DELIMITER                 0x7E
#define TX_FRAME_TYPE                   0x00
#define DEFAULT_DISABLE_RETRIES         0x00
#define DEFAULT_APS_ENCRYPTION          0x00
#define DEFAULT_TIMEOUT                 0x00
#define DEFAULT_FRAME_ID                0x00
#define DESTINATION_64_BYTE_INDEX_FROM  5    // 64-bit destination starts at index 5
#define TX_RF_DATA_INDEX_FROM           14    // Data index starts from index 14.
#define TOTAL_FIELDS_LENGTH             15    // Except data.
#define FRAME_DATA_LENGTH_WITHOUT_DATA  11    // Except length, frame type, and data.
#define RX_RF_DATA_INDEX_FROM           8
#define ADDRESS_16_SIZE                 8

void txFrame(uint8_t *destination_64, uint8_t *msg);
void arrayCopy(uint8_t *from, uint8_t *to, int start, int length, int offset);

#endif //TEMPERATURE_NODE_MAIN_H
