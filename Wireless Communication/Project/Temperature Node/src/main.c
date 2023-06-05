//
// Created by Sathwik kannam on 6/5/2023.
//

#include "main.h"


// Temperature Node
// This node transmits the temperature when requested.

// Functions
void tx_frame(uint8_t * destination_64, uint8_t * msg);
void arrayCopy(uint8_t * from, uint8_t * to, int length, int offset);

int main() {
    // Temperature
    int temperature = 5;

    // Initialize UART
    uart_init();

    // 64-bit (mac) address of coordinator.
    uint8_t coordinatorAddress[] = {0x00, 0x13, 0xA2, 0x00, 0x41, 0xC7, 0x20, 0x1C};

    // This is the converted uint8_t array of int temperature.
    uint8_t temp_char[11];



    /*
        Read from UART byte by byte.
        When we received three bytes, the frame length (n) can be calculated.
        So, we poll 'n' times, and one additional byte for the checksum.


        Format of 0x81 Rx (Receive) Frame:

        {
            START DELIMITER - 0x7E
            Frame Length (byte 1)
            Frame Length (byte 2)
            Frame Type - 0x81 for 16-bit addressing.
            Source address - 16-bit address of source.
            RSSI
            OPTIONS
            RF Data - Payload of the frame.
            Checksum - Excluding frame length, frame delimiter, frame type.

        }

    */

    while(1){
        uint8_t frame[256];
        frame[0] = START_DELIMITER;
        uint16_t length = 0;
        int sizeOfFrame = 0;


        // Capture the full frame based on the specified frame length.
        if(uart_receive() == START_DELIMITER){
            frame[1] = uart_receive();
            frame[2] = uart_receive();
            length = (((frame[1] & 0xFF) << 8) | (frame[2] & 0xFF) + 1);

            // Here we need to read one more byte for the checksum.
            for (int m = 0; m < length; m++){
                frame[m + 3] = uart_receive();
            }
        }

        sizeOfFrame = length + 3;

        // Payload of the frame.
        uint8_t rfData[sizeOfFrame - RX_RF_DATA_INDEX_FROM + 1];

        // Parse the frame to so 'rfData' only contains the payload.
        for (int i = RX_RF_DATA_INDEX_FROM; i < sizeOfFrame - 1; i++){
            rfData[i - RX_RF_DATA_INDEX_FROM] = frame[i];
        }


        // Here we send necessary frames based on the payload.
        if(strstr("Temperature", rfData) == 0){
            itoa(temperature, temp_char, 10);
            tx_frame(coordinatorAddress,temp_char);
        }


    }

    return 0;
}

/*
	Creates a frame with using 0x00 TX (Transmit) with 64-bit addressing frame.
	However, it is converted to 16-bit addressing when the node receives the frame.

	Format of 0x00 TX (Transmit) frame:

	{
		START DELIMITER - 0x7E
		Frame Length (byte 1)
		Frame Length (byte 2)
		Frame Type - 0x00 for 64-bit addressing.
		Frame ID - 0x00 for no acknowledgment response.
		Destination - 64-bit address of destination.
		RF Data - Payload of the frame.
		Checksum - Excluding frame length, frame delimiter, frame type.
	}

*/
void tx_frame(uint8_t * destination_64, uint8_t * msg){
    // Calculate the length of the message.
    const int msg_length = strlen((const char *) msg);

    // Create packet;
    uint8_t frame[TOTAL_FIELDS_LENGTH + msg_length];

    // Frame length
    uint16_t length = FRAME_DATA_LENGTH_WITHOUT_DATA + msg_length; // Frame length 2 bytes.

    // For checksum
    int sum = 0;

    frame[0] = START_DELIMITER;
    frame[1] = ((length >> 8) & 0xFF); // MSB
    frame[2] = (length & 0xFF); // LSB
    frame[3] = TX_FRAME_TYPE;
    frame[4] = DEFAULT_FRAME_ID;

    // Add 64-bit address to the frame.
    arrayCopy(destination_64, frame, ADDRESS_16_SIZE, DESTINATION_64_BYTE_INDEX_FROM);

    // Add options
    frame[13] = (DEFAULT_TIMEOUT | DEFAULT_APS_ENCRYPTION | DEFAULT_DISABLE_RETRIES);

    // Add message/
    arrayCopy(msg, frame, msg_length, TX_RF_DATA_INDEX_FROM);

    // Calculate checksum, the first three elements are frame delimiter, and 2 bytes of frame length.
    for (int i = 3; i < sizeof(frame); i++) {
        sum += frame[i];
    }

    // Add checksum
    frame[sizeof(frame) - 1] = (0xFF - (sum & 0xFF));

    // Transmit data
    for (int i = 0; i < sizeof(frame); i++) {
        uart_send_char(frame[i]);
    }

}

void arrayCopy(uint8_t * from, uint8_t * to, int length, int offset){
    for (int i = 0; i < length; i++){
        to[i + offset] = *from++;
    }

}