package org.mfr.application;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.mfr.domain.model.Celula;
import org.mfr.domain.port.ExcelActionsPort;
import org.mfr.domain.usecase.*;

import java.util.List;

@RequiredArgsConstructor
public class ProcessUseCaseImpl implements ProcessUseCase {
    private final GetExcelByPathUseCase getExcelByPathUseCase;
    private final CalculateFeeUseCase calculateFeeUseCase;
    private final BuildExcelUseCase buildExcelUseCase;
    private final ReadExcelUseCase readExcelUseCase;
    private final ExcelActionsPort excelActionsPort;

    public void apply() {
        Sheet sheet = this.getExcelByPathUseCase.apply("/template-os.xlsx");
        List<Celula> celulas = this.readExcelUseCase.apply(sheet);
        final Workbook workbook = new XSSFWorkbook();
        this.calculateFeeUseCase.apply(celulas);
        this.buildExcelUseCase.apply(workbook, celulas);
        this.excelActionsPort.write(workbook);
    }
}
