package io.sskuratov.mathparserservice.services;

import io.sskuratov.parser.EvaluationResult;
import io.sskuratov.parser.MathExpressionParser;
import io.sskuratov.parser.Parser;
import io.sskuratov.parser.exceptions.InvalidTokenException;
import io.sskuratov.parser.exceptions.ParsingException;
import org.springframework.stereotype.Service;

@Service
public class MathParserService {

    public EvaluationResult parse(String expression) throws ParsingException, InvalidTokenException {
        Parser parser = new MathExpressionParser();
        return parser.parse(expression);
    }
}
