package io.sskuratov.mathparserservice.dao;

import io.sskuratov.parser.EvaluationResult;
import io.sskuratov.parser.Token;
import io.sskuratov.parser.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static javax.persistence.CascadeType.ALL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Expressions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate created_on;
    private String expression;
    private BigDecimal result;

    @OneToMany(cascade = ALL, fetch = FetchType.EAGER)
    private Set<Operations> operations = new HashSet<>();

    @OneToMany(cascade = ALL, fetch = FetchType.EAGER)
    private Set<Numbers> numbers = new HashSet<>();

    public Expressions(LocalDate created_on, String expression, BigDecimal result,
                       Set<Operations> operations, Set<Numbers> numbers) {
        this.created_on = created_on;
        this.expression = expression;
        this.result = result;
        this.operations = operations;
        this.numbers = numbers;
    }

    public static Expressions from(EvaluationResult result) {
        Expressions e = new Expressions();
        e.created_on = LocalDate.now();
        e.expression = result.getExpression();
        e.result = result.getResult();
        e.operations = result.getTokens().stream()
                .filter(t -> t.getTokenType() != TokenType.NUM)
                .map(Token::getTokenType)
                .map(Operations::new)
                .collect(Collectors.toSet());
        e.numbers = result.getTokens().stream()
                .filter(t -> t.getTokenType() == TokenType.NUM)
                .map(Token::getValue)
                .map(Numbers::new)
                .collect(Collectors.toSet());

        return e;
    }
}
