package org.mfr.commons.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CellFontUtils {
    public static void buildDefaultHeaderFont(CellStyle headerStyle, Workbook workbook) {
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Calibri");
        font.setFontHeightInPoints((short) 12);
        font.setBold(Boolean.TRUE);
        headerStyle.setFont(font);
    }
}
