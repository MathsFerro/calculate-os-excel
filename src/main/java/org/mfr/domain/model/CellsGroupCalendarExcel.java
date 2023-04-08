package org.mfr.domain.model;

import lombok.Builder;
import org.apache.poi.ss.usermodel.*;
import org.mfr.commons.utils.CellFontUtils;
import org.mfr.commons.utils.DateUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mfr.commons.utils.CellStyleUtils.buildDefaultCalendarStyle;
import static org.mfr.commons.utils.CustomRoundingMode.decreaseScaleToTwoDecimalCases;

@Builder
public class CellsGroupCalendarExcel {
    private LocalDate currentDate;
    private Row row;
    private Sheet sheet;
    private Workbook workbook;
    private List<Celula> celulas;
    private int rowNum;

    private int column;
    private int dayOfMonth;
    private int position;

    public void build() {
        String dayName = DateUtils.getFirstDayOfMonthName(currentDate);
        Integer idValueDayName = HeaderDaysNameCellGroup.getIdValueByDayName(dayName);
        int maxDayOfMonth = DateUtils.getMaxDayOfMonthInNumber(currentDate);

        while(dayOfMonth<=maxDayOfMonth) {
            currentDate = currentDate.withDayOfMonth(dayOfMonth);

            if(this.canBrokenLine(position)) {
                this.createBrokenLine();
                column = 0;
            }

            Cell cell = row.createCell(column++);

            if(position < idValueDayName) {
                position++;
                continue;
            }

            double totalValueInDay = calculateTotalValueInDay();

            cell.setCellValue(dayOfMonth);

            if(totalValueInDay>0)
                cell.setCellValue(dayOfMonth + " - R$ " + decreaseScaleToTwoDecimalCases(BigDecimal.valueOf(totalValueInDay)));

            CellStyle styleCell = buildDefaultCalendarStyle(workbook, totalValueInDay);

            CellFontUtils.buildDefaultCellFontWithValue(styleCell, workbook, totalValueInDay);

            cell.setCellStyle(styleCell);

            position++;
            dayOfMonth++;
        }
    }

    private double calculateTotalValueInDay() {
        return celulas.stream()
                .filter(celula -> this.currentDateIsValidToApplyPayment(currentDate, celula))
                .filter(celula -> this.nextPaymentDayIsEqualsCurrentDay(celula, dayOfMonth))
                .map(Celula::markNextPaymentDate)
                .mapToDouble(celula -> celula.getValorParcelado().doubleValue())
                .sum();
    }

    private boolean canBrokenLine(int x) {
        return x % 7 == 0 && x>0;
    }

    private void createBrokenLine() {
        rowNum = rowNum+1;
        row = sheet.createRow(rowNum);
        row.setHeight((short) 500);
    }

    private boolean currentDateIsValidToApplyPayment(LocalDate currentDate, Celula celula) {
        return celula.getDataProximoPagamento().equals(currentDate);
    }

    private boolean nextPaymentDayIsEqualsCurrentDay(Celula celula, int currentDayOfMonth) {
        return celula.getDataProximoPagamento().getDayOfMonth()==currentDayOfMonth;
    }
}