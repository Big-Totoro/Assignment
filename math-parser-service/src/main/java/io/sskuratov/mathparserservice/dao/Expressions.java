package io.sskuratov.mathparserservice.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Expressions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private LocalDateTime created_on;

    @NotNull
    private String expression;

    @NotNull
    private BigDecimal result;

    @OneToMany(cascade = ALL, fetch = FetchType.EAGER)
    private Set<Operations> operations = new HashSet<>();

    @OneToMany(cascade = ALL, fetch = FetchType.EAGER)
    private Set<Numbers> numbers = new HashSet<>();

    public Expressions(LocalDateTime created_on, String expression, BigDecimal result,
                       Set<Operations> operations, Set<Numbers> numbers) {
        this.created_on = created_on;
        this.expression = expression;
        this.result = result;
        this.operations = operations;
        this.numbers = numbers;
    }
}