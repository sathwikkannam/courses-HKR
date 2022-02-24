;Write a program in Assembler that compares one integer value with the value of 50. 
;If it is bigger than 50, turn on the green LED, else turn on red the LED.
start:
	LDI R17, 0b00000011
	STS 0x24, R17

	LDI R19, 55
	LDI R18, 50
	CP R19, R18
	BRGE great
	rjmp less

great:
	LDI R17, 0b00000001
	STS 0x25, R17
	rjmp loop

less:
	LDI R17, 0b00000011
	STS 0x24, R17

	LDI R17, 0b00000010
	STS 0x25, R17
	rjmp loop

loop:
	rjmp loop