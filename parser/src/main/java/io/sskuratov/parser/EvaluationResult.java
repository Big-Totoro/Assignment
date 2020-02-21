package io.sskuratov.parser;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.math.BigDecimal;
import java.util.Collection;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "className")
public interface EvaluationResult {

    /**
     * Returns the original expression
     * @return the original expression
     */
    String getExpression();

    /**
     * Returns the result of expression evaluation
     * @return the result of expression evaluation
     */
    BigDecimal getResult();

    /**
     * Returns the representation of the input string expression as a list of tokens
     * @return the representation of the input string expression as a list of tokens
     */
    Collection<Token> getTokens();
}
