package io.sskuratov;

import io.sskuratov.exceptions.InvalidTokenException;
import io.sskuratov.exceptions.ParsingException;

import java.math.BigDecimal;

public class MathExpressionParser implements Parser {

    private int currentPosition = -1;
    private Token currentToken;
    private String expression;

    @Override
    public BigDecimal parse(String expression) throws ParsingException, InvalidTokenException {
        this.expression = expression;

        BigDecimal result = expr();

        return result;
    }

    private BigDecimal expr() throws InvalidTokenException {
        currentToken = next();

        Token left = currentToken;
        eat(TokenType.NUM);

        Token op = currentToken;
        eat(TokenType.PLUS);

        Token right = currentToken;
        eat(TokenType.NUM);

        return left.getValue().add(right.getValue());
    }

    private Token next() throws InvalidTokenException {
        ++currentPosition;
        if (currentPosition >= expression.length()) {
            return new Token(TokenType.END, BigDecimal.ZERO);
        }

        char ch = expression.charAt(currentPosition);
        if (Character.isDigit(ch)) {
            return new Token(TokenType.NUM, new BigDecimal(getNumber()));
        }

        if (ch == '+') {
            return new Token(TokenType.PLUS, BigDecimal.ZERO);
        }

        throw new InvalidTokenException("Некорректный токен '" + ch + "'");
    }

    private String getNumber() {
        int beginIndex = currentPosition;
        while ((currentPosition < expression.length()) &&
                (Character.isDigit(expression.charAt(currentPosition)) ||
                    expression.charAt(currentPosition) == '.')) {
            ++currentPosition;
        }
        return expression.substring(beginIndex, currentPosition--);
    }

    private void eat(TokenType type) throws InvalidTokenException {
        if (currentToken.getTokenType() == type) {
            currentToken = next();
        } else {
            throw new InvalidTokenException("Токен '" + type + "' не найден");
        }
    }
}
