package io.sskuratov.mathparserservice.repositories;

import io.sskuratov.mathparserservice.dao.Expressions;
import io.sskuratov.parser.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpressionsRepository extends JpaRepository<Expressions, Long> {

    /**
     * Returns amount of calculated expressions on date
     * @param createdOn
     * @return
     */
    @Query(value = "select count(*) from EXPRESSIONS where CREATED_ON BETWEEN ':createdOn 0:00:00' AND '2019-09-19 23:59:59'", nativeQuery = true)
    Long amountOnDate(@Param("created_on") LocalDate createdOn);

    /**
     * Returns amount of calculated expressions have the specific operation
     * @param operation operation token
     * @return
     */
    @Query(value = "select count(*) from EXPRESSIONS where ID IN (select EXPRESSION_ID from OPERATIONS where TYPE=?1)", nativeQuery = true)
    Long amountByOperation(@Param("operation") TokenType operation);

    /**
     * Returns a list of calculated expressions on date
     * @param createdOn
     * @return
     */
    @Query(value = "select * from EXPRESSIONS where CREATED_ON BETWEEN '2019-09-17 0:00:00' AND '2019-09-17 23:59:59'", nativeQuery = true)
    List<Expressions> listOfExpressionsOnDate(@Param("created_on") LocalDate createdOn);

    /**
     * Returns a list of calculated expressions have the specific operation
     * @param operation operation token
     * @return
     */
    @Query(value = "select id, created_on, expression, result from EXPRESSIONS where ID IN (select EXPRESSION_ID from OPERATIONS where TYPE=?1)", nativeQuery = true)
    List<Expressions> listOfExpressionsByOperation(@Param("operation") TokenType operation);

    /**
     * Returns the most popular number(s) used among the expressions
     * @return
     */
    @Query(value = "select num.NUMBER from NUMBERS num group by num.NUMBER having count(num.NUMBER) = (select count(n.NUMBER) from NUMBERS n group by n.NUMBER order by count(n.NUMBER) desc limit 1)", nativeQuery = true)
    BigDecimal popularNumber();
}
