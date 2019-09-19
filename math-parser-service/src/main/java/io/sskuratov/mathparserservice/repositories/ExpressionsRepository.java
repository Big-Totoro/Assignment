package io.sskuratov.mathparserservice.repositories;

import io.sskuratov.mathparserservice.dao.Expressions;
import io.sskuratov.parser.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpressionsRepository extends JpaRepository<Expressions, Long> {

    Long amountByDate(@Param("created_on") LocalDateTime createdOn);

    Long amountByOperation(@Param("operation") TokenType operation);

    @Query("select id, created_on, expression, result from EXPRESSIONS where ID IN (select EXPRESSION_ID from OPERATIONS where NAME=:operation)")
    List<Expressions> listOfExpressionsByOperation(@Param("operation") TokenType operation);

    @Query("select NUMBER, count(NUMBER) from NUMBERS group by NUMBER order by count(NUMBER) desc")
    Optional<BigDecimal> popularNumber();
}
