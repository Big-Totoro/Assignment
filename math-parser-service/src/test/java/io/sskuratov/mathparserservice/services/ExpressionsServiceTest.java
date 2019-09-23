package io.sskuratov.mathparserservice.services;

import io.sskuratov.mathparserservice.dao.Expressions;
import io.sskuratov.parser.EvaluationResult;
import io.sskuratov.parser.TokenType;
import io.sskuratov.parser.exceptions.InvalidTokenException;
import io.sskuratov.parser.exceptions.ParsingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ExpressionsServiceTest {

    private static final String FORMAT_EXPECTED_ACTUAL = "Ожидаемый результат: %s, фактический: %s";
    private final String expression = "7 + 3 * ( 10 / (12 / (3 + 1) - 1) ) / (2 + 3) - 5 - 3 + (8) + 1 + 1";
    private final BigDecimal expressionResult = BigDecimal.valueOf(12);


    @Autowired
    private ExpressionsService expressionsService;

    @Autowired
    private MathParserService mathParserService;

    private void doParse(String expression, BigDecimal expectedResult) throws ParsingException, InvalidTokenException {
        EvaluationResult actualResult = mathParserService.parse(expression);
        assertThat(String.format(FORMAT_EXPECTED_ACTUAL, expectedResult, actualResult.getResult()),
                actualResult.getResult(), equalTo(expectedResult));
        expressionsService.save(Expressions.from(actualResult));
    }

    @Test
    public void amountOnDateTest() throws ParsingException, InvalidTokenException {
        doParse(expression, expressionResult);

        Long actualResult = expressionsService.amountOnDate(LocalDate.now());
        Long expectedResult = 1L;
        assertThat(String.format(FORMAT_EXPECTED_ACTUAL, expectedResult, actualResult), expectedResult, equalTo(actualResult));
    }

    @Test
    public void amountByOperationTest() throws ParsingException, InvalidTokenException {
        doParse(expression, expressionResult);

        Long actualResult = expressionsService.amountByOperation(TokenType.PLUS);
        Long expectedResult = 1L;
        assertThat(String.format(FORMAT_EXPECTED_ACTUAL, expectedResult, actualResult), expectedResult, equalTo(actualResult));
    }

    @Test
    public void listOfExpressionsOnDateTest() throws ParsingException, InvalidTokenException {
        doParse(expression, expressionResult);

        List<String> actualResult = expressionsService.listOfExpressionsOnDate(LocalDate.now());
        assertThat(String.format(FORMAT_EXPECTED_ACTUAL, expression, actualResult.get(0)), expression, equalTo(actualResult.get(0)));
    }

    @Test
    public void listOfExpressionsByOperationTest() throws ParsingException, InvalidTokenException {
        doParse(expression, expressionResult);

        List<String> actualResult = expressionsService.listOfExpressionsByOperation(TokenType.MINUS);
        assertThat(String.format(FORMAT_EXPECTED_ACTUAL, expression, actualResult.get(0)), expression, equalTo(actualResult.get(0)));
    }

    @Test
    public void popularNumberTest() throws ParsingException, InvalidTokenException {
        doParse(expression, expressionResult);

        List<String> actualResult = expressionsService.popularNumber();
        String expectedResult = "1.00";
        assertThat(String.format(FORMAT_EXPECTED_ACTUAL, expectedResult, actualResult.get(0)), expectedResult, equalTo(actualResult.get(0)));
    }
}
