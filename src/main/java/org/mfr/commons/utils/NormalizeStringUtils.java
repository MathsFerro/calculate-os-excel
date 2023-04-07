package org.mfr.commons.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.Normalizer;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NormalizeStringUtils {
    public static String normalizeFirstDayOfMonth(DayOfWeek dayOfWeek) {
        String dayNameInPortuguese = transformToPtBrDayOfMonth(dayOfWeek);
        dayNameInPortuguese = removeAccent(dayNameInPortuguese);
        return setFirstCharacterAsUpperCase(dayNameInPortuguese);
    }

    private static String removeAccent(String dayName) {
        String normalized = Normalizer.normalize(dayName, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }

    private static String transformToPtBrDayOfMonth(DayOfWeek dayOfWeek) {
        Locale locale = new Locale("pt", "BR");
        return dayOfWeek.getDisplayName(TextStyle.FULL, locale);
    }

    private static String setFirstCharacterAsUpperCase(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
