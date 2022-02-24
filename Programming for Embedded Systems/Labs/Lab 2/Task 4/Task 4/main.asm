;
; Task 4.asm
;
; Created: 2022-02-23 15:52:29
; Author : SAKA0191
;


;Begin the program by storing ten integer values in memory, then write a function 
;that reads the ten integer values in memory and finds the position of the greatest 
;value (position between 0 and 9, where the first value is in position 0 and the last 
;value is in position 9) and display the position on the 7-segment display.


.include "m328pdef.inc"
 
LDI R20, 0
LDI R21, 1
LDI R22, 2
LDI R23, 3
LDI R24, 4
LDI R25, 5
LDI R26, 6
LDI R27, 7
LDI R28, 8
LDI R29, 9

START: 
	PUSH R24

loop:
	rjmp loop



	