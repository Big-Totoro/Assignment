package io.sskuratov.parser;

public enum TokenType {
    NUM(0), PLUS(1), MINUS(2), MUL(3), DIV(4), LP(5), RP(6), POW(7), END(8);

    public final int type;

    TokenType(int type) {
        this.type = type;
    }

    public static String toSign(TokenType type) {
        String result;

        switch (type) {
            case PLUS :
                result = "+";
                break;
            case MINUS :
                result = "-";
                break;
            case MUL:
                result = "*";
                break;
            case DIV :
                result = "/";
                break;
            case LP :
                result = "(";
                break;
            case RP :
                result = ")";
                break;
            case POW :
                result = "^";
                break;
            default:
                throw new IllegalArgumentException("The NUM token cannot be converted to specific string representation");
        }

        return result;
    }
}
