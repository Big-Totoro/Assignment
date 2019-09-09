package io.sskuratov;

import io.sskuratov.exceptions.InvalidTokenException;

import java.math.BigDecimal;

public class MathExpressionParser implements Parser {

    @Override
    public BigDecimal parse(String expression) throws InvalidTokenException {
        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(expression);
        Interpreter interpreter = new Interpreter(lexicalAnalyzer);
        BigDecimal result = interpreter.expr();

        return result;
    }
}
