package io.sskuratov.mathparserservice.dao;

import io.sskuratov.parser.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Operations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Expressions expression;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private TokenType name;

    public Operations(TokenType name) {
        this.name = name;
    }
}
