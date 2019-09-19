package io.sskuratov.mathparserservice.controllers;

import io.sskuratov.mathparserservice.services.ExpressionsService;
import io.sskuratov.parser.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
public class StatRestController {

    @Autowired
    private ExpressionsService expressionsService;

    @GetMapping("/stats/v1/expressions/amount/date")
    @ResponseBody
    public Long amountByDate(LocalDateTime date) {
        return Long.valueOf(10);
    }

    @GetMapping("/stats/v1/expressions/amount/operation")
    @ResponseBody
    public Long amountByOperation(TokenType type) {
        return Long.valueOf(20);
    }

    @GetMapping("/stats/v1/expressions/operation")
    @ResponseBody
    public Long listOfExpressionsByOperation(TokenType type) {
        return Long.valueOf(30);
    }

    @GetMapping("/stats/v1/number/popular")
    @ResponseBody
    public BigDecimal popularNumber() {
        return BigDecimal.valueOf(40.0);
    }
}
