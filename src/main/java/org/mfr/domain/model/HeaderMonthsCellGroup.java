package org.mfr.domain.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mfr.commons.utils.NormalizeStringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mfr.commons.Constants.*;
import static org.mfr.commons.utils.CustomRoundingMode.decreaseScaleToTwoDecimalCases;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HeaderMonthsCellGroup {
    public static void build(List<Celula> celulas, Workbook workbook, Sheet sheet, LocalDate currentDate, int rowNum) {
        String monthName = NormalizeStringUtils.normalize(currentDate.getMonth());
        Row row = sheet.createRow(rowNum-2);
        CellStyle monthStyle = workbook.createCellStyle();
        Cell cell = row.createCell(0);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName(DEFAULT_FONT);
        font.setFontHeightInPoints((short) HEADER_CALENDAR_MONTH_FONT_SIZE);
        font.setBold(HEADER_CALENDAR_MONTH_FONT_BOLD);

        monthStyle.setFont(font);

        monthStyle.setAlignment(HorizontalAlignment.LEFT);
        monthStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        row.setHeight((short) HEADER_CALENDAR_MONTH_HEIGHT);

        cell.setCellStyle(monthStyle);
        row.setRowStyle(monthStyle);

        cell.setCellValue(monthName + " " + currentDate.getYear() + " - R$ " + decreaseScaleToTwoDecimalCases(
                BigDecimal.valueOf(calculateTotalValueInMonth(celulas, currentDate))));
    }

    private static double calculateTotalValueInMonth(List<Celula> celulas, LocalDate currentDate) {
        return celulas.stream()
                .filter(celula -> celula.getDataProximoPagamento().getMonthValue()==currentDate.getMonthValue()
                        && celula.getDataProximoPagamento().getYear()==currentDate.getYear())
                .mapToDouble(celula -> celula.getValorParcelado().doubleValue())
                .sum();
    }
}
