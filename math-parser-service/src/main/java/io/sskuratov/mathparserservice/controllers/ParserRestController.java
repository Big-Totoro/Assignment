package io.sskuratov.mathparserservice.controllers;

import io.sskuratov.mathparserservice.services.MathParserService;
import io.sskuratov.parser.exceptions.InvalidTokenException;
import io.sskuratov.parser.exceptions.ParsingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@RestController
public class ParserRestController {

    @Autowired
    MathParserService mathParserService;

    @GetMapping("/parser/v1/parse")
    public ParseResult parse(@RequestParam String expression) throws ParsingException, InvalidTokenException, UnsupportedEncodingException {
        expression = URLDecoder.decode(expression, StandardCharsets.UTF_8.toString());
        return new ParseResult(mathParserService.parse(expression));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidInputData(MethodArgumentNotValidException methodArgumentNotValidException) {
        return new ErrorResponse(1, "error message here");
    }
}
