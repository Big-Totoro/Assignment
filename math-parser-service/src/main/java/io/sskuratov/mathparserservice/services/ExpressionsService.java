package io.sskuratov.mathparserservice.services;

import io.sskuratov.mathparserservice.dao.Expressions;
import io.sskuratov.mathparserservice.repositories.ExpressionsRepository;
import io.sskuratov.parser.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
        return expressionsRepository.amountOnDate(
                LocalDateTime.of(date, LocalTime.of(0, 0, 0, 0)),
                LocalDateTime.of(date, LocalTime.of(23, 59, 59))
        );
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
    public List<String> listOfExpressionsOnDate(LocalDate date) {
        return expressionsRepository.listOfExpressionsOnDate(
                LocalDateTime.of(date, LocalTime.of(0, 0, 0, 0)),
                LocalDateTime.of(date, LocalTime.of(23, 59, 59))
        );
    }

    /**
     * Returns a list of calculated expressions have the specific operation
     * @param type operation token
     * @return
     */
    public List<String> listOfExpressionsByOperation(TokenType type) {
        return expressionsRepository.listOfExpressionsByOperation(type);
    }

    /**
     * Returns the most popular number(s) used among the expressions
     * @return
     */
    public List<String> popularNumber() {
        return expressionsRepository.popularNumber();
    }
}
