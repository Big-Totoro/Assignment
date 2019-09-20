package io.sskuratov.mathparserservice.dao;

import io.sskuratov.parser.EvaluationResult;
import io.sskuratov.parser.Token;
import io.sskuratov.parser.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime createdDate;
    private String expression;
    private BigDecimal result;

    @OneToMany(cascade = ALL, fetch = FetchType.EAGER)
    private Set<Operations> operations = new HashSet<>();

    @OneToMany(cascade = ALL, fetch = FetchType.EAGER)
    private Set<Numbers> numbers = new HashSet<>();

    public static Expressions from(EvaluationResult result) {
        Expressions e = new Expressions();
        e.createdDate = LocalDateTime.now();
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
