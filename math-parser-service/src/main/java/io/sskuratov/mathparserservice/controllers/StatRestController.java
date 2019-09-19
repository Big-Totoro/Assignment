package io.sskuratov.mathparserservice.controllers;

import io.sskuratov.mathparserservice.dao.Expressions;
import io.sskuratov.mathparserservice.services.ExpressionsService;
import io.sskuratov.parser.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
public class StatRestController {

    @Autowired
    private ExpressionsService expressionsService;

    @GetMapping("/stats/v1/expressions/amount/date/{date}")
    @ResponseBody
    public Long amountOnDate(@PathVariable LocalDate date) {
        return Long.valueOf(10);
    }

    @GetMapping("/stats/v1/expressions/amount/operation/{operationId}")
    @ResponseBody
    public Long amountByOperation(@PathVariable TokenType operationId) {
        return Long.valueOf(20);
    }

    @GetMapping("/stats/v1/expressions/date/{date}")
    @ResponseBody
    public List<Expressions> listOfExpressionsOnDate(@PathVariable LocalDate date) {
        return new ArrayList<>();
    }

    @GetMapping("/stats/v1/expressions/operation/{operationId}")
    @ResponseBody
    public List<Expressions> listOfExpressionsByOperation(@PathVariable TokenType operationId) {
        return new ArrayList<>();
    }

    @GetMapping("/stats/v1/number/popular")
    @ResponseBody
    public BigDecimal popularNumber() {
        return BigDecimal.valueOf(40.0);
    }
}
