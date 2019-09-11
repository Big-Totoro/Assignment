package io.sskuratov.parser;

import io.sskuratov.parser.exceptions.InvalidTokenException;
import io.sskuratov.parser.exceptions.ParsingException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class MathExpressionParserTest {

    @Rule
    public ExpectedException exceptionGrabber = ExpectedException.none();

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

    @Test
    public void powTest() throws ParsingException, InvalidTokenException {
        BigDecimal expected = new BigDecimal("12");
        Parser parser = new MathExpressionParser();
        BigDecimal result = parser.parse("3*2^2");
        assertThat(
                String.format("Ожидаемый результат: %s, фактический: %s", expected, result),
                result,
                equalTo(expected)
        );
    }

    @Test
    public void powExprTest() throws ParsingException, InvalidTokenException {
        BigDecimal expected = new BigDecimal("10");
        Parser parser = new MathExpressionParser();
        BigDecimal result = parser.parse("7 + 3 * ( 10 / (12 / (3 + 1) - 1) ) / (2 + 3) - 5 - 3 + (8)");
        assertThat(
                String.format("Ожидаемый результат: %s, фактический: %s", expected, result),
                result,
                equalTo(expected)
        );
    }

    @Test
    public void powPowExprTest() throws ParsingException, InvalidTokenException {
        BigDecimal expected = new BigDecimal("60");
        Parser parser = new MathExpressionParser();
        BigDecimal result = parser.parse("7 + 3 * 2 ^ (2 ^ (1+1)) + 5");
        assertThat(
                String.format("Ожидаемый результат: %s, фактический: %s", expected, result),
                result,
                equalTo(expected)
        );
    }

    @Test
    public void negAddMulTest() throws ParsingException, InvalidTokenException {
        exceptionGrabber.expect(InvalidTokenException.class);
        Parser parser = new MathExpressionParser();
        BigDecimal result = parser.parse("7+*3");
    }

    @Test
    public void negNotClosedBracketTest() throws ParsingException, InvalidTokenException {
        exceptionGrabber.expect(ParsingException.class);
        Parser parser = new MathExpressionParser();
        BigDecimal result = parser.parse("(7+3 * 5");
    }

    @Test
    public void negNotOpenedBracketTest() throws ParsingException, InvalidTokenException {
        exceptionGrabber.expect(ParsingException.class);
        Parser parser = new MathExpressionParser();
        BigDecimal result = parser.parse("7+3) * 5");
    }

    @Test
    public void negEmptyExpressionTest() throws ParsingException, InvalidTokenException {
        exceptionGrabber.expect(ParsingException.class);
        Parser parser = new MathExpressionParser();
        BigDecimal result = parser.parse("   ");
    }
}