package io.sskuratov.mathparserservice.dao;

import io.sskuratov.parser.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Operations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Expressions expression;

    @Enumerated(EnumType.ORDINAL)
    private TokenType name;

    public Operations(TokenType name) {
        this.name = name;
    }
}
