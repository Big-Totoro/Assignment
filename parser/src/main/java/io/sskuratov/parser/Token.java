package io.sskuratov.parser;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Token {

    TokenType tokenType;
    BigDecimal value;

    @Override
    public String toString() {
        return String.format("Token type: %s, value=%s", tokenType, value);
    }
}

