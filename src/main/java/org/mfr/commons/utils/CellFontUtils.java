package org.mfr.commons.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import static org.mfr.commons.Constants.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CellFontUtils {
    public static void buildDefaultHeaderCellFont(CellStyle style, Workbook workbook) {
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName(HEADER_FONT);
        font.setFontHeightInPoints((short) HEADER_FONT_SIZE);
        font.setBold(HEADER_FONT_BOLD);
        style.setFont(font);
    }

    public static void buildDefaultCellFont(CellStyle style, Workbook workbook) {
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName(FONT);
        font.setFontHeightInPoints((short) FONT_SIZE);
        font.setBold(FONT_BOLD);
        style.setFont(font);
    }
}
