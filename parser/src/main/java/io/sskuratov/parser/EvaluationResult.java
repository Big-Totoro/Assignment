package io.sskuratov.parser;

import java.math.BigDecimal;
import java.util.Collection;

public interface EvaluationResult {

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
