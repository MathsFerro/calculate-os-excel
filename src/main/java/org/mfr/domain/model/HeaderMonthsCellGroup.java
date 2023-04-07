package org.mfr.domain.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.mfr.commons.utils.CellFontUtils;
import org.mfr.commons.utils.CellStyleUtils;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HeaderMonthsCellGroup {
    private static final List<String> rowHeaderMonthsName;

    static {
        rowHeaderMonthsName = List.of("Jan", "Fev", "Mar", "Abr", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez");
    }

    public static void build(Workbook workbook) {
        CellStyle headerStyle = CellStyleUtils.buildDefaultHeaderStyle(workbook);
        CellFontUtils.buildDefaultHeaderFont(headerStyle, workbook);
        CellStyleUtils.addMediumBorders(headerStyle);


    }
}
