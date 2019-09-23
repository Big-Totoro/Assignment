package io.sskuratov.mathparserservice.controllers;

import io.sskuratov.parser.EvaluationResult;
import io.sskuratov.parser.MathResult;
import io.sskuratov.parser.TokenType;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatRestControllerTest {

    @Autowired
    TestRestTemplate template;

    private void doParse(String expression, BigDecimal expectedResult) throws UnsupportedEncodingException {
        expression = URLEncoder.encode(expression, StandardCharsets.UTF_8.toString());
        ResponseEntity<MathResult> entity = template.getForEntity(String.format("/parser/v1/parse?expression=%s",
                expression), MathResult.class);
        assertThat(String.format("Ожидаемый статус код: %s, получен: %s",
                HttpStatus.OK, entity.getStatusCode()), entity.getStatusCode(), equalTo(HttpStatus.OK));
        EvaluationResult actualResult = entity.getBody();
        assertThat(
                String.format("Ожидаемый результат: %s, фактический: %s", expectedResult, actualResult),
                actualResult.getResult().compareTo(expectedResult),
                CoreMatchers.equalTo(0)
        );
    }

    @Test
    public void amountOnDateTest() throws UnsupportedEncodingException {
        String expression = "7 + 3 * ( 10 / (12 / (3 + 1) - 1) ) / (2 + 3) - 5 - 3 + (8)";
        BigDecimal expectedResult = BigDecimal.valueOf(10.0);
        doParse(expression, expectedResult);
        ResponseEntity<Long> entity = template.getForEntity(
                String.format("/stats/v1/expressions/amount/date/%s",
                LocalDate.now().format(DateTimeFormatter.ISO_DATE)), Long.class
        );
        assertThat(String.format("Ожидаемый статус код: %s, получен: %s",
                HttpStatus.OK, entity.getStatusCode()), entity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(String.format("Ожидаемый результат: %s, фактический: %s",
                1L, entity.getBody()), entity.getBody(), equalTo(1L));
    }

    @Test
    public void amountByOperationTest() throws UnsupportedEncodingException {
        String expression = "9*1+4.5";
        BigDecimal expectedResult = BigDecimal.valueOf(13.5);
        doParse(expression, expectedResult);
        ResponseEntity<Long> entity = template.getForEntity(
                String.format("/stats/v1/expressions/amount/operation/%s", TokenType.PLUS), Long.class
        );
        assertThat(String.format("Ожидаемый статус код: %s, получен: %s",
                HttpStatus.OK, entity.getStatusCode()), entity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(String.format("Ожидаемый результат: %s, фактический: %s",
                1L, entity.getBody()), entity.getBody(), equalTo(1L));
    }
}
