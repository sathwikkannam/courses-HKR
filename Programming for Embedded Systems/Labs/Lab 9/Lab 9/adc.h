/*
 * adc.h
 *
 * Created: 2022-05-15 17:57:13
 *  Author: SAKA0191
 */ 


#ifndef ADC_H_
#define ADC_H_


#define ADC2_MUX MUX1
#define ADC1_MUX MUX0


void adc_init(unsigned int mode);
void conversion_init();



#endif /* ADC_H_ */