package io.sskuratov.parser;

import io.sskuratov.parser.exceptions.InvalidTokenException;
import io.sskuratov.parser.exceptions.ParsingException;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;
import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class MathExpressionParserTest {

    @Test
    @Parameters(source = TestDataProvider.class, method = "providePositiveData")
    public void positiveTest(String expression, BigDecimal expectedResult) throws ParsingException, InvalidTokenException {
        Logger.getGlobal().info(String.format("-> Проверяем строку '%s', ожидаемый результат '%s'",
                expression, expectedResult));
        Parser parser = new MathExpressionParser();
        BigDecimal actualResult = parser.parse(expression);
        assertThat(
                String.format("Ожидаемый результат: %s, фактический: %s", expectedResult, actualResult),
                actualResult.compareTo(expectedResult),
                equalTo(0)
        );
    }

    @Test(expected = InvalidTokenException.class)
    @Parameters(source = TestDataProvider.class, method = "provideInvalidTokenData")
    public void invalidTokenTest(String expression) throws ParsingException, InvalidTokenException {
        Logger.getGlobal().info(String.format("-> Проверяем строку '%s', ожидаемый результат '%s'",
                expression, InvalidTokenException.class));
        Parser parser = new MathExpressionParser();
        parser.parse(expression);
    }

    @Test(expected = ParsingException.class)
    @Parameters(source = TestDataProvider.class, method = "provideParsingErrorData")
    public void parsingErrorTest(String expression) throws ParsingException, InvalidTokenException {
        Logger.getGlobal().info(String.format("-> Проверяем строку '%s', ожидаемый результат '%s'",
                expression, ParsingException.class));
        Parser parser = new MathExpressionParser();
        parser.parse(expression);
    }
}