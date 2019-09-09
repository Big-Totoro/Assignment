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

    private BigDecimal term() throws InvalidTokenException {
        Token token = currentToken;
        eat(TokenType.NUM);
        return token.getValue();
    }

    private BigDecimal expr() throws InvalidTokenException {
        currentToken = next();

        BigDecimal result = term();

        Token op = currentToken;
        while ((op.getTokenType() == TokenType.PLUS) ||
                (op.getTokenType() == TokenType.MINUS) ||
                (op.getTokenType() == TokenType.MULTIPLY) ||
                (op.getTokenType() == TokenType.DIV)) {
            if (op.getTokenType() == TokenType.PLUS) {
                eat(TokenType.PLUS);
                result = result.add(term());
            } else if (op.getTokenType() == TokenType.MINUS) {
                eat(TokenType.MINUS);
                result = result.subtract(term());
            } else {

            }
            op = currentToken;
        }

        return result;
    }

    private Token next() throws InvalidTokenException {
        move();
        if (currentPosition >= expression.length()) {
            return new Token(TokenType.END, BigDecimal.ZERO);
        }

        skipWhitespaces();

        char ch = expression.charAt(currentPosition);
        if (Character.isDigit(ch)) {
            return new Token(TokenType.NUM, new BigDecimal(getNumber()));
        }

        switch (ch) {
            case '+' :
                return new Token(TokenType.PLUS, BigDecimal.ZERO);
            case '-' :
                return new Token(TokenType.MINUS, BigDecimal.ZERO);
            case '*' :
                return new Token(TokenType.MULTIPLY, BigDecimal.ZERO);
            case '/' :
                return new Token(TokenType.DIV, BigDecimal.ZERO);
        }

        throw new InvalidTokenException("Некорректный токен '" + ch + "'");
    }

    private void move() {
        if (++currentPosition >= expression.length()) {
            currentToken = new Token(TokenType.END, BigDecimal.ZERO);
        }
    }

    private void skipWhitespaces() {
        while (Character.isSpaceChar(expression.charAt(currentPosition))) {
            move();
        }
    }

    private String getNumber() {
        int beginIndex = currentPosition;
        while ((currentPosition < expression.length()) &&
                (Character.isDigit(expression.charAt(currentPosition)) ||
                    expression.charAt(currentPosition) == '.')) {
            move();
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
