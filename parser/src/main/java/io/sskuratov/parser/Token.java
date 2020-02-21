package io.sskuratov.parser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Token {

    private TokenType tokenType;
    private BigDecimal value;

    @Override
    public String toString() {
        return String.format("Token type: %s, value=%s", tokenType, value);
    }
}

