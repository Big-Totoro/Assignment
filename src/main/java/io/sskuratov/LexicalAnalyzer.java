package io.sskuratov;

import io.sskuratov.exceptions.InvalidTokenException;

import java.math.BigDecimal;

public class LexicalAnalyzer {

    private final char END = 0;
    private int currentPosition;
    private String expression;
    private char currentChar;

    public LexicalAnalyzer(String expression) {
        this.expression = expression;
        currentPosition = 0;
        currentChar = expression.charAt(currentPosition);
    }

    public Token next() throws InvalidTokenException {
        while (currentChar != END) {
            if (Character.isSpaceChar(currentChar)) {
                skipWhitespaces();
                continue;
            }

            if (Character.isDigit(currentChar)) {
                return new Token(TokenType.NUM, getNumber());
            }

            if (currentChar == '+') {
                move();
                return new Token(TokenType.PLUS, BigDecimal.ZERO);
            }

            if (currentChar == '-') {
                move();
                return new Token(TokenType.MINUS, BigDecimal.ZERO);
            }

            if (currentChar == '*') {
                move();
                return new Token(TokenType.MULTIPLY, BigDecimal.ZERO);
            }

            if (currentChar == '/') {
                move();
                return new Token(TokenType.DIV, BigDecimal.ZERO);
            }

            if  (currentChar == '(') {
                move();
                return new Token(TokenType.LP, BigDecimal.ZERO);
            }

            if (currentChar == ')') {
                move();
                return new Token(TokenType.RP, BigDecimal.ZERO);
            }
        }

        return new Token(TokenType.END, BigDecimal.ZERO);
    }

    private void move() {
        if (++currentPosition >= expression.length()) {
            currentChar = END;
        } else {
            currentChar = expression.charAt(currentPosition);
        }
    }

    private void skipWhitespaces() {
        while ((currentChar != END) && Character.isSpaceChar(expression.charAt(currentPosition))) {
            move();
        }
    }

    private BigDecimal getNumber() {
        int beginIndex = currentPosition;
        while ((currentChar != END) && (Character.isDigit(expression.charAt(currentPosition)) ||
                expression.charAt(currentPosition) == '.')) {
            move();
        }
        return new BigDecimal(expression.substring(beginIndex, currentPosition));
    }
}
