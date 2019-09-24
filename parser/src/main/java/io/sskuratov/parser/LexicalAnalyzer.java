package io.sskuratov.parser;

import io.sskuratov.parser.exceptions.InvalidTokenException;

import java.math.BigDecimal;

public class LexicalAnalyzer {

    private final char END = 0;
    private int currentPosition;
    private String expression;
    private char currentChar;

    public LexicalAnalyzer(String expression) {
        this.expression = expression.replaceAll("\\(-", "(0-");
        currentPosition = 0;
        currentChar = expression.charAt(currentPosition);
    }

    /**
     * Step to the next lexeme and parse it
     * @return Token corresponds to the lexeme or lexemes
     */
    public Token next() throws InvalidTokenException {
        while (currentChar != END) {
            if (Character.isSpaceChar(currentChar)) {
                skipWhitespaces();
                continue;
            }

            if (Character.isDigit(currentChar)) {
                return new Token(TokenType.NUM, parseNumber());
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
                return new Token(TokenType.MUL, BigDecimal.ZERO);
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

            if (currentChar == '^') {
                move();
                return new Token(TokenType.POW, BigDecimal.ZERO);
            }

            if (currentPosition < expression.length()) {
                throw new InvalidTokenException(String.format("Incorrect symbol: '%s'", currentChar));
            }
        }

        return new Token(TokenType.END, BigDecimal.ZERO);
    }

    /**
     * Get the next lookahead lexeme
     */
    private void move() {
        if (++currentPosition >= expression.length()) {
            currentChar = END;
        } else {
            currentChar = expression.charAt(currentPosition);
        }
    }

    /**
     * Skip whitespaces
     */
    private void skipWhitespaces() {
        while ((currentChar != END) && Character.isSpaceChar(expression.charAt(currentPosition))) {
            move();
        }
    }

    /**
     * Parse the lexemes as a number
     * @return a number
     */
    private BigDecimal parseNumber() {
        int beginIndex = currentPosition;
        while ((currentChar != END) && (Character.isDigit(expression.charAt(currentPosition)) ||
                expression.charAt(currentPosition) == '.')) {
            move();
        }
        return new BigDecimal(expression.substring(beginIndex, currentPosition));
    }
}
