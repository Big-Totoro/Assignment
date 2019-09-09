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
    public void addTest() throws ParsingException, InvalidTokenException {
        BigDecimal expected = new BigDecimal("31.00");
        Parser parser = new MathExpressionParser();
        BigDecimal result = parser.parse("10.50     +  20.50");
        assertThat(
                String.format("Ожидаемый результат: %s, фактический: %s", expected, result),
                result,
                equalTo(expected)
        );
    }

    @Test
    public void subTest() throws ParsingException, InvalidTokenException {
        BigDecimal expected = new BigDecimal("60.00");
        Parser parser = new MathExpressionParser();
        BigDecimal result = parser.parse("100.50     -  20.50   - 20");
        assertThat(
                String.format("Ожидаемый результат: %s, фактический: %s", expected, result),
                result,
                equalTo(expected)
        );
    }

    @Test
    public void addSubMixedTest() throws ParsingException, InvalidTokenException {
        BigDecimal expected = new BigDecimal("57.00");
        Parser parser = new MathExpressionParser();
        BigDecimal result = parser.parse("100.50     +  20.50 - 80.00+16");
        assertThat(
                String.format("Ожидаемый результат: %s, фактический: %s", expected, result),
                result,
                equalTo(expected)
        );
    }

    @Test
    public void mulTest() throws ParsingException, InvalidTokenException {
        BigDecimal expected = new BigDecimal("2000");
        Parser parser = new MathExpressionParser();
        BigDecimal result = parser.parse("100     *  20");
        assertThat(
                String.format("Ожидаемый результат: %s, фактический: %s", expected, result),
                result,
                equalTo(expected)
        );
    }

    @Test
    public void divTest() throws ParsingException, InvalidTokenException {
        BigDecimal expected = new BigDecimal("70");
        Parser parser = new MathExpressionParser();
        BigDecimal result = parser.parse("1400  /  20");
        assertThat(
                String.format("Ожидаемый результат: %s, фактический: %s", expected, result),
                result,
                equalTo(expected)
        );
    }

    @Test
    public void addSubMulDivMixedTest() throws ParsingException, InvalidTokenException {
        BigDecimal expected = new BigDecimal("17");
        Parser parser = new MathExpressionParser();
        BigDecimal result = parser.parse("14  +  2*    3  -6/   2");
        assertThat(
                String.format("Ожидаемый результат: %s, фактический: %s", expected, result),
                result,
                equalTo(expected)
        );
    }

    @Test
    public void parenthesesTest() throws ParsingException, InvalidTokenException {
        BigDecimal expected = new BigDecimal("10");
        Parser parser = new MathExpressionParser();
        BigDecimal result = parser.parse("7 + 3 * ( 10 / (12 / (3 + 1) - 1) ) / (2 + 3) - 5 - 3 + (8)");
        assertThat(
                String.format("Ожидаемый результат: %s, фактический: %s", expected, result),
                result,
                equalTo(expected)
        );
    }
}
