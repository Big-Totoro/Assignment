# Assignment

The arithmetic expression parser corresponding to the context free-grammar

- expr: term ((PLUS | MINUS) term)*
- term: factor ((MUL | DIV) factor)*
- factor: exp ^ exp | exp
- exp: (expr) | NUM
