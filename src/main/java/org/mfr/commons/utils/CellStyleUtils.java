package org.mfr.commons.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.Color;

import static org.mfr.commons.Constants.*;
import static org.mfr.domain.model.RgbUtils.generateXSSFColor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CellStyleUtils {
    public static CellStyle buildDefaultHeaderStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        XSSFColor xssfColor = generateXSSFColor(new Color(217, 225, 242));
        headerStyle.setFillForegroundColor(xssfColor);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        return headerStyle;
    }

    public static void buildDefaultStyle(Workbook workbook, CellStyle style) {
        DataFormat dataFormat = workbook.createDataFormat();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setDataFormat(dataFormat.getFormat("@"));

        XSSFColor xssfColor = generateXSSFColor(new Color(242, 242, 242));
        style.setFillForegroundColor(xssfColor);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }

    public static void addMediumBorders(CellStyle style) {
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
    }

    public static CellStyle buildDefaultCalendarStyle(Workbook workbook, double value) {
        CellStyle styleCell = workbook.createCellStyle();
        buildDefaultStyle(workbook, styleCell);
        addMediumBorders(styleCell);

        if(value > 0) {
            styleCell.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
            styleCell.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        }

        return styleCell;
    }
}
