package io.sskuratov.mathparserservice.controllers;

import io.sskuratov.mathparserservice.services.ExpressionsService;
import io.sskuratov.parser.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
public class StatRestController {

    private ExpressionsService expressionsService;

    @Autowired
    public StatRestController(ExpressionsService expressionsService) {
        this.expressionsService = expressionsService;
    }

    @GetMapping("/stats/v1/expressions/amount/date/{date}")
    @ResponseBody
    public Long amountOnDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return expressionsService.amountOnDate(date);
    }

    @GetMapping("/stats/v1/expressions/amount/operation/{type}")
    @ResponseBody
    public Long amountByOperation(@PathVariable TokenType type) {
        return expressionsService.amountByOperation(type);
    }

    @GetMapping("/stats/v1/expressions/date/{date}")
    @ResponseBody
    public List<String> listOfExpressionsOnDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return expressionsService.listOfExpressionsOnDate(date);
    }

    @GetMapping("/stats/v1/expressions/operation/{type}")
    @ResponseBody
    public List<String> listOfExpressionsByOperation(@PathVariable TokenType type) {
        return expressionsService.listOfExpressionsByOperation(type);
    }

    @GetMapping("/stats/v1/number/popular")
    @ResponseBody
    public List<String> popularNumber() {
        return expressionsService.popularNumber();
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
        return new ResponseEntity<>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}