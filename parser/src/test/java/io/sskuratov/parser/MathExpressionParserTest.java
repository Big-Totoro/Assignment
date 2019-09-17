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

    private final String logMessageTemplate = "-> Проверяем строку '%s', ожидаемый результат '%s'";

    @Test
    @Parameters(source = TestDataProvider.class, method = "providePositiveData")
    public void positiveTest(String expression, BigDecimal expectedResult, int tokensAmount) throws ParsingException, InvalidTokenException {
        Logger.getGlobal().info(String.format(logMessageTemplate, expression, expectedResult));
        Parser parser = new MathExpressionParser();
        EvaluationResult actualResult = parser.parse(expression);
        assertThat(
                String.format("Ожидаемый результат: %s, фактический: %s", expectedResult, actualResult),
                actualResult.getResult().compareTo(expectedResult),
                equalTo(0)
        );
        assertThat(
                String.format("Ожидаемое количество операций: %s, фактически: %s",
                        tokensAmount,
                        actualResult.getTokens().size()),
                actualResult.getTokens().size(),
                equalTo(tokensAmount)
        );
    }

    @Test(expected = InvalidTokenException.class)
    @Parameters(source = TestDataProvider.class, method = "provideInvalidTokenData")
    public void invalidTokenTest(String expression) throws ParsingException, InvalidTokenException {
        Logger.getGlobal().info(String.format(logMessageTemplate, expression, InvalidTokenException.class));
        Parser parser = new MathExpressionParser();
        parser.parse(expression);
    }

    @Test(expected = ParsingException.class)
    @Parameters(source = TestDataProvider.class, method = "provideParsingErrorData")
    public void parsingErrorTest(String expression) throws ParsingException, InvalidTokenException {
        Logger.getGlobal().info(String.format(logMessageTemplate, expression, ParsingException.class));
        Parser parser = new MathExpressionParser();
        parser.parse(expression);
    }
}