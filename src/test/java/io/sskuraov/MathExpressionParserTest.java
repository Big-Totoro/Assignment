package io.sskuraov;

import io.sskuratov.MathExpressionParser;
import io.sskuratov.Parser;
import io.sskuratov.exceptions.InvalidTokenException;
import io.sskuratov.exceptions.ParsingException;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class MathExpressionParserTest {

    @Test
    public void simpleTest() throws ParsingException, InvalidTokenException {
        BigDecimal expected = new BigDecimal("31.00");
        Parser parser = new MathExpressionParser();
        BigDecimal result = parser.parse("10.50+20.50");
        assertThat(
                String.format("Ожидаемый результат: %s, фактический: %s", expected, result),
                result,
                equalTo(expected)
        );
    }
}