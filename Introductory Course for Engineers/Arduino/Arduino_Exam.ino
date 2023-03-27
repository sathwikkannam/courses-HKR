
const int LEFT_LED = 3;
const int RIGHT_LED = 2;
const int THRESHOLD = 200;
int lap_counter = 0;

/* Declare and initialize a char array of the sensors */
char sensors[5] = {A0, A1, A2, A3, A7};

/* Declare an int array to be used for storing raw sensor data */
int sensor_values[5];

/*Used to store the normalized_ sensor values*/
int normalized_sensor_values[5];

/*Used to find the maximum value (over white surface) for each sensor*/
int max_sensor_values[5] = {0, 0, 0, 0, 0};

/*Used to find the minimum value (over black surface) for each sensor*/
int min_sensor_values[5] = {1023, 1023, 1023, 1023, 1023};


/* Declare and initialize const int for motors */
const int LEFT_MOTOR_DIR = 4;      /* Control pin */
const int RIGHT_MOTOR_DIR = 7;     /* Control pin */
const int LEFT_MOTOR_SPEED = 5;    /* Set motor speed */
const int RIGHT_MOTOR_SPEED = 6;

/* Set motor speed */

/* Entry point of program */


void setup() {
    Serial.begin(9600);

  /* Declare your sensor pin here! */
  for(int i = 0; i < 5; i++) {
    pinMode(sensors[i], INPUT);
  }    

  /*Declare your sensors input as analogRead */

 
  /* Declare your LED pin here, LEFT_LED and RIGHT_LED! */
  pinMode(LEFT_LED, OUTPUT);
  pinMode(RIGHT_LED, OUTPUT);

  /* Declare your motor pins here, control pins and motor speed pins! */
  pinMode(LEFT_MOTOR_SPEED, OUTPUT);
  pinMode(RIGHT_MOTOR_SPEED, OUTPUT);
  pinMode(LEFT_MOTOR_DIR, OUTPUT);
  pinMode(RIGHT_MOTOR_DIR, OUTPUT);

  /* Set the direction here, assign values to your control pins! */
  digitalWrite(LEFT_MOTOR_DIR, LOW);
  digitalWrite(RIGHT_MOTOR_DIR, HIGH);
  

  /* Call your sensor_calibration() function here! */
sensor_calibration();

} /* End setup() */


void loop() {

  /* Use for-loop to read the analog value for each sensor here! */
for (int i = 0; i < 5; i++) {
  sensor_values[i] = analogRead(sensors[i]);
  }

  /* Call the normalize_sensor_values() function here! */
normalize_sensor_values();

  /* Call your Line Following function here! */
line_following();

} /* End loop() */


void line_following() {

  /* Implement your line following algorithm based on your Flow Chart here! */

  /* Going straight */
if (( normalized_sensor_values [2] < THRESHOLD)
  && (normalized_sensor_values [0] > THRESHOLD)
  && (normalized_sensor_values [1] > THRESHOLD)
  && (normalized_sensor_values [3] > THRESHOLD)
  && (normalized_sensor_values [4] > THRESHOLD)){

  analogWrite(LEFT_MOTOR_SPEED, 200);
  analogWrite(RIGHT_MOTOR_SPEED, 200);
} 

  /* Turning BIG left */
else if (( normalized_sensor_values [0] < THRESHOLD)
  && (normalized_sensor_values [1] > THRESHOLD)
  && (normalized_sensor_values [2] > THRESHOLD)
  && (normalized_sensor_values [3] > THRESHOLD)
  && (normalized_sensor_values [4] > THRESHOLD)){
    
  analogWrite(LEFT_MOTOR_SPEED, 0);
  analogWrite(RIGHT_MOTOR_SPEED, 80);
}

  /* Turning BIG right */
else if (( normalized_sensor_values [0] > THRESHOLD)
  && (normalized_sensor_values [1] > THRESHOLD)
  && (normalized_sensor_values [2] > THRESHOLD)
  && (normalized_sensor_values [3] > THRESHOLD)
  && (normalized_sensor_values [4] < THRESHOLD)){
    
  analogWrite(LEFT_MOTOR_SPEED, 80);
  analogWrite(RIGHT_MOTOR_SPEED, 0);
  
}

  /* Start all black */
else if (( normalized_sensor_values [0] < THRESHOLD)
  && (normalized_sensor_values [1] < THRESHOLD)
  && (normalized_sensor_values [2] < THRESHOLD)
  && (normalized_sensor_values [3] < THRESHOLD)
  && (normalized_sensor_values [4] < THRESHOLD)){
  lap_counter += 1;
  analogWrite(LEFT_MOTOR_SPEED, 200);
  analogWrite(RIGHT_MOTOR_SPEED, 200);
  delay(100);
  
  while (lap_counter == 2) {
  analogWrite(LEFT_MOTOR_SPEED, 0);
  analogWrite(RIGHT_MOTOR_SPEED, 0); 
  }
  }
  


  /* 0X000 */
else if (( normalized_sensor_values [0] > THRESHOLD)
  && (normalized_sensor_values [1] < THRESHOLD)
  && (normalized_sensor_values [2] > THRESHOLD)
  && (normalized_sensor_values [3] > THRESHOLD)
  && (normalized_sensor_values [4] > THRESHOLD)){
 
  analogWrite(LEFT_MOTOR_SPEED, 25);
  analogWrite(RIGHT_MOTOR_SPEED, 100);
}

  /* 000X0 */
else if (( normalized_sensor_values [0] > THRESHOLD)
  && (normalized_sensor_values [1] > THRESHOLD)
  && (normalized_sensor_values [2] > THRESHOLD)
  && (normalized_sensor_values [3] < THRESHOLD)
  && (normalized_sensor_values [4] > THRESHOLD)){
  analogWrite(LEFT_MOTOR_SPEED, 100);
  analogWrite(RIGHT_MOTOR_SPEED, 25);
}




  /* Assign left speed and right speed to your motors inside your line following algorithm! */

} /* End line_following() */



