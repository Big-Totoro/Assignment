package io.sskuratov.mathparserservice.controllers;

import io.sskuratov.mathparserservice.services.MathParserService;
import io.sskuratov.parser.exceptions.InvalidTokenException;
import io.sskuratov.parser.exceptions.ParsingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
public class ParserRestController {

    @Autowired
    MathParserService mathParserService;

    @GetMapping("/parser/v1/parse")
    @ResponseBody
    public BigDecimal parse(@RequestParam String expression) throws ParsingException, InvalidTokenException, UnsupportedEncodingException {
        try {
            expression = URLDecoder.decode(expression, StandardCharsets.UTF_8.toString());
            return mathParserService.parse(expression);
        } catch (InvalidTokenException | ParsingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
