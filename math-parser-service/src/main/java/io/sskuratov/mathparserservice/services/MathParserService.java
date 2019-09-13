package io.sskuratov.mathparserservice.services;

import io.sskuratov.parser.MathExpressionParser;
import io.sskuratov.parser.Parser;
import io.sskuratov.parser.exceptions.InvalidTokenException;
import io.sskuratov.parser.exceptions.ParsingException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MathParserService {

    public BigDecimal parse(String expression) throws ParsingException, InvalidTokenException {
        Parser parser = new MathExpressionParser();
        return parser.parse(expression);
    }
}
