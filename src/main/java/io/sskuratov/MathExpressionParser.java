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
        if (op.getTokenType() == TokenType.PLUS) {
            eat(TokenType.PLUS);
        } else if (op.getTokenType() == TokenType.MINUS) {
            eat(TokenType.MINUS);
        } else if (op.getTokenType() == TokenType.MULTIPLY) {
            eat(TokenType.MULTIPLY);
        } else if (op.getTokenType() == TokenType.DIV) {
            eat(TokenType.DIV);
        }

        Token right = currentToken;
        eat(TokenType.NUM);

        BigDecimal result;
        if (op.getTokenType() == TokenType.PLUS) {
            result = left.getValue().add(right.getValue());
        } else if (op.getTokenType() == TokenType.MINUS) {
            result = left.getValue().subtract(right.getValue());
        } else if (op.getTokenType() == TokenType.MULTIPLY) {
            result = left.getValue().multiply(right.getValue());
        } else {//if (op.getTokenType() == TokenType.DIV) {
            result = left.getValue().divide(right.getValue());
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
