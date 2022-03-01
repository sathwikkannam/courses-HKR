.include "m328pdef.inc"
 
;Create 1ms delay
;formula: 3(x+y) = #cycles
;where x and y are values of the registers
;while not exceeding a 8-bit values (max: 255)
LDI R16, 133
LDI R17, 200

START: 
  DEC R16 ; the cycle counter for this step = R16 value
  BRNE start ; R16*2-1 (the -1 is the last decrement)

next:
	DEC R17 ;same logic as start function
	BRNE next ;same logic as start function
	rjmp loop ; the rjmp loop is for compensating for 2 additional cycles. 

loop: 
	rjmp loop
	;this is not included in the total time
	;it is just to make a stop function
	

;; if frequency is 8MHZ

LDI R16, 255
LDI R17, 255
LDI R18, 255
LDI R19, 255
LDI R20, 255
LDI R21, 255
LDI R22, 255
LDI R23, 255
LDi R24, 255
LDi R25, 255
LDi R26, 117


START:
    DEC R16
    BRNE START

next:
	DEC R17
	BRNE next
zx:

	DEC R18
	BRNE zx
a:

	DEC R19
	BRNE a
b:

	DEC R20
	BRNE b
c:

	DEC R21
	BRNE c
d:

	DEC R22
	BRNE d
e:

	DEC R23
	BRNE e
f:

	DEC R24
	BRNE f
g:

	DEC R25
	BRNE g
h:

	DEC R26
	BRNE h

loop:
	rjmp loop
