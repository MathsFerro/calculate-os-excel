package org.mfr.domain.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlagGroup {
    private static final List<Flag> flags;

    static {
        flags = List.of(
            new Flag("VISA", "V", BigDecimal.valueOf(0.99), BigDecimal.valueOf(1.93), BigDecimal.valueOf(2.11), BigDecimal.valueOf(2.69)),
            new Flag("MASTER", "M", BigDecimal.valueOf(0.99), BigDecimal.valueOf(1.93), BigDecimal.valueOf(2.11), BigDecimal.valueOf(2.69)),
            new Flag("ELO", "E",BigDecimal.valueOf(1.5), BigDecimal.valueOf(2.29), BigDecimal.valueOf(2.62), BigDecimal.valueOf(3.14)),
            new Flag("AMERICAN", "A", BigDecimal.valueOf(0), BigDecimal.valueOf(2.99), BigDecimal.valueOf(3), BigDecimal.valueOf(3.25)),
            new Flag("HIPERCARD", "H", BigDecimal.valueOf(0), BigDecimal.valueOf(1.88), BigDecimal.valueOf(2.61), BigDecimal.valueOf(3.09))
        );
    }

    public static Flag getFlagByCode(String code) {
        return flags.stream().filter(flag -> flag.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElse(null);
    }

    public static List<Flag> getFlags() {
        return flags;
    }
}
