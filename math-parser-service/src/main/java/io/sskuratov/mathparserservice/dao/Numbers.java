package io.sskuratov.mathparserservice.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Numbers {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Long expression_id;

    private BigDecimal number;

    public Numbers(Long expression_id, BigDecimal number) {
        this.expression_id = expression_id;
        this.number = number;
    }
}
