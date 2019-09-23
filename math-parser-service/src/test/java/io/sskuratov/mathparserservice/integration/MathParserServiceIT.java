package io.sskuratov.mathparserservice.integration;

import io.restassured.RestAssured;
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
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.get;
import static org.hamcrest.CoreMatchers.equalTo;

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
    int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void test() {
        List<E> expressions = new ArrayList<>();
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
        expressions.add(new E("9*1+4.5", BigDecimal.valueOf(13.5), 5));
        
        expressions.stream()
                .forEach(e -> {
                    get("/parser/v1/parse?expression=" + e.getExpression())
                            .then()
                            .assertThat()
                            .body("result", equalTo(e.getResult()));
                });
    }
}
