package org.mfr.application;

import org.apache.poi.ss.usermodel.*;
import org.mfr.commons.utils.CellFontUtils;
import org.mfr.commons.utils.CellSizeUtils;
import org.mfr.commons.utils.CellStyleUtils;
import org.mfr.domain.model.*;
import org.mfr.domain.usecase.BuildExcelUseCase;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.mfr.commons.Constants.COLUMN_HEIGHT;
import static org.mfr.commons.Constants.COLUMN_WIDTH;
import static org.mfr.commons.utils.CustomRoundingMode.decreaseScaleToTwoDecimalCases;
import static org.mfr.commons.utils.DateUtils.getDifferenceBetweenDates;

public class BuildExcelUseCaseImpl implements BuildExcelUseCase {
    private static final int COLUMNS = 7;
    private static final int COLUMNS_FLAG = 5;
    private static final int FLAG_COLUMN_STARTING_WITH = 11;

    private static final int START_COLUMN = 0;
    private static final int START_DAY = 1;
    private static final int START_POINTER = 0;


    private int startRowCalendar;

    @Override
    public void apply(Workbook workbook, List<Celula> celulas) {
        Sheet sheet = workbook.createSheet("OS");
        CellSizeUtils.buildColumnWidth(sheet, COLUMNS, COLUMN_WIDTH);

        HeaderCellGroup.build(workbook, sheet.createRow(0));
        sheet.setDisplayGridlines(false);

        this.startRowCalendar = celulas.size() + 5;

        this.buildDefaultCells(sheet, workbook, celulas);
        this.buildFlagGroupCells(sheet, workbook);
        this.buildCalendarOfFuturePayments(sheet, workbook, celulas);
    }

    public void buildCalendarOfFuturePayments(Sheet sheet, Workbook workbook, List<Celula> celulas) {
        LocalDate dateMin = getMinLocalDate(celulas);
        LocalDate dateMax = getMaxLocalDate(celulas);

        int rowNum = startRowCalendar;

        Period differenceBetweenDates = getDifferenceBetweenDates(dateMin, dateMax);
        long differenceBetweenDatesInMonth = differenceBetweenDates.getMonths();

        dateMin = dateMin.withDayOfMonth(1);
        LocalDate currentDate = dateMin;

        for(int m=0; m<=differenceBetweenDatesInMonth; m++) {
            HeaderMonthsCellGroup.build(celulas, workbook, sheet, currentDate, rowNum);
            HeaderDaysNameCellGroup.build(sheet, workbook, rowNum);

            Row row = sheet.createRow(rowNum);
            row.setHeight((short) COLUMN_HEIGHT);

            CellsGroupCalendarExcel cellsGroupCalendarExcel = CellsGroupCalendarExcel.builder()
                    .currentDate(currentDate)
                    .row(row)
                    .sheet(sheet)
                    .workbook(workbook)
                    .celulas(celulas)
                    .rowNum(rowNum)
                    .column(START_COLUMN)
                    .dayOfMonth(START_DAY)
                    .position(START_POINTER)
                    .build();

            cellsGroupCalendarExcel.build();

            currentDate = currentDate.plusMonths(1);
            rowNum+=10;
        }
    }

    private void buildDefaultCells(Sheet sheet, Workbook workbook, List<Celula> celulas) {
        for(int i=0;i<celulas.size();i++) {
            Row row = sheet.createRow(i+1);
            row.setHeight((short) COLUMN_HEIGHT);
            Celula celula = celulas.get(i);
            this.buildCellsInRow(workbook, celula, row);
        }
    }

    private void buildFlagGroupCells(Sheet sheet, Workbook workbook) {
        for(int i=0;i<FlagGroup.getFlags().size();i++) {
            Row row = nonNull(sheet.getRow(i+1)) ? sheet.getRow(i+1) : sheet.createRow(i+1);
            Flag flag = FlagGroup.getFlags().get(i);
            row.setHeight((short) COLUMN_HEIGHT);
            this.buildCellsFlagInRow(workbook, flag, row);
        }
    }

    private void buildCellsInRow(Workbook workbook, Celula celula, Row row) {
        for (int column = 0; column <= COLUMNS; column++) {
            Cell cell = row.createCell(column);

            CellStyle style = workbook.createCellStyle();
            CellStyleUtils.buildDefaultStyle(workbook, style);
            CellFontUtils.buildDefaultCellFont(style, workbook);
            CellStyleUtils.addMediumBorders(style);

            switch (column) {
                case 0 -> cell.setCellValue(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(celula.getData()));
                case 1 -> cell.setCellValue(celula.getNumeroOs().replace(".0", ""));
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
        for (int column = 0; column < COLUMNS_FLAG; column++) {
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

    private LocalDate getMaxLocalDate(List<Celula> celulas) {
        Comparator<Celula> dateComparator = Comparator.comparing(Celula::getDataUltimoPagamento);
        return Collections.max(celulas, dateComparator).getDataUltimoPagamento();
    }

    private LocalDate getMinLocalDate(List<Celula> celulas) {
        Comparator<Celula> dateComparator = Comparator.comparing(Celula::getData);
        return Collections.min(celulas, dateComparator).getData();
    }
}
