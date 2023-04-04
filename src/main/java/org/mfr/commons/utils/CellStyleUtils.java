package org.mfr.commons.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CellStyleUtils {
    public static CellStyle buildDefaultHeaderStyle(Workbook workbook) {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return headerStyle;
    }


    public static void buildDefaultStyle(Workbook workbook, CellStyle style) {
        DataFormat dataFormat = workbook.createDataFormat();
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setDataFormat(dataFormat.getFormat("@"));
    }

    public static void addMediumBorders(CellStyle style) {
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
    }
}
