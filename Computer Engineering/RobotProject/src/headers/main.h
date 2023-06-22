//
// Created by Sathwik kannam on 3/17/2023.
//

#ifndef ROBOTPROJECT_MAIN_H
#define ROBOTPROJECT_MAIN_H


#include "headers/IR.h"
#include "headers/motor_control.h"
#include "headers/uart.h"
#include "headers/ultrasonic.h"
#include "headers/servo.h"

#include <stdbool.h>
#include <util/delay.h>

#define START 'A'
#define PENALTY 'B'
#define STOP 'C'

volatile bool obstacleDetected;

void followTrack(void);

#endif //ROBOTPROJECT_MAIN_H
