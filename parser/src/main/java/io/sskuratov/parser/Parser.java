package io.sskuratov.parser;

import io.sskuratov.parser.exceptions.InvalidTokenException;
import io.sskuratov.parser.exceptions.ParsingException;

public interface Parser {

    /**
     * Evaluate a math expression given in a string form
     * @param expression arithmetic expression
     * @return the calculation result
     * @throws ParsingException signals that an error has been reached unexpectedly while parsing
     */
    EvaluationResult parse(String expression) throws ParsingException, InvalidTokenException;
}
