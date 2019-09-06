package io.sskuratov;

import io.sskuratov.exceptions.InvalidTokenException;
import io.sskuratov.exceptions.ParsingException;

import java.math.BigDecimal;

public interface Parser {

    /**
     * Вычисляет значение арифметического выражения, представленного в виде строки.
     * @param expression выходное арифметическое выражение
     * @return Результат вычисления
     * @throws ParsingException выбрасывается, если не удается разобрать входное арифметическое выражение
     */
    BigDecimal parse(String expression) throws ParsingException, InvalidTokenException;
}