/************************************************************************
 * sensor_calibration() - Brief description of sensor calibration.
 *                        Calibration Function Used To Find Out Max-
 *                        and Min Reading Value For Each Sensor.
 * 
 ************************************************************************/
void sensor_calibration(void) {

  Serial.println("Starting The Calibration!");
  Serial.println("Place the Robot On The Black Line, with sensor A2 on the Black Line (00X00)");
  Serial.println("Press Start Button To Start To Calibrate");

  /*Turn on the left LED on the robot*/
  digitalWrite(LEFT_LED, HIGH);   

  /*Wait Until Button is Pressed*/
  while (digitalRead(10) == HIGH) {
    ; 
  }
  /*Turn on the right LED on the robot*/
  digitalWrite(RIGHT_LED, HIGH);

  /* Make Rollbot run in circles during calibration */
  analogWrite(LEFT_MOTOR_SPEED, 200);
  analogWrite(RIGHT_MOTOR_SPEED, 0);

  /***********************************************************************
  * This functionality reads 5000 values for each sensor while you move the
  * robot back and forth over the black line. Every time the new value is
  * either less than the min value or bigger than the max value for that sensor
  * it stores that value as the new min- respectively max value.
  ************************************************************************/
  for (int i = 0; i < 5000 ; i++) {
    for (int j = 0 ; j < 5; j++) {
      if (analogRead(sensors[j]) > max_sensor_values[j]) {
        max_sensor_values[j] = analogRead(sensors[j]);
        Serial.print("New max value: ");
        Serial.println(max_sensor_values[j]);
      }
      if (analogRead(sensors[j]) < min_sensor_values[j]) {
        min_sensor_values[j] = analogRead(sensors[j]);
        Serial.print("New min value: ");
        Serial.println(min_sensor_values[j]);
      }
    }
  }


  analogWrite(LEFT_MOTOR_SPEED, 0);
  analogWrite(RIGHT_MOTOR_SPEED, 0);

  /*Turn off the left LED on the robot*/
  digitalWrite(LEFT_LED, LOW);
  
  Serial.println();
  Serial.println("The obtained maximum and minimum sensor values for each sensor:");
  
  for (int i = 0; i < 5; i++) {
    if (i == 4) {
      Serial.print("A7: ");
    } else {
      Serial.print("A");
      Serial.print(i);
      Serial.print(": ");
    }
    Serial.print(max_sensor_values[i]);
    Serial.print("  ");
    Serial.println(min_sensor_values[i]);
  }
  Serial.println();
  Serial.println("Calibration Is Over!");
  Serial.println("Press Start When You Have Placed The Robot On The Racing Track");

  /*Wait Until Button is Pressed*/
  while (digitalRead(10) == HIGH) {
    ; 
  }
  
  /*Turn off the right LED on the robot*/
  digitalWrite(RIGHT_LED, LOW);   
  Serial.println("Start");

} /* End sensor_calibration() */


/************************************************************************
 * normalize_sensor_values() - Brief description of normalizing sensor
 *                             values.
 * 
 ************************************************************************/
void normalize_sensor_values(void) {

  /***********************************************************************
  * This functionality will take your sensorvalue and will map it to a value
  * between 0 and 1023 depending on the minimum- respectively maximum value
  * obtained from the Calibration Function.  In case the sensor value becomes
  * less or greater than the obtained minimum- and maximum value you will
  * get a normalized_ value which is <0 or >1023. In this case you set your
  * value to 0 respectively 1023
  ***********************************************************************/
  for (int i = 0; i < 5; i++) {
    normalized_sensor_values[i] = map(sensor_values[i], min_sensor_values[i], max_sensor_values[i], 0, 1023);
    if (normalized_sensor_values[i] < 0) {
      normalized_sensor_values[i] = 0;
    } else if (normalized_sensor_values[i] > 1023) {
      normalized_sensor_values[i] = 1023;
    }
  }
  
} /* End normalize_sensor_values() */

/* End Exam_template2021.ino */
