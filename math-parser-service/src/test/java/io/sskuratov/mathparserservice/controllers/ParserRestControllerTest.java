package io.sskuratov.mathparserservice.controllers;

import io.sskuratov.parser.EvaluationResult;
import io.sskuratov.parser.MathResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParserRestControllerTest {

    @Autowired
    TestRestTemplate template;

    @Test
    public void parserTest() throws UnsupportedEncodingException {
        String params = URLEncoder.encode("1+2", StandardCharsets.UTF_8.toString());
        ResponseEntity<MathResult> entity = template.getForEntity(String.format("/parser/v1/parse?expression=%s",
                params), MathResult.class);
        assertThat(String.format("Ожидаемый статус код: %s, получен: %s",
                HttpStatus.OK, entity.getStatusCode()), entity.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(String.format("Ожидаемый заголовок: %s, получен: %s",
                entity.getHeaders().getContentType(), MediaType.APPLICATION_JSON_UTF8),
                entity.getHeaders().getContentType(), equalTo(MediaType.APPLICATION_JSON_UTF8));
        EvaluationResult result = entity.getBody();
        System.out.println(result);
    }
}