package org.mfr.commons.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mfr.domain.model.HeaderDaysNameCellGroup;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

import static org.mfr.commons.utils.NormalizeStringUtils.normalizeFirstDayOfMonth;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {
    public static String getFirstDayOfMonthName(Date date) {
        LocalDate firstDayOfMonth = LocalDate.of(date.getYear(), date.getMonth(), date.getDate());
        DayOfWeek dayOfWeek = firstDayOfMonth.getDayOfWeek();
        return normalizeFirstDayOfMonth(dayOfWeek);
    }

    public static Integer getMaxDayOfMonthInNumber(Date date) {
        LocalDate lastDayOfMonth = LocalDate.of(date.getYear(), date.getMonth(), date.getDate());
        return lastDayOfMonth.lengthOfMonth();
    }

    public static void main(String[] args) {
        Date date = new Date(2023, 5, 7);
        String dayName = DateUtils.getFirstDayOfMonthName(date);
        Integer idDay = HeaderDaysNameCellGroup.getIdValueByDayName(dayName);
        int maxDayOfMonth = DateUtils.getMaxDayOfMonthInNumber(date);

        int dayOfMonth = 1;
        int x = 0;

        while(dayOfMonth<=maxDayOfMonth) {
            if(x<=idDay) {
                System.out.print(" x");
                x++;
                continue;
            }

            if(x%7==0)
                System.out.println();


            System.out.print(" " + dayOfMonth++);
            x++;
        }
    }
}
