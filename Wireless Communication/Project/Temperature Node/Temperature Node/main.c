/*
 * Temperature Node.c
 *
 * Created: 5/29/2023 8:08:44 PM
 * Author : Sathwik kannam
 */ 

#include <avr/io.h>
#include <util/delay.h>
#include <string.h>
#include "usart.h"

#ifndef F_CPU
#define F_CPU 16000000UL
#endif

#define LED_DDRx DDRD
#define LED_PIN PIND0
#define LED_PORTx PORTD

#define START_DELIMITER                 0x7E
#define TX_FRAME_TYPE                   0x00
#define DEFAULT_DISABLE_RETRIES         0x00
#define DEFAULT_APS_ENCRYPTION          0x00
#define DEFAULT_TIMEOUT                 0x00
#define DEFAULT_FRAME_ID                0x00
#define DESTINATION_64_BYTE_INDEX_FROM  5	// 64-bit destination starts at index 5
#define TX_RF_DATA_INDEX_FROM           14	// Data index starts from index 14.
#define TOTAL_FIELDS_LENGTH             15	// Except data.
#define FRAME_DATA_LENGTH_WITHOUT_DATA  11	// Except length, frame type, and data.
#define RX_RF_DATA_INDEX_FROM           8
#define ADDRESS_16_SIZE					8


// Temperature Node
// This node transmits the temperature when requested.


// Functions
void tx_frame(uint8_t * destination_64, uint8_t * msg);
void arrayCopy(uint8_t * from, uint8_t * to, int length, int offset);
uint8_t * parseData(uint8_t * data, int length);

// Very important, don't poll UDRO if RXCIE0 is enabled, and vice versa. Then it won't receive the full frame.

int main() {
	// Temperature
	int temperature = 5;
	
	// Set LED is as OUTPUT
	LED_DDRx |= (1<<LED_PIN);
	
	// Initialize USART
	usart_init();
	
	// 64-bit (mac) address of coordinator.
	uint8_t coordinatorAddress[] = {0x00, 0x13, 0xA2, 0x00, 0x41, 0xC7, 0x20, 0x1C};
	
	// This is the converted uint8_t array of int temperature.
	uint8_t temp_char[11];
	
	
	while(1){
		uint8_t frame[256];
		frame[0] = START_DELIMITER;
		uint16_t length = 0;
		int sizeOfFrame = 0;
		
		
		// Capture the full frame based on the specified frame length.
		if(usart_recieve() == START_DELIMITER){
			frame[1] = usart_recieve();
			frame[2] = usart_recieve();
			length = (((frame[1] & 0xFF) << 8) | (frame[2] & 0xFF) + 1);
			
			// Here we need to read one more byte for the checksum.
			for (int m = 0; m < length; m++){
				frame[m + 3] = usart_recieve();
			}
		}

		sizeOfFrame = length + 3;
		
		// Payload of the frame.
		uint8_t rfData[sizeOfFrame - RX_RF_DATA_INDEX_FROM];
		
		// Parse the frame to so 'rfData' only contains the payload.
		for (int i = RX_RF_DATA_INDEX_FROM; i < sizeOfFrame - 1; i++){
			rfData[i - RX_RF_DATA_INDEX_FROM] = frame[i];
		}
		
		
		// Here we send necessary frames based on the payload.
		if(strcmp(rfData, "Temperature")){
			itoa(temperature, temp_char, 10);
			tx_frame(coordinatorAddress,temp_char);
		}
	}

	return 0;
}


void tx_frame(uint8_t * destination_64, uint8_t * msg){
	// Calculate length of message.
	const int msg_length = strlen((const char *) msg);
	
	// Create packet;
	uint8_t packet[TOTAL_FIELDS_LENGTH + msg_length];
	
	// Frame length
	uint16_t length = FRAME_DATA_LENGTH_WITHOUT_DATA + msg_length; // Frame length 2 bytes.
	
	// For checksum
	int sum = 0;

	packet[0] = START_DELIMITER;
	packet[1] = ((length >> 8) & 0xFF); // MSB
	packet[2] = (length & 0xFF); // LSB
	packet[3] = TX_FRAME_TYPE;
	packet[4] = DEFAULT_FRAME_ID;

	// Add 64 address to packet.
	arrayCopy(destination_64, packet, ADDRESS_16_SIZE, DESTINATION_64_BYTE_INDEX_FROM);
	
	// Add options
	packet[13] = (DEFAULT_TIMEOUT  | DEFAULT_APS_ENCRYPTION | DEFAULT_DISABLE_RETRIES);
	
	// Add message to packet.
	arrayCopy(msg, packet, msg_length, TX_RF_DATA_INDEX_FROM);
	
	// Calculate checksum, the first three elements are frame delimiter, and 2 bytes of frame length.
	for (int i = 3; i < sizeof(packet); i++) {
		sum += packet[i];
	}

	// Add checksum
	packet[sizeof(packet) - 1] = (0xFF - (sum & 0xFF));

	// Transmit data
	for (int i = 0; i < sizeof(packet); i++) {
		usart_send_char(packet[i]);
	}
	
}

void arrayCopy(uint8_t * from, uint8_t * to, int length, int offset){
	for (int i = 0; i < length; i++){
		to[i + offset] = *from++;
	}
	
}