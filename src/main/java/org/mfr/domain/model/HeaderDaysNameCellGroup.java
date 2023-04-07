package org.mfr.domain.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HeaderDaysNameCellGroup {
    private static final Map<Integer, String> rowHeaderDaysName;

    static {
        rowHeaderDaysName = Map.of(
                0,"Domingo",
                1,"Segunda-feira",
                2, "Terca-feira",
                3, "Quarta-feira",
                4, "Quinta-feira",
                5, "Sexta-feira",
                6, "Sabado"
        );
    }

    public static void build() {
    }

    public static Integer getIdValueByDayName(String dayName) {
       return rowHeaderDaysName.entrySet().stream()
               .filter(integerStringEntry ->  integerStringEntry.getValue().equalsIgnoreCase(dayName))
               .findFirst()
               .map(Map.Entry::getKey)
               .orElse(null);
    }
}
