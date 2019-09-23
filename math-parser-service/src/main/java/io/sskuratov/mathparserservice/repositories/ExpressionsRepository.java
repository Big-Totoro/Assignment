package io.sskuratov.mathparserservice.repositories;

import io.sskuratov.mathparserservice.dao.Expressions;
import io.sskuratov.parser.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpressionsRepository extends JpaRepository<Expressions, Long> {

    /**
     * Returns amount of calculated expressions on date
     * @param beginDate
     * @param endDate
     * @return
     */
    @Query(value = "SELECT COUNT(e.expression) FROM Expressions e WHERE e.createdDate BETWEEN :beginDate AND :endDate")
    Long amountOnDate(@Param("beginDate") LocalDateTime beginDate, @Param("endDate") LocalDateTime endDate);

    /**
     * Returns amount of calculated expressions have the specific operation
     * @param operation token
     * @return
     */
    @Query(value = "SELECT COUNT(DISTINCT e.expression) FROM Expressions e INNER JOIN e.operations AS o WHERE o.type=:operation")
    Long amountByOperation(@Param("operation") TokenType operation);

    /**
     * Returns a list of calculated expressions on date
     * @param beginDate
     * @param endDate
     * @return
     */
    @Query(value = "SELECT e.expression FROM Expressions e WHERE e.createdDate BETWEEN :beginDate AND :endDate")
    List<String> listOfExpressionsOnDate(@Param("beginDate") LocalDateTime beginDate, @Param("endDate") LocalDateTime endDate);

    /**
     * Returns a list of calculated expressions have the specific operation
     * @param operation operation token
     * @return
     */
    @Query(value = "SELECT DISTINCT e.expression FROM Expressions e INNER JOIN e.operations AS o WHERE o.type=:operation")
    List<String> listOfExpressionsByOperation(@Param("operation") TokenType operation);

    /**
     * Returns the most popular number(s) used among the expressions
     * @return
     */
    @Query(value = "SELECT num.value FROM Numbers num GROUP BY num.value HAVING COUNT(num.value) = " +
            "(SELECT COUNT(n.value) FROM NUMBERS n GROUP BY n.value ORDER BY COUNT(n.value) DESC limit 1)",
            nativeQuery = true)
    List<String> popularNumber();
}