package io.sskuratov.mathparserservice.dao;

import io.sskuratov.mathparserservice.repositories.ExpressionsRepository;
import io.sskuratov.parser.EvaluationResult;
import io.sskuratov.parser.MathExpressionParser;
import io.sskuratov.parser.Parser;
import io.sskuratov.parser.TokenType;
import io.sskuratov.parser.exceptions.InvalidTokenException;
import io.sskuratov.parser.exceptions.ParsingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "development")
@SpringBootTest
public class ExpressionsTest {

    @Autowired
    private ExpressionsRepository expressionsRepository;
    private final String logMessageTemplate = "-> Проверяем строку '%s', ожидаемый результат '%s'";

    @Test
    public void expressionsTest() throws ParsingException, InvalidTokenException {
        String expression = "10+2-6";
        BigDecimal expectedResult = BigDecimal.valueOf(6.0);

        Set<Operations> operations = new HashSet<>();
        operations.add(new Operations(TokenType.PLUS));
        operations.add(new Operations(TokenType.MINUS));

        Set<Numbers> numbers = new HashSet<>();
        numbers.add(new Numbers(BigDecimal.valueOf(10.0)));
        numbers.add(new Numbers(BigDecimal.valueOf(2.0)));
        numbers.add(new Numbers(BigDecimal.valueOf(60)));
        Expressions saved = expressionsRepository.save(new Expressions(
                LocalDateTime.now(),
                expression,
                expectedResult,
                operations,
                numbers
        ));

        Logger.getGlobal().info(String.format(logMessageTemplate, expression, expectedResult));
        Parser parser = new MathExpressionParser();
        EvaluationResult actualResult = parser.parse(expression);
        assertThat(
                String.format("Ожидаемый результат: %s, фактический: %s", expectedResult, actualResult),
                actualResult.getResult().compareTo(expectedResult),
                equalTo(0)
        );
    }
}
