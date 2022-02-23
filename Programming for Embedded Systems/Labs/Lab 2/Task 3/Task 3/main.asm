;
; Task 3.asm
;
; Created: 2022-02-23 14:11:05
; Author : SAKA0191
;


; Replace with your application code
.include "m328pdef.inc"

start:
	LDI R17, 0b00001111 
	STS 0x24, R17 ;; DDRB OUTPUT

	LDI R18, 0b11110000 
	STS 0x2A, R18 ;; DDRD OUTPUT

	LDI R20, 1
	LDI R21, 2
	LDI R22, 3
	LDI R23, 4
	LDI R24, 5
	LDI R25, 6
	LDI R26, 7
	LDI R27, 8
	LDI R28, 9
	LDI R29, 0

	LDI R19, 0
	LDI R16, 0
	ADD R19, R16  ;; MUL R19, R16 or SUB R19, R16
	CP R19, R28
	BRLO equal_or_lower
	BREQ equal_or_lower
	BRGE not_equal_or_higher

equal_or_lower:
	CP R19, R29
	BREQ not_equal_or_higher
	CP R19, R20
	BREQ turn_on_one
	CP R19, R21
	BREQ turn_on_two
	CP R19, R22
	BREQ turn_on_three
	CP R19, R23
	BREQ turn_on_four
	CP R19, R24
	BREQ turn_on_five
	CP R19, R25
	BREQ turn_on_six
	CP R19, R26
	BREQ turn_on_seven
	CP R19, R27
	BREQ turn_on_eight
	CP R19, R28
	BREQ turn_on_nine

not_equal_or_higher: ;show 0 if higher of greater 
	LDI R17, 0b00001101
	STS 0x25, R17

	LDI R18, 0b11110000
	STS 0x2B, R18
	
	
turn_on_one:
	LDI R17, 0b00001001
	STS 0x25, R17
	rjmp loop

turn_on_two:
	LDI R17, 0b0001110
	STS 0x25, R17

	LDI R18, 0b11000000
	STS 0x2B, R18
	rjmp loop

turn_on_three:

	LDI R17, 0b0000110
	STS 0x25, R17

	LDI R18, 0b11110000
	STS 0x2B, R18
	rjmp loop

turn_on_four:
	LDI R17, 0b00000011
	STS 0x25, R17

	LDI R18, 0b01100000
	STS 0x2B, R18
	rjmp loop

turn_on_five:
	LDI R17, 0b00000111
	STS 0x25, R17

	LDI R18, 0b10110000
	STS 0x2B, R18
	rjmp loop

turn_on_six:
	LDI R17, 0b00001111
	STS 0x25, R17

	LDI R18, 0b10110000
	STS 0x2B, R18
	rjmp loop

turn_on_seven:
	LDI R17, 0b00000000
	STS 0x25, R17

	LDI R18, 0b11110000
	STS 0x2B, R18
	rjmp loop

turn_on_eight:

	LDI R17, 0b00001111
	STS 0x25, R17

	LDI R18, 0b11110000
	STS 0x2B, R18
	rjmp loop

turn_on_nine:
	LDI R17, 0b00000111
	STS 0x25, R17

	LDI R18, 0b11110000
	STS 0x2B, R18
	rjmp loop

loop: ;; loop to prevent jumping to random registers.
	rjmp loop