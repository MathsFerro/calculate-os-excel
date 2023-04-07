package org.mfr.domain.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HeaderMonthsCellGroup {
    private static final List<String> rowHeaderMonthsName;

    static {
        rowHeaderMonthsName = List.of("Jan", "Fev", "Mar", "Abr", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez");
    }

    public static void build(Date min, Date max) {

    }
}
