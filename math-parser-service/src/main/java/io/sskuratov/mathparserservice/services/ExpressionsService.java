package io.sskuratov.mathparserservice.services;

import io.sskuratov.mathparserservice.dao.Expressions;
import io.sskuratov.mathparserservice.repositories.ExpressionsRepository;
import io.sskuratov.parser.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExpressionsService {

    @Autowired
    private ExpressionsRepository expressionsRepository;

    public Long amountOnDate(LocalDate date) {
        return null;
    }

    public Long amountByOperation(TokenType operationId) {
        return null;
    }

    public List<Expressions> listOfExpressionsOnDate(LocalDate date) {
        return null;
    }

    public List<Expressions> listOfExpressionsByOperation(TokenType operationId) {
        return null;
    }

    public BigDecimal popularNumber() {
        return null;
    }
}
