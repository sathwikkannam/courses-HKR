;
; Assembler.asm
;
; Created: 2022-02-09 13:51:50
; Author : SAKA0191
;


.INCLUDE "m48def.inc"

LDI R16, 3 
LDI R17, 10 
LDI R18, 2 
MOV R2, R18 
MOV R1, R17 
 
START: 
  ADD R1, R2 
  DEC R16 
  BRNE start 
  OUT PORTD, R1 

  FOREVER: 
   RJMP FOREVER 
 
