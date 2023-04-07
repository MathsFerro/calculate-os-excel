package org.mfr.commons.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mfr.domain.model.HeaderDaysNameCellGroup;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

import static org.mfr.commons.utils.NormalizeStringUtils.normalizeFirstDayOfMonth;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {
    public static String getFirstDayOfMonthName(LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return normalizeFirstDayOfMonth(dayOfWeek);
    }

    public static Integer getMaxDayOfMonthInNumber(LocalDate localDate) {
        return localDate.lengthOfMonth();
    }

    public static long getMonthsDifference(LocalDate dateMin, LocalDate dateMax) {
        return ChronoUnit.MONTHS.between(dateMin, dateMax);
    }

    public static void main(String[] args) {
        LocalDate dateMin = LocalDate.of(2023, 4, 1);
        LocalDate dateMax = LocalDate.of(2025, 9, 1);

        long differenceBetweenDates = getMonthsDifference(dateMin, dateMax);

        int m = 0;

        LocalDate actualDate = dateMin;
        while(m<differenceBetweenDates) {
            String dayName = DateUtils.getFirstDayOfMonthName(actualDate);
            Integer idDay = HeaderDaysNameCellGroup.getIdValueByDayName(dayName);
            int maxDayOfMonth = DateUtils.getMaxDayOfMonthInNumber(actualDate);

            int x = 0;
            int dayOfMonth = 1;
            while(dayOfMonth<=maxDayOfMonth) {
                if(x<idDay) {
                    System.out.print(" x");
                    x++;
                    continue;
                }

                if(x%7==0)
                    System.out.println();

                System.out.print(" " + dayOfMonth++);
                x++;
            }

            System.out.println();

            m++;

            System.out.println("------------");

            actualDate = actualDate.plusMonths(1);
//            actualDate = addOneMonth(actualDate);
//            if(actualDate.getMonth()==11)
//                actualDate = addOneYear(actualDate);
        }

    }
}
