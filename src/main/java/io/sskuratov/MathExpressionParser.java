package io.sskuratov;

import io.sskuratov.exceptions.InvalidTokenException;
import io.sskuratov.exceptions.ParsingException;

import java.math.BigDecimal;

public class MathExpressionParser implements Parser {

    @Override
    public BigDecimal parse(String expression) throws InvalidTokenException, ParsingException {
        if (expression == null || expression.trim().length() == 0) {
            throw new ParsingException("Строка должна содержать арифметическое выражение.");
        }
        if (expression.chars().filter(c -> c == '(').count() != expression.chars().filter(c -> c == ')').count()) {
            throw new ParsingException("Количество открывающих скобок, должно соответствовать количеству закрывающих скобок.");
        }

        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer(expression);
        Interpreter interpreter = new Interpreter(lexicalAnalyzer);
        BigDecimal result = interpreter.expr();

        return result;
    }
}
