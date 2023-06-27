# dl-short-4
Este projeto é uma versão resumida da linguagem DL.  
Esta versao faz as análises léxica, sintática e semântica e a geração de código intermediário.  
Ela está de acordo com a gramática abaixo.  

## Gramática
* PROGRAM				::= programa ID BLOCK  
* BLOCK				::= inicio STMTS fim  
* STMTS				::= STMT; STMTS | ε  
* STMT				::= BLOCK | DECL | ASSIGN | WRITE | IF | READ | WHILE
* DECL     			::= TYPE ID  
* ASSIGN   			::= ID = EXPR  
* WHILE               ::= enquanto(EXPR)BLOCK
* WRITE				::= escreva(ID)  
* READ                ::= leia(ID)
* IF					::= se (EXPR) STMT  
* EXPR				::= EXPR "|" REL | REL  
* REL					::= REL < ARITH | REL <= ARITH | REL > ARITH | ARITH  
* ARITH  				::= ARITH + TERM | ARITH - TERM | TERM  
* TERM				::= TERM * PO| PO
* PO				    ::= PO ** FACTOR| FACTOR
* FACTOR				::= (EXPR) | ID | LIT_INT | LIT_REAL | LIT_BOOL  



## Definições Regulares
LETTER		::= a | b | ... | z | A | B | ... Z | _  
DIGIT		::= 0 | 1 | ... | 9  
ID			::= LETTER (LETTER | DIGIT)*  
LIT_INT		::= DIGIT+  
LIT_REAL	::= DIGIT+ . DIGIT+   
LIT_BOOL	::= verdadeiro | falso  
TYPE     	::= inteiro | real | booleano  