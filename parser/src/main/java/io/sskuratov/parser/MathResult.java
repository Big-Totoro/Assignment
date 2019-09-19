package io.sskuratov.parser;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
public class MathResult implements EvaluationResult {

    private BigDecimal result;
    private Collection<Token> tokens;

    @Override
    public BigDecimal getResult() {
        return result;
    }

    @Override
    public Collection<Token> getTokens() {
        return tokens;
    }
}
