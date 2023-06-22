//
// Created by Sathwik kannam on 3/14/2023.
//

#include "headers/main.h"

int main(void) {
    motors_init();
    uart_init();
    initIRSensors();
    ultrasonic_init();
    servo_init();
    obstacleDetected = false;

    while (1) {
        if (command == START) {
            // wheelie!!!
            set_speed(100, 100);
            _delay_ms(200);

            while (1) {
                followTrack();

                if (command == STOP) {
                    set_speed(0, 0);
                    break;

                } else if (command == PENALTY) {
                    set_speed(0, 0);
                    _delay_ms(5000);
                }
            }
        }
    }

}


void followTrack(void) {
    int track_dir = (int) getTrackDirection();

    if (track_dir == 1001) {
        set_speed(0, 0);
        return;
    }

    int leftMotorSpeed;
    int rightMotorSpeed;

    if (track_dir != 0 && track_dir != 1000) {
        set_angle(track_dir / 10 + 40);

        // the closer to 500, scalar moves closer to 6 -> higher speed
        float middleDist = (float) track_dir - 500;
        if (middleDist < 0)
            middleDist *= -1;
        // range from [0 ~ 500]: 0 -> 7, 500 -> 13
        float scalar = (middleDist / 100.0) + 7;
        if (scalar <= 7.2)
            scalar -= 0.8;
        //free(&middleDist);

        leftMotorSpeed = (int) (((float) (1000 - track_dir)) / scalar);
        rightMotorSpeed = (int) (((float) track_dir) / scalar);
        //free(&scalar);

        if (pulse_width > 5 && pulse_width < 60) {
            obstacleDetected = true;
            // brake because obstacle is close
            // 60 -> 1
            // 5 -> 50 something

            int breakMultiplier = -1 * pulse_width;
            breakMultiplier += 70;    // 60 -> 10, 5 -> 65

            leftMotorSpeed -= breakMultiplier;
            rightMotorSpeed -= breakMultiplier;

            if (leftMotorSpeed < 0)
                leftMotorSpeed = 0;
            if (rightMotorSpeed < 0)
                rightMotorSpeed = 0;
        } else if (obstacleDetected) {
            set_speed(80, 80);
            obstacleDetected = false;
        }
    } else {
        if (track_dir == 0) {
            set_angle(90);
            leftMotorSpeed = 99;
            rightMotorSpeed = 30;
        } else {
            set_angle(90);
            leftMotorSpeed = 0;
            rightMotorSpeed = 99;
        }
    }
    set_speed(leftMotorSpeed, rightMotorSpeed);
}