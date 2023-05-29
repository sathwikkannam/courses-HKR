/*
 * Temperature Node.c
 *
 * Created: 5/29/2023 8:08:44 PM
 * Author : Sathwik kannam
 */ 

#include <avr/io.h>
#include "usart.h"
#include <util/delay.h>
#include <string.h>

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


void tx_frame(uint8_t * destination_64, uint8_t * msg);
void arrayCopy(uint8_t * from, uint8_t * to, int length, int offset);

int main() {
	int temperature = 200;
	
	// Set LED is as OUTPUT
	LED_DDRx |= (1<<LED_PIN);
	
	// Initialize USART
	usart_init();
	
	// 64-bit (mac) address of coordinator.
	uint8_t coordinatorAddress[] = {0x00, 0x13, 0xA2, 0x00, 0x41, 0xC7, 0x20, 0x1C};
	
	// This is the converted uint8_t array of int temperature.
	uint8_t temp_char[11];
	
	while(1){
		if(usart_recieve() == START_DELIMITER){
			_delay_ms(500);
			
			// Turn on LED.
			LED_PORTx |= (1<<LED_PIN);
			
			// Converts int to char array.
			itoa(temperature, temp_char, 10);
			
			// Transmits frame with temperature as message.
			tx_frame(coordinatorAddress, temp_char);
			
			_delay_ms(1000);
			
			// Turn off LED.
			LED_PORTx &= ~(1<<LED_PIN);
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