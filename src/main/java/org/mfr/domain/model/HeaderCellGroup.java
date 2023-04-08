package org.mfr.domain.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.mfr.commons.Constants;
import org.mfr.commons.utils.CellFontUtils;
import org.mfr.commons.utils.CellStyleUtils;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HeaderCellGroup {
    private static final List<String> rowHeaderNames;

    static {
        rowHeaderNames = List.of("DATA", "O.S.", "VALOR TOTAL", "TIPO PGTO", "PARCELAS", "BANDEIRA", "TAXA", "VALOR A RECEBER",
                "", "", "","BANDEIRA", "DEBITO", "1x", "2x a 6x", "7x a 12x");
    }

    public static void build(Workbook workbook, Row header) {
        CellStyle headerStyle = CellStyleUtils.buildDefaultHeaderStyle(workbook);

        CellFontUtils.buildDefaultHeaderCellFont(headerStyle, workbook);
        CellStyleUtils.buildDefaultHeaderStyle(workbook);
        CellStyleUtils.addMediumBorders(headerStyle);

        header.setHeight((short) Constants.HEADER_HEIGHT);

        for(int x=0;x<rowHeaderNames.size();x++) {
            Cell headerCell = header.createCell(x);
            String cellValue = rowHeaderNames.get(x);
            headerCell.setCellValue(cellValue);
            if(cellValue.isBlank())
                continue;
            headerCell.setCellStyle(headerStyle);
        }
    }
}
