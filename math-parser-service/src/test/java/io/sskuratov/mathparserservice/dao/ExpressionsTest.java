package io.sskuratov.mathparserservice.dao;

import io.sskuratov.mathparserservice.repositories.ExpressionsRepository;
import io.sskuratov.parser.EvaluationResult;
import io.sskuratov.parser.MathExpressionParser;
import io.sskuratov.parser.Parser;
import io.sskuratov.parser.exceptions.InvalidTokenException;
import io.sskuratov.parser.exceptions.ParsingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.logging.Logger;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = "development")
@SpringBootTest
public class ExpressionsTest {

    @Autowired
    private ExpressionsRepository expressionsRepository;
    private final String logMessageTemplate = "-> Проверяем строку '%s', ожидаемый результат '%s'";

    @Test
    public void expressionsTest() throws ParsingException, InvalidTokenException {
        String expression = "(-7*8+9-(9/4.5))^2";
        BigDecimal expectedResult = BigDecimal.valueOf(2401);

        Logger.getGlobal().info(String.format(logMessageTemplate, expression, expectedResult));
        Parser parser = new MathExpressionParser();
        EvaluationResult actualResult = parser.parse(expression);
        Expressions saved = expressionsRepository.save(Expressions.from(actualResult));
        assertThat("Expression.Id должен быть назначен и больше 0", saved.getId(), greaterThan(0L));
        assertThat(
                String.format("Ожидаемый результат: %s, фактический: %s", expectedResult, actualResult),
                actualResult.getResult().compareTo(expectedResult),
                equalTo(0)
        );
    }
}
