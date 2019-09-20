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

    /**
     * Saves the expression to the database
     * @param expressions
     * @return
     */
    public Expressions save(Expressions expressions) {
        return expressionsRepository.save(expressions);
    }

    /**
     * Returns amount of calculated expressions on date
     * @param date
     * @return
     */
    public Long amountOnDate(LocalDate date) {
        return expressionsRepository.amountOnDate(date);
    }

    /**
     * Returns amount of calculated expressions have the specific operation
     * @param type operation token
     * @return
     */
    public Long amountByOperation(TokenType type) {
        return expressionsRepository.amountByOperation(type);
    }

    /**
     * Returns a list of calculated expressions on date
     * @param date
     * @return
     */
    public List<Expressions> listOfExpressionsOnDate(LocalDate date) {
        return expressionsRepository.listOfExpressionsOnDate(date);
    }

    /**
     * Returns a list of calculated expressions have the specific operation
     * @param type operation token
     * @return
     */
    public List<Expressions> listOfExpressionsByOperation(TokenType type) {
        return expressionsRepository.listOfExpressionsByOperation(type);
    }

    /**
     * Returns the most popular number(s) used among the expressions
     * @return
     */
    public BigDecimal popularNumber() {
        return expressionsRepository.popularNumber();
    }
}
