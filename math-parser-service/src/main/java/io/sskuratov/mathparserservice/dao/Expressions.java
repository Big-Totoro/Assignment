package io.sskuratov.mathparserservice.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expressions {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime created_on;
    private String expression;
    private BigDecimal result;

    public Expressions(LocalDateTime created_on, String expression, BigDecimal result) {
        this.created_on = created_on;
        this.expression = expression;
        this.result = result;
    }
}
