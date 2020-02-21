package io.sskuratov.parser;

public enum TokenType {
    NUM(0, ""), PLUS(1, "+"), MINUS(2, "-"), MUL(3, "*"),
    DIV(4, "/"), LP(5, "("), RP(6, ")"), POW(7, "^"),
    END(8, "E");

    private final int type;
    private final String sign;

    TokenType(int type, String sign) {
        this.type = type;
        this.sign = sign;
    }

    public int getType() {
        return type;
    }

    public String getSign() {
        return sign;
    }
}
