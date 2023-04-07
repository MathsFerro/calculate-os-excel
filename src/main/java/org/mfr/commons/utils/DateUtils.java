package org.mfr.commons.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.mfr.domain.model.Celula;
import org.mfr.domain.model.FlagGroup;
import org.mfr.domain.model.HeaderDaysNameCellGroup;
import org.mfr.domain.model.TipoPagamento;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.mfr.commons.utils.NormalizeStringUtils.normalize;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateUtils {
    public static LocalDate dateToLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static String getFirstDayOfMonthName(LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.withDayOfMonth(1).getDayOfWeek();
        return normalize(dayOfWeek);
    }

    public static Integer getMaxDayOfMonthInNumber(LocalDate localDate) {
        return localDate.lengthOfMonth();
    }

    public static long getMonthsDifference(LocalDate dateMin, LocalDate dateMax) {
        return ChronoUnit.MONTHS.between(dateMin, dateMax);
    }

    public static void main(String[] args) {
        List<Celula> celulas = mockCelulas();
        celulas.forEach(celula -> celula.calculateValorAReceber(FlagGroup.getFlagByCode("V")));

        LocalDate dateMin = getMinLocalDate(celulas);
        LocalDate dateMax = getMaxLocalDate(celulas);

        long differenceBetweenDates = getMonthsDifference(dateMin, dateMax);

        int m = 0;

        LocalDate actualDate = dateMin;
        while(m<=differenceBetweenDates) {
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

                int actualDay = dayOfMonth;
                LocalDate finalActualDate = actualDate;
                double totalValueDay = celulas.stream()
                        .filter(celula -> {
                            if(maxDayOfMonth==30 && actualDay==30 && celula.getDataUltimoPagamento().getDayOfMonth()==31)
                                return true;

                            return celula.getDataUltimoPagamento().getDayOfMonth()==actualDay;
                        })
                        .filter(celula -> celula.getDataUltimoPagamento().isAfter(finalActualDate))
                        .mapToDouble(c -> c.getValorParcelado().doubleValue())
                        .sum();

                if(totalValueDay!=0.0)
                    System.out.print(" " + dayOfMonth++ + " R$ " + totalValueDay);
                else
                    System.out.print(" " + dayOfMonth++);
                x++;
            }

            System.out.println();

            m++;

            System.out.println("------------");

            actualDate = actualDate.plusMonths(1);
        }

    }

    private static List<Celula> mockCelulas() {
        List<Celula> celulas = new ArrayList<>();
        celulas.add(Celula.builder()
                        .data(LocalDate.of(2023, 4, 7))
                        .numeroOs("1")
                        .valorTotal(BigDecimal.valueOf(100))
                        .tipoPagamento(TipoPagamento.CRED)
                        .parcelas(3)
                        .bandeira("V")
                .build());

        celulas.add(Celula.builder()
                .data(LocalDate.of(2023, 6, 7))
                .numeroOs("1")
                .valorTotal(BigDecimal.valueOf(200))
                .tipoPagamento(TipoPagamento.CRED)
                .parcelas(5)
                .bandeira("V")
                .build());

        celulas.add(Celula.builder()
                .data(LocalDate.of(2023, 3, 2))
                .numeroOs("1")
                .valorTotal(BigDecimal.valueOf(500))
                .tipoPagamento(TipoPagamento.CRED)
                .parcelas(4)
                .bandeira("V")
                .build());

        celulas.add(Celula.builder()
                .data(LocalDate.of(2023, 5, 6))
                .numeroOs("1")
                .valorTotal(BigDecimal.valueOf(780))
                .tipoPagamento(TipoPagamento.CRED)
                .parcelas(12)
                .bandeira("V")
                .build());

        celulas.add(Celula.builder()
                .data(LocalDate.of(2023, 3, 31))
                .numeroOs("1")
                .valorTotal(BigDecimal.valueOf(180))
                .tipoPagamento(TipoPagamento.CRED)
                .parcelas(4)
                .bandeira("V")
                .build());

        return celulas;
    }

    private static LocalDate getMaxLocalDate(List<Celula> celulas) {
        Comparator<Celula> dateComparator = Comparator.comparing(Celula::getDataUltimoPagamento);
        return Collections.max(celulas, dateComparator).getDataUltimoPagamento();
    }

    private static LocalDate getMinLocalDate(List<Celula> celulas) {
        Comparator<Celula> dateComparator = Comparator.comparing(Celula::getDataUltimoPagamento);
        return Collections.min(celulas, dateComparator).getData();
    }
}
