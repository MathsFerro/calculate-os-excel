package org.mfr.application;

import org.apache.poi.ss.usermodel.*;
import org.mfr.commons.utils.CellSizeUtils;
import org.mfr.domain.model.Celula;
import org.mfr.domain.model.HeaderCellGroup;
import org.mfr.domain.usecase.BuildExcelUseCase;

import java.text.SimpleDateFormat;
import java.util.List;

import static org.mfr.commons.utils.CustomRoundingMode.decreaseScaleToTwoDecimalCases;

public class BuildExcelUseCaseImpl implements BuildExcelUseCase {
    private static final int COLUMNS_WIDTH = 5000;
    private static final int COLUMNS = 7;

    @Override
    public void apply(Workbook workbook, List<Celula> celulas) {
        Sheet sheet = workbook.createSheet("OS");
        CellSizeUtils.buildColumnWidth(sheet, COLUMNS, COLUMNS_WIDTH);
        HeaderCellGroup.build(workbook, sheet.createRow(0));

        for(int i=0;i<celulas.size();i++) {
            Row row = sheet.createRow(i+1);
            Celula celula = celulas.get(i);
            this.buildCellsInRow(workbook, celula, row);
        }
    }

    private void buildCellsInRow(Workbook workbook, Celula celula, Row row) {
        DataFormat dataFormat = workbook.createDataFormat();

        for (int column = 0; column <= COLUMNS; column++) {
            Cell cell = row.createCell(column);
            CellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setDataFormat(dataFormat.getFormat("@"));

            switch (column) {
                case 0 -> cell.setCellValue(new SimpleDateFormat("dd/MM/yyyy").format(celula.getData()));
                case 1 -> {
                    style.setDataFormat((short) 1);
                    cell.setCellValue(celula.getNumeroOs());
                }
                case 2 -> {
                    style.setDataFormat((short) 7);
                    cell.setCellValue(celula.getValorTotal().doubleValue());
                }
                case 3 -> cell.setCellValue(celula.getTipoPagamento().getTipo());
                case 4 -> cell.setCellValue(celula.getParcelas());
                case 5 -> cell.setCellValue(celula.getBandeira());
                case 6 -> {
                    style.setDataFormat((short) 7);
                    cell.setCellValue(decreaseScaleToTwoDecimalCases(celula.getTaxa()).doubleValue());
                }
                case 7 -> {
                    style.setDataFormat((short) 7);
                    cell.setCellValue(decreaseScaleToTwoDecimalCases(celula.getValorAReceber()).doubleValue());
                }
                default -> {}
            }

            cell.setCellStyle(style);
        }
    }
}
