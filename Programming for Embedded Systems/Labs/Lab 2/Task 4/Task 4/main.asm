
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
.equ PB            =               0x25 ;PORTB
.equ PD            =               0x2B ;PORTD

LDI R20, zero
LDI R21, one
LDI R22, two
LDI R23, three
LDI R24, four
LDI R25, five
LDI R26, six
LDI R27, seven
LDI R28, eight
LDI R29, nine

start:
	CP R21, R20
	BRGE a
	CP R22, R20
	BRGE b
	CP R23, R20
	BRGE c
	CP R24, R20
	BRGE d
	CP R25, R20
	BRGE e
	CP R26, R20
	BRGE f
	CP R27, R20
	BRGE g
	CP R28, R20
	BRGE h
	CP R29, R20
	BRGE i
	RJMP display
a:
	MOV R20, R21
	LDI R21, 0
	rjmp start

b:
	MOV R20, R22
	LDI R22, 0
	rjmp start
c:
	MOV R20, R23
	LDI R23, 0
	rjmp start
d:
	MOV R20, R24
	LDI R24, 0
	rjmp start
e:
	MOV R20, R25
	LDI R25, 0
	rjmp start
f:
	MOV R20, R26
	LDI R26, 0
	rjmp start
g:
	MOV R20, R27
	LDI R27, 0
	rjmp start

h:
	MOV R20, R28
	LDI R28, 0
	rjmp start

i:
	MOV R20, R29
	LDI R29, 0
	rjmp start

display:
	LDI R17, DDRB_PINS
	STS 0x24, R17

	LDI R18, DDRD_PINS 
	STS 0x2A, R18 

	CPI R20, zero
	BREQ turn_on_zero
	CPI R20, one
	BREQ turn_on_one
	CPI R20, two
	BREQ turn_on_two
	CPI R20, three
	BREQ turn_on_three
	CPI R20, four
	BREQ turn_on_four
	CPI R20, five
	BREQ turn_on_five
	CPI R20, six
	BREQ turn_on_six
	CPI R20, seven
	BREQ turn_on_seven
	CPI R20, eight
	BREQ turn_on_eight
	CPI R20, nine
	BREQ turn_on_nine

turn_on_zero:
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
	STS PD, R17

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

loop: 
	rjmp loop