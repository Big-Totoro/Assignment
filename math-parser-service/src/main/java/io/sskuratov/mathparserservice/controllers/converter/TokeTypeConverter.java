package io.sskuratov.mathparserservice.controllers.converter;

import io.sskuratov.parser.TokenType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class TokeTypeConverter implements Converter<String, TokenType> {
    @Override
    public TokenType convert(String s) {
        return TokenType.valueOf(s.toUpperCase());
    }
}
