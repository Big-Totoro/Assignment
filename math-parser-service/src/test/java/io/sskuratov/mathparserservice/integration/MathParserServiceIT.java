package io.sskuratov.mathparserservice.integration;

import io.restassured.RestAssured;
import io.sskuratov.parser.MathResult;
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
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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

    private final List<E> expressions = new ArrayList<>();

    @Before
    public void setUp() {
        RestAssured.port = port;
        initData();
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

    @Test
    public void test() {
        /**
         * Parse a list of expressions
         */
        expressions.stream()
                .forEach(e -> {
                    MathResult result =
                            given().
                                param(e.getExpression()).
                            when().
                                get("/parser/v1/parse?expression=" + e.getExpression()).
                                    as(MathResult.class);
                    assertThat(
                            String.format("Ожидаемый результат: %s, фактический результат: %s",
                                result.getResult(), e.getResult()),
                            result.getResult().compareTo(e.getResult()), equalTo(0)
                    );
                });
        /**
         * Verify stats information
         */
        given().
                param("").
        when().
                get(String.format("/stats/v1/expressions/amount/date/%s", LocalDate.now().format(DateTimeFormatter.ISO_DATE))).
        then().
                assertThat().
                body("$", is(String.valueOf(expressions.size())));
    }
}