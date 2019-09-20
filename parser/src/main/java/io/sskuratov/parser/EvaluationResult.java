package io.sskuratov.parser;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.math.BigDecimal;
import java.util.Collection;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "className")
public interface EvaluationResult {

    /**
     * Returns the original expression
     * @return
     */
    String getExpression();

    /**
     * Returns the result of expression evaluation
     * @return
     */
    BigDecimal getResult();

    /**
     * Returns the representation of the input string expression as a list of tokens
     * @return
     */
    Collection<Token> getTokens();
}
