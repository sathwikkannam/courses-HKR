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
	