;
; Task 3.asm
;
; Created: 2022-02-23 14:11:05
; Author : SAKA0191
;Write a short program that adds two integer values, where the sum is less
;than or equal to 9, and display the result on a 7-segment display

.include "m328pdef.inc"

.equ zero          =               0
.equ one           =               1
.equ two           =               2
.equ three         =               3
.equ four          =               4
.equ five          =               5
.equ six           =               6
.equ seven         =               7
.equ eight         =               8
.equ nine          =               9
.equ DDRB_PINS     =               0b00001111
.equ DDRD_PINS     =               0b11110000
.equ PB            =               0x25
.equ PD            =               0x2B

start:
	LDI R17, DDRB_PINS
	STS 0x24, R17 ;; DDRB OUTPUT

	LDI R18, DDRD_PINS
	STS 0x2A, R18 ;; DDRD OUTPUT

	LDI R19, 0 ;; R19 and R16 as inputs
	LDI R16, 0
	ADD R19, R16  ;; MUL R19, R16 or SUB R19, R16
	CPI R19, nine
	BRLO equal_or_lower
	BREQ equal_or_lower
	BRGE turn_on_zero

equal_or_lower:
	CPI R19, zero
	BREQ turn_on_zero
	CPI R19, one
	BREQ turn_on_one
	CPI R19, two
	BREQ turn_on_two
	CPI R19, three
	BREQ turn_on_three
	CPI R19, four
	BREQ turn_on_four
	CPI R19, five
	BREQ turn_on_five
	CPI R19, six
	BREQ turn_on_six
	CPI R19, seven
	BREQ turn_on_seven
	CPI R19, eight
	BREQ turn_on_eight
	CPI R19, nine
	BREQ turn_on_nine

turn_on_zero: ;show 0 if higher of zero
	LDI R17, 0b00001101
	STS PB, R17

	LDI R18, 0b11110000
	STS PD, R18
	rjmp loop

turn_on_one:
	LDI R17, 0b00001001
	STS PB, R17
	rjmp loop

turn_on_two:
	LDI R17, 0b0001110
	STS PB, R17

	LDI R18, 0b11000000
	STS PD, R18
	rjmp loop

turn_on_three:

	LDI R17, 0b0000110
	STS PB, R17

	LDI R18, 0b11110000
	STS PD, R18
	rjmp loop

turn_on_four:
	LDI R17, 0b00000011
	STS PB, R17

	LDI R18, 0b01100000
	STS PD, R18
	rjmp loop

turn_on_five:
	LDI R17, 0b00000111
	STS PB, R17

	LDI R18, 0b10110000
	STS PD, R18
	rjmp loop

turn_on_six:
	LDI R17, 0b00001111
	STS PB, R17

	LDI R18, 0b10110000
	STS PD, R18
	rjmp loop

turn_on_seven:
    LDI R17, 0b00000000
	STS PB, R17

	LDI R18, 0b11110000
	STS PD, R18
	rjmp loop

turn_on_eight:

	LDI R17, 0b00001111
	STS PB, R17

	LDI R18, 0b11110000
	STS PD, R18
	rjmp loop

turn_on_nine:
	LDI R17, 0b00000111
	STS PB, R17

	LDI R18, 0b11110000
	STS PD, R18
	rjmp loop

loop: ;; loop to prevent jumping to random registers.
	rjmp loop