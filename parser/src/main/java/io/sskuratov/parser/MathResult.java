package io.sskuratov.parser;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
public class MathResult implements EvaluationResult {

    private String expression;
    private BigDecimal result;
    private Collection<Token> tokens;

    @Override
    public String getExpression() {
        return expression;
    }

    @Override
    public BigDecimal getResult() {
        return result;
    }

    @Override
    public Collection<Token> getTokens() {
        return tokens;
    }
}
