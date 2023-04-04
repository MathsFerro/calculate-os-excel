package org.mfr.application;

import org.apache.poi.ss.usermodel.*;
import org.mfr.commons.utils.CellSizeUtils;
import org.mfr.commons.utils.CellStyleUtils;
import org.mfr.domain.model.Celula;
import org.mfr.domain.model.Flag;
import org.mfr.domain.model.FlagGroup;
import org.mfr.domain.model.HeaderCellGroup;
import org.mfr.domain.usecase.BuildExcelUseCase;

import java.text.SimpleDateFormat;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.mfr.commons.utils.CustomRoundingMode.decreaseScaleToTwoDecimalCases;

public class BuildExcelUseCaseImpl implements BuildExcelUseCase {
    private static final int COLUMNS_WIDTH = 5000;
    private static final int COLUMNS = 7;
    private static final int COLUMNS_FLAG = 5;
    private static final int FLAG_COLUMN_STARTING_WITH = 11;

    @Override
    public void apply(Workbook workbook, List<Celula> celulas) {
        Sheet sheet = workbook.createSheet("OS");
        CellSizeUtils.buildColumnWidth(sheet, COLUMNS, COLUMNS_WIDTH);
        HeaderCellGroup.build(workbook, sheet.createRow(0));

        this.buildDefaultCells(sheet, workbook, celulas);
        this.buildFlagGroupCells(sheet, workbook);
    }

    private void buildDefaultCells(Sheet sheet, Workbook workbook, List<Celula> celulas) {
        for(int i=0;i<celulas.size();i++) {
            Row row = sheet.createRow(i+1);
            row.setHeight((short) 500);
            Celula celula = celulas.get(i);
            this.buildCellsInRow(workbook, celula, row);
        }
    }

    private void buildFlagGroupCells(Sheet sheet, Workbook workbook) {
        for(int i=0;i<FlagGroup.getFlags().size();i++) {
            Row row = nonNull(sheet.getRow(i+1)) ? sheet.getRow(i+1) : sheet.createRow(i+1);
            Flag flag = FlagGroup.getFlags().get(i);
            this.buildCellsFlagInRow(workbook, flag, row);
        }
    }

    private void buildCellsInRow(Workbook workbook, Celula celula, Row row) {
        for (int column = 0; column <= COLUMNS; column++) {
            Cell cell = row.createCell(column);

            CellStyle style = workbook.createCellStyle();
            CellStyleUtils.buildDefaultStyle(workbook, style);
            CellStyleUtils.addMediumBorders(style);

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

    private void buildCellsFlagInRow(Workbook workbook, Flag flag, Row row) {
        for (int column = 0; column <= COLUMNS_FLAG; column++) {
            Cell cell = row.createCell(column+FLAG_COLUMN_STARTING_WITH);

            CellStyle style = workbook.createCellStyle();
            CellStyleUtils.buildDefaultStyle(workbook, style);
            CellStyleUtils.addMediumBorders(style);

            switch (column) {
                case 0 -> cell.setCellValue(flag.getName());
                case 1 -> {
                    style.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));
                    cell.setCellValue(decreaseScaleToTwoDecimalCases(flag.getDebitPercentage()).doubleValue()/100);
                }
                case 2 -> {
                    style.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));
                    cell.setCellValue(decreaseScaleToTwoDecimalCases(flag.getCredit1x()).doubleValue()/100);
                }
                case 3 -> {
                    style.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));
                    cell.setCellValue(decreaseScaleToTwoDecimalCases(flag.getCredit2x6x()).doubleValue()/100);
                }
                case 4 -> {
                    style.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));
                    cell.setCellValue(decreaseScaleToTwoDecimalCases(flag.getCredit7x12x()).doubleValue()/100);
                }
            }

            cell.setCellStyle(style);
        }
    }
}
