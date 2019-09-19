package io.sskuratov.mathparserservice.dao;

import io.sskuratov.parser.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Operations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Long expression_id;

    @Enumerated(EnumType.ORDINAL)
    private TokenType name;

    public Operations(Long expression_id, TokenType name) {
        this.expression_id = expression_id;
        this.name = name;
    }
}
