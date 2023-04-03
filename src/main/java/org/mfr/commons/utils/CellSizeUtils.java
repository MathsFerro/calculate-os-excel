package org.mfr.commons.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CellSizeUtils {
    public static void buildColumnWidth(Sheet sheet, int columns, int size) {
        for (int x=0; x<=columns; x++)
            sheet.setColumnWidth(x, size);
    }
}
