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
    public static String getFirstDayOfMonthName(Date date) {
        LocalDate firstDayOfMonth = LocalDate.of(date.getYear(), date.getMonth(), date.getDate());
        DayOfWeek dayOfWeek = firstDayOfMonth.getDayOfWeek();
        return normalizeFirstDayOfMonth(dayOfWeek);
    }

    public static Integer getMaxDayOfMonthInNumber(Date date) {
        LocalDate lastDayOfMonth = LocalDate.of(date.getYear(), date.getMonth(), date.getDate());
        return lastDayOfMonth.lengthOfMonth();
    }

    public static int getMonthsDifference(Date dateMin, Date dateMax) {
        Calendar calMin = Calendar.getInstance();
        Calendar calMax = Calendar.getInstance();
        calMin.setTime(dateMin);
        calMax.setTime(dateMax);

        int yearsDifference = calMax.get(Calendar.YEAR) - calMin.get(Calendar.YEAR);
        int monthsDifference = calMax.get(Calendar.MONTH) - calMin.get(Calendar.MONTH);
        return yearsDifference * 12 + monthsDifference;
    }

    public static Date addOneMonth(Date inputDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(inputDate);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();
    }

    public static void main(String[] args) {
        Date dateMin = new Date(2023, 4, 1);
        Date dateMax = new Date(2023, 11, 1);

        long differenceBetweenDates = getMonthsDifference(dateMin, dateMax);

        int m = 0;

        Date actualDate = dateMin;
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

            actualDate = addOneMonth(actualDate);
        }

    }
}
