.include "m328pdef.inc"


    LDI  r18, 21 ;Load R18 with 21
    LDI  r19, 219 ; LOAD R19, 197

delay: 
	DEC  r19 ; DEC from 197 to 0 first time, then from 256 for 20 times.
    BRNE delay ;Branch to L1 until R19 is 0.
    DEC  r18 ; DEC from 21 to 0
    BRNE delay ; Branch until R18 is 0
    RJMP end ; exit the 1ms delay loop

end:
	RJMP end


