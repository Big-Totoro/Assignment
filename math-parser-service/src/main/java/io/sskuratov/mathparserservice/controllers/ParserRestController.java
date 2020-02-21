package io.sskuratov.mathparserservice.controllers;

import io.sskuratov.mathparserservice.dao.Expressions;
import io.sskuratov.mathparserservice.services.ExpressionsService;
import io.sskuratov.mathparserservice.services.MathParserService;
import io.sskuratov.parser.EvaluationResult;
import io.sskuratov.parser.exceptions.InvalidTokenException;
import io.sskuratov.parser.exceptions.ParsingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class ParserRestController {

    private MathParserService mathParserService;
    private ExpressionsService expressionsService;

    @Autowired
    public ParserRestController(ExpressionsService expressionsService, MathParserService mathParserService) {
        this.expressionsService = expressionsService;
        this.mathParserService = mathParserService;
    }

    @GetMapping("/parser/v1/parse")
    @ResponseBody
    public ResponseEntity<EvaluationResult> parse(@RequestParam String expression) {
        try {
            EvaluationResult result = mathParserService.parse(expression);
            expressionsService.save(Expressions.from(result));
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (InvalidTokenException | ParsingException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
