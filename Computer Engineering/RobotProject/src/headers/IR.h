//
// Created by Sathwik kannam on 3/14/2023.
//

#ifndef ROBOTPROJECT_IR_H
#define ROBOTPROJECT_IR_H

#include <avr/io.h>
#include <avr/sfr_defs.h>

#include "headers/adc.h"

#define sensor_l ADC5D
#define sensor_c ADC4D
#define sensor_r ADC3D

#define sensor_bl ADC1D
#define sensor_br ADC2D


void initIRSensors();
uint16_t getTrackDirection();

#endif //ROBOTPROJECT_IR_H
