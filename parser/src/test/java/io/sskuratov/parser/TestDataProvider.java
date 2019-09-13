package io.sskuratov.parser;

import java.math.BigDecimal;

public class TestDataProvider {

    public static Object[] providePositiveData() {
        return new Object[] {
                new Object[] {"10.50     +  20.50", BigDecimal.valueOf(31.0)},
                new Object[] {"100.50     -  20.50   - 20", BigDecimal.valueOf(60.0)},
                new Object[] {"100.50     +  20.50 - 80.00+16", BigDecimal.valueOf(57.0)},
                new Object[] {"100     *  20", BigDecimal.valueOf(2000.0)},
                new Object[] {"1400  /  20", BigDecimal.valueOf(70.0)},
                new Object[] {"14  +  2*    3  -6/   2", BigDecimal.valueOf(17.0)},
                new Object[] {"7 + 3 * ( 10 / (12 / (3 + 1) - 1) ) / (2 + 3) - 5 - 3 + (8)", BigDecimal.valueOf(10.0)},
                new Object[] {"3*2^2", BigDecimal.valueOf(12.0)},
                new Object[] {"7 + 3 * 2 ^ (2 ^ (1+1)) + 5", BigDecimal.valueOf(60.0)},
                new Object[] {"(-7*8+9-(9/4.5))^2", BigDecimal.valueOf(2401)},
                new Object[] {"9*1+4.5", BigDecimal.valueOf(13.5)}
        };
    }

    public static Object[] provideInvalidTokenData() {
        return new Object[] {
                new Object[] {"7+*3"},
        };
    }

    public static Object[] provideParsingErrorData() {
        return new Object[] {
                new Object[] {"(7+3 * 5"},
                new Object[] {"7+3) * 5"},
                new Object[] {"   "}
        };
    }
}
