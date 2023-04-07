package org.mfr.domain.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HeaderDaysNameCellGroup {
    private static final List<String> rowHeaderDaysName;

    static {
        rowHeaderDaysName = List.of("Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", "Quinta-feira", "Sexta-feira", "Sabado");
    }

    public static void build() {

    }
}
