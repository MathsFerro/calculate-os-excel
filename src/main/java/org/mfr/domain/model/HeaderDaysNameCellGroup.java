package org.mfr.domain.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mfr.commons.utils.CellStyleUtils;

import java.util.Comparator;
import java.util.List;
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

    public static void build(Sheet sheet, Workbook workbook, int rowNum) {
        Row row = sheet.createRow(rowNum-1);
        for(int j=0; j<HeaderDaysNameCellGroup.getDaysNameOfWeek().size(); j++) {
            CellStyle styleCell = workbook.createCellStyle();
            CellStyleUtils.buildDefaultStyle(workbook, styleCell);
            CellStyleUtils.addMediumBorders(styleCell);

            XSSFFont font = ((XSSFWorkbook) workbook).createFont();
            font.setFontName("Calibri");
            font.setFontHeightInPoints((short) 14);
            font.setBold(Boolean.TRUE);
            styleCell.setFont(font);

            row.setHeight((short) 500);

            Cell cell = row.createCell(j);
            cell.setCellStyle(styleCell);
            cell.setCellValue(HeaderDaysNameCellGroup.getDaysNameOfWeek().get(j));
        }
    }

    public static List<String> getDaysNameOfWeek() {
        return rowHeaderDaysName.entrySet()
                .stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .toList();
    }

    public static Integer getIdValueByDayName(String dayName) {
       return rowHeaderDaysName.entrySet().stream()
               .filter(integerStringEntry ->  integerStringEntry.getValue().equalsIgnoreCase(dayName))
               .findFirst()
               .map(Map.Entry::getKey)
               .orElse(null);
    }
}
