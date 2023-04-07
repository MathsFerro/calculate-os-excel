package org.mfr.commons.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.text.Normalizer;
import java.time.DayOfWeek;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NormalizeStringUtils {
    public static String normalize(DayOfWeek dayOfWeek) {
        String dayNameInPortuguese = transformToPtBr(dayOfWeek);
        dayNameInPortuguese = removeAccent(dayNameInPortuguese);
        return setFirstCharacterAsUpperCase(dayNameInPortuguese);
    }

    public static String normalize(Month month) {
        String dayNameInPortuguese = transformToPtBr(month);
        dayNameInPortuguese = removeAccent(dayNameInPortuguese);
        return setFirstCharacterAsUpperCase(dayNameInPortuguese);
    }

    public static String transformToPtBr(DayOfWeek dayOfWeek) {
        Locale locale = new Locale("pt", "BR");
        return dayOfWeek.getDisplayName(TextStyle.FULL, locale);
    }

    public static String transformToPtBr(Month month) {
        Locale locale = new Locale("pt", "BR");
        return month.getDisplayName(TextStyle.FULL, locale);
    }

    private static String removeAccent(String str) {
        String normalized = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }

    private static String setFirstCharacterAsUpperCase(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }
}
