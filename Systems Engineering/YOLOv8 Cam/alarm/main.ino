// Pin definitions
const int alarmPin = 7; // Connect the alarm to digital pin 9

// Variables
bool alarmActive = false; // Flag to track alarm activation
unsigned long lastAlarmTime = 0; // Timestamp of last alarm activation
const int reactivation_delay = 15000; // The interval between each activation.
const int activation_time = 10000; // Duration of each activation

void setup() {
    pinMode(alarmPin, OUTPUT);
    Serial.begin(9600); // Initialize UART communication
}

void loop() {
    // Check if a message is available on UART
    if (Serial.available()) {
        String receivedMessage = Serial.readStringUntil('\n'); // Read the message
        receivedMessage.trim(); // Remove leading/trailing spaces

        // Check if the received message is "ring"
        if (receivedMessage == "ring") {
            activateAlarm();
        }
    }

    // Check if the alarm is active
    if (alarmActive) {
        unsigned long currentTime = millis();

        // Wait for 1 minute (60,000 milliseconds) before allowing alarm activation again
        if (currentTime - lastAlarmTime >= reactivation_delay) {
            alarmActive = false; // Reset alarm flag
        }
    }
}

void activateAlarm() {
    if (!alarmActive) {
        // Activate the alarm for 5 seconds
        digitalWrite(alarmPin, HIGH); // Activate the alarm
        delay(activation_time); // Wait for 10 seconds
        digitalWrite(alarmPin, 0); // Turn off the alarm
        alarmActive = true; // Set alarm flag
        lastAlarmTime = millis(); // Record the timestamp
    }
}
