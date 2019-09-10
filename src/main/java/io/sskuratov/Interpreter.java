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

    /**
     * Проверяет, что текущий токен соответствует ожидаемому согласно грамматике и переходит к следующему
     * @param type ожидаемый токен
     * @throws InvalidTokenException
     */
    private void eat(TokenType type) throws InvalidTokenException {
        if (currentToken.getTokenType() == type) {
            currentToken = lexicalAnalyzer.next();
        } else {
            throw new InvalidTokenException("Токен '" + type + "' не найден");
        }
    }

    /**
     * Метод, соответствующий нетерминальному символу term грамматики
     * @return результат вычислений
     * @throws InvalidTokenException
     */
    private BigDecimal term() throws InvalidTokenException {
        BigDecimal result = factor();

        while ((currentToken.getTokenType() == TokenType.MULTIPLY) ||
                (currentToken.getTokenType() == TokenType.DIV)) {
            Token op = currentToken;
            if (op.getTokenType() == TokenType.MULTIPLY) {
                eat(TokenType.MULTIPLY);
                result = result.multiply(factor());
            } else if (op.getTokenType() == TokenType.DIV) {
                eat(TokenType.DIV);
                result = result.divide(factor());
            }
        }

        return result;
    }

    /**
     * Нетерминальный символ factor, соответствующий грамматике
     * @return результат вычислений
     * @throws InvalidTokenException
     */
    private BigDecimal factor() throws InvalidTokenException {
        BigDecimal result = exp();

        if (currentToken.getTokenType() == TokenType.NUM) {
            eat(TokenType.NUM);
            return currentToken.getValue();
        }

        if (currentToken.getTokenType() == TokenType.POW) {
            eat(TokenType.POW);
            result = result.pow(exp().intValue());
            return result;
        }

        return result;
    }

    private BigDecimal exp() throws InvalidTokenException {
        Token token = currentToken;
        if (token.getTokenType() == TokenType.NUM) {
            eat(TokenType.NUM);
            return token.getValue();
        }

        BigDecimal result;
        if (token.getTokenType() == TokenType.LP) {
            eat(TokenType.LP);
            result = expr();
            eat(TokenType.RP);
            return result;
        }

        throw new InvalidTokenException("Некорректный токен: " + token);
    }

    /**
     * Нетерминальный символ expr, соответствующий грамматике
     * @return результат вычислений
     * @throws InvalidTokenException
     */
    public BigDecimal expr() throws InvalidTokenException {
        BigDecimal result = term();

        while ((currentToken.getTokenType() == TokenType.PLUS) ||
                (currentToken.getTokenType() == TokenType.MINUS)) {
            Token op = currentToken;
            if (op.getTokenType() == TokenType.PLUS) {
                eat(TokenType.PLUS);
                result = result.add(term());
            } else if (op.getTokenType() == TokenType.MINUS) {
                eat(TokenType.MINUS);
                result = result.subtract(term());
            }
        }

        return result;
    }
}