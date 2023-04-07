package org.mfr.application;

import org.apache.poi.ss.usermodel.*;
import org.mfr.commons.utils.CellSizeUtils;
import org.mfr.commons.utils.CellStyleUtils;
import org.mfr.commons.utils.DateUtils;
import org.mfr.commons.utils.NormalizeStringUtils;
import org.mfr.domain.model.*;
import org.mfr.domain.usecase.BuildExcelUseCase;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Objects.nonNull;
import static org.mfr.commons.utils.CustomRoundingMode.decreaseScaleToTwoDecimalCases;
import static org.mfr.commons.utils.DateUtils.getMonthsDifference;

public class BuildExcelUseCaseImpl implements BuildExcelUseCase {
    private static final int COLUMNS_WIDTH = 5000;
    private static final int COLUMNS = 7;
    private static final int COLUMNS_FLAG = 5;
    private static final int FLAG_COLUMN_STARTING_WITH = 11;
    private int startRowCalendar;

    @Override
    public void apply(Workbook workbook, List<Celula> celulas) {
        Sheet sheet = workbook.createSheet("OS");
        CellSizeUtils.buildColumnWidth(sheet, COLUMNS, COLUMNS_WIDTH);

        HeaderCellGroup.build(workbook, sheet.createRow(0));

        this.startRowCalendar = celulas.size()+5;

        this.buildDefaultCells(sheet, workbook, celulas);
        this.buildFlagGroupCells(sheet, workbook);
        this.buildCalendarOfFuturePayments(sheet, workbook, celulas);
    }

    public void buildCalendarOfFuturePayments(Sheet sheet, Workbook workbook, List<Celula> celulas) {
        LocalDate dateMin = getMinLocalDate(celulas);
        LocalDate dateMax = getMaxLocalDate(celulas);

        int rowNum = startRowCalendar;
        long differenceBetweenDatesInMonths = getMonthsDifference(dateMin, dateMax);

        LocalDate actualDate = dateMin;

        for(int m=0; m<differenceBetweenDatesInMonths; m++) {
            String dayName = DateUtils.getFirstDayOfMonthName(actualDate);
            Integer idValueDayName = HeaderDaysNameCellGroup.getIdValueByDayName(dayName);
            int maxDayOfMonth = DateUtils.getMaxDayOfMonthInNumber(actualDate);

            Row row = sheet.createRow(rowNum-2);
            row.setHeight((short) 500);
            String monthName = NormalizeStringUtils.normalize(actualDate.getMonth());
            row.createCell(0).setCellValue(monthName + " " + actualDate.getYear());

            row = sheet.createRow(rowNum-1);
            for(int x=0; x<HeaderDaysNameCellGroup.getDaysNameOfWeek().size(); x++) {
                CellStyle styleCell = workbook.createCellStyle();
                CellStyleUtils.buildDefaultStyle(workbook, styleCell);
                CellStyleUtils.addMediumBorders(styleCell);

                row.setHeight((short) 500);
                row.createCell(x).setCellValue(HeaderDaysNameCellGroup.getDaysNameOfWeek().get(x));
            }

            row = sheet.createRow(rowNum);
            row.setHeight((short) 500);

            int x = 0;
            int column = 0;
            int dayOfMonth = 1;

            while(dayOfMonth<=maxDayOfMonth) {
                if(x % 7 == 0 && x>0) {
                    rowNum = rowNum+1;
                    row = sheet.createRow(rowNum);
                    row.setHeight((short) 500);
                    column = 0;
                }

                Cell cell = row.createCell(column++);

                if(x<idValueDayName) {
                    cell.setCellValue("X");
                    x++;
                    continue;
                }

                CellStyle styleCell = workbook.createCellStyle();
                CellStyleUtils.buildDefaultStyle(workbook, styleCell);
                CellStyleUtils.addMediumBorders(styleCell);

                int actualDay = dayOfMonth;
                LocalDate finalActualDate = actualDate;
                double totalValueDay = celulas.stream()
                        .filter(celula -> {
                            if(maxDayOfMonth==30 && actualDay==30 && celula.getDataUltimoPagamento().getDayOfMonth()==31)
                                return true;

                            return celula.getDataUltimoPagamento().getDayOfMonth()==actualDay;
                        })
                        .filter(celula -> celula.getDataUltimoPagamento().isAfter(finalActualDate))
                        .mapToDouble(celula -> celula.getValorParcelado().doubleValue())
                        .sum();

                cell.setCellValue("Dia " + actualDay + " R$ " + decreaseScaleToTwoDecimalCases(BigDecimal.valueOf(totalValueDay)));

                x++;
                dayOfMonth++;
            }

            actualDate = actualDate.plusMonths(1);
            rowNum+=6;
        }
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
            row.setHeight((short) 500);
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

    private LocalDate getMaxLocalDate(List<Celula> celulas) {
        Comparator<Celula> dateComparator = Comparator.comparing(Celula::getDataUltimoPagamento);
        return Collections.max(celulas, dateComparator).getDataUltimoPagamento();
    }

    private LocalDate getMinLocalDate(List<Celula> celulas) {
        Comparator<Celula> dateComparator = Comparator.comparing(Celula::getData);
        return Collections.min(celulas, dateComparator).getData();
    }
}
