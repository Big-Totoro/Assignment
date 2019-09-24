package io.sskuratov.mathparserservice.integration;

import io.restassured.RestAssured;
import io.sskuratov.parser.MathResult;
import io.sskuratov.parser.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MathParserServiceIT {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class E {
        private String expression;
        private BigDecimal result;
        private int symbols;
    }

    @LocalServerPort
    private int port;

    public static final String FORMAT_EXPECTED_ACTUAL = "Ожидаемый результат: %s, фактический результат: %s";
    private final List<E> expressions = new ArrayList<>();

    @Before
    public void setUp() {
        RestAssured.port = port;
        initData();
        prepareParsedExpressions();
    }

    private void initData() {
        expressions.add(new E("10.50     +  20.50", BigDecimal.valueOf(31.0), 3));
        expressions.add(new E("100.50     -  20.50   - 20", BigDecimal.valueOf(60.0), 5));
        expressions.add(new E("100.50     +  20.50 - 80.00+16", BigDecimal.valueOf(57.0), 7));
        expressions.add(new E("100     *  20", BigDecimal.valueOf(2000.0), 3));
        expressions.add(new E("1400  /  20", BigDecimal.valueOf(70.0), 3));
        expressions.add(new E("14  +  2*    3  -6/   2", BigDecimal.valueOf(17.0), 9));
        expressions.add(new E("7 + 3 * ( 10 / (12 / (3 + 1) - 1) ) / (2 + 3) - 5 - 3 + (8)", BigDecimal.valueOf(10.0), 33));
        expressions.add(new E("3*2^2", BigDecimal.valueOf(12.0), 5));
        expressions.add(new E("7 + 3 * 2 ^ (2 ^ (1+1)) + 5", BigDecimal.valueOf(60.0), 17));
        expressions.add(new E("(-7*8+9-(9/4.5))^2", BigDecimal.valueOf(2401), 17));
        expressions.add(new E("9*1+4.5", BigDecimal.valueOf(13.50), 5));
    }

    private void prepareParsedExpressions() {
        /**
         * Parse a list of expressions
         */
        expressions
                .forEach(e -> {
                    MathResult result =
                            given().
                                    param(e.getExpression()).
                                    when().
                                    get("/parser/v1/parse?expression=" + e.getExpression()).
                                    as(MathResult.class);
                    assertThat(
                            String.format(FORMAT_EXPECTED_ACTUAL,
                                    e.getResult(), result.getResult()),
                            result.getResult().compareTo(e.getResult()), equalTo(0)
                    );
                });
    }

    private void amountByOperation(TokenType type, Long expectedResult) {
        final Long actualResult = get(String.format("/stats/v1/expressions/amount/operation/%s", type)).as(Long.class);
        assertThat(String.format(FORMAT_EXPECTED_ACTUAL, expectedResult, actualResult),
                actualResult, equalTo(expectedResult));
    }

    private void listOfExpressionsByOperation(TokenType type) {
        final List<String> actualExpr = get(String.format("/stats/v1/expressions/operation/%s", type)).as(ArrayList.class);
        final List<String> expectedExpr = expressions.stream()
                .filter(e -> e.getExpression().contains(TokenType.toSign(type)))
                .map(E::getExpression)
                .collect(Collectors.toList());
        assertThat(
                String.format(FORMAT_EXPECTED_ACTUAL, expectedExpr, actualExpr),
                actualExpr,
                containsInAnyOrder(expectedExpr.toArray())
        );
    }

    @Test
    public void verifyIntegration() {
        /**
         * Amount of expressions on date
         */
        final Long result = get(String.format("/stats/v1/expressions/amount/date/%s",
                LocalDate.now().format(DateTimeFormatter.ISO_DATE))).
            as(Long.class);
        assertThat(String.format(FORMAT_EXPECTED_ACTUAL, expressions.size(), result),
                result, equalTo((long) expressions.size()));

        /**
         * Amount of expression with operation PLUS
         */
        final Long PLUS_EXPR = 7L;
        amountByOperation(TokenType.PLUS, PLUS_EXPR);

        /**
         * Amount of expression with operation MINUS
         */
        final Long MINUS_EXPR = 5L;
        amountByOperation(TokenType.MINUS, MINUS_EXPR);

        /**
         * Amount of expression with operation MULTIPLY
         */
        final Long MULTIPLY_EXPR = 7L;
        amountByOperation(TokenType.MUL, MULTIPLY_EXPR);

        /**
         * Amount of expression with operation DIV
         */
        final Long DIV_EXPR = 4L;
        amountByOperation(TokenType.DIV, DIV_EXPR);

        /**
         * Amount of expression with operation LEFT BRACKET
         */
        final Long LP_EXPR = 3L;
        amountByOperation(TokenType.LP, LP_EXPR);

        /**
         * Amount of expression with operation RIGHT BRACKET
         */
        final Long RP_EXPR = 3L;
        amountByOperation(TokenType.LP, RP_EXPR);

        /**
         * Amount of expression with operation POWER
         */
        final Long POW_EXPR = 3L;
        amountByOperation(TokenType.POW, POW_EXPR);

        /**
         * List of expressions on date
         */
        final List<String> actualExpr = get(String.format("/stats/v1/expressions/date/%s",
                    LocalDate.now().format(DateTimeFormatter.ISO_DATE))).
                    as(ArrayList.class);
        final List<String> expectedExpr = expressions.stream().map(E::getExpression).collect(Collectors.toList());
        assertThat(
                String.format(FORMAT_EXPECTED_ACTUAL, expectedExpr, actualExpr),
                actualExpr,
                equalTo(expectedExpr)
        );

        /**
         * Amount of expression with operation PLUS
         */
        listOfExpressionsByOperation(TokenType.PLUS);

        /**
         * Amount of expression with operation MINUS
         */
        listOfExpressionsByOperation(TokenType.MINUS);

        /**
         * Amount of expression with operation MULTIPLY
         */
        listOfExpressionsByOperation(TokenType.MUL);

        /**
         * Amount of expression with operation DIV
         */
        listOfExpressionsByOperation(TokenType.DIV);

        /**
         * Amount of expression with operation LP
         */
        listOfExpressionsByOperation(TokenType.LP);

        /**
         * Amount of expression with operation RP
         */
        listOfExpressionsByOperation(TokenType.RP);

        /**
         * Amount of expression with operation POW
         */
        listOfExpressionsByOperation(TokenType.POW);

        /**
         * Verify the most popular numbers request
         */
        final List<String> actualPopular = get("/stats/v1/number/popular").as(ArrayList.class);
        final List<String> expectedPopular = Arrays.asList("2.00");
        assertThat(
                String.format(FORMAT_EXPECTED_ACTUAL, expectedPopular, actualPopular),
                actualPopular,
                equalTo(expectedPopular)
        );
    }
}