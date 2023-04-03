package org.mfr.domain.port;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public interface ExcelActionsPort {
    Sheet getByPath(String path);
    void write(Workbook workbook);
}
