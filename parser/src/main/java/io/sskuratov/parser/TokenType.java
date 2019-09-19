package io.sskuratov.parser;

public enum TokenType {
    NUM(0), PLUS(1), MINUS(2), MULTIPLY(3), DIV(4), LP(5), RP(6), POW(7), END(8);

    public final int type;

    TokenType(int type) {
        this.type = type;
    }
}
