package org.mfr.commons.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;

import static org.mfr.commons.utils.NormalizeStringUtils.normalizeFirstDayOfMonth;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {
    public static String getFirstDayOfMonthName(Date date) {
        LocalDate firstDayOfMonth = LocalDate.of(date.getYear(), date.getMonth(), 1);
        DayOfWeek dayOfWeek = firstDayOfMonth.getDayOfWeek();
        return normalizeFirstDayOfMonth(dayOfWeek);
    }



    public static void main(String[] args) {
        DateUtils.getFirstDayOfMonthName(new Date(2023, 06, 1));
    }
}
