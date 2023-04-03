package org.mfr.domain.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.mfr.commons.utils.CellFontUtils;
import org.mfr.commons.utils.CellStyleUtils;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HeaderCellGroup {
    private static final List<String> rowHeaderNames;

    static {
        rowHeaderNames = List.of("DATA", "O.S.", "VALOR_TOTAL", "TIPO PGTO", "PARCELAS", "BANDEIRA", "TAXA", "VALOR A RECEBER");
    }

    public static void build(Workbook workbook, Row header) {
        CellStyle headerStyle = CellStyleUtils.buildDefaultHeaderStyle(workbook);
        CellFontUtils.buildDefaultHeaderFont(headerStyle, workbook);

        for(int x=0;x<rowHeaderNames.size();x++) {
            Cell headerCell = header.createCell(x);
            headerCell.setCellValue(rowHeaderNames.get(x));
            headerCell.setCellStyle(headerStyle);
        }
    }
}
