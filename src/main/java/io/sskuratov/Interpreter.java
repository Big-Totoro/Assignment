package io.sskuratov;

import io.sskuratov.exceptions.InvalidTokenException;

import java.math.BigDecimal;

public class Interpreter {

    private LexicalAnalyzer lexicalAnalyzer;
    private Token currentToken;

    public Interpreter(LexicalAnalyzer lexicalAnalyzer) throws InvalidTokenException {
        this.lexicalAnalyzer = lexicalAnalyzer;
        this.currentToken = lexicalAnalyzer.next();
    }

    private void eat(TokenType type) throws InvalidTokenException {
        if (currentToken.getTokenType() == type) {
            currentToken = lexicalAnalyzer.next();
        } else {
            throw new InvalidTokenException("Токен '" + type + "' не найден");
        }
    }

    private BigDecimal factor() throws InvalidTokenException {
        Token token = currentToken;
        eat(TokenType.NUM);
        return token.getValue();
    }

    public BigDecimal expr() throws InvalidTokenException {
        BigDecimal result = factor();

        while ((currentToken.getTokenType() == TokenType.PLUS) ||
                (currentToken.getTokenType() == TokenType.MINUS) ||
                (currentToken.getTokenType() == TokenType.MULTIPLY) ||
                (currentToken.getTokenType() == TokenType.DIV)) {
            Token op = currentToken;
            if (op.getTokenType() == TokenType.PLUS) {
                eat(TokenType.PLUS);
                result = result.add(factor());
            } else if (op.getTokenType() == TokenType.MINUS) {
                eat(TokenType.MINUS);
                result = result.subtract(factor());
            } else if (op.getTokenType() == TokenType.MULTIPLY) {
                eat(TokenType.MULTIPLY);
                result = result.multiply(factor());
            } else {
                eat(TokenType.DIV);
                result = result.divide(factor());
            }
        }

        return result;
    }
}