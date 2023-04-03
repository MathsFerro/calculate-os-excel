package org.mfr.application;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Sheet;
import org.mfr.domain.port.ExcelActionsPort;
import org.mfr.domain.usecase.GetExcelByPathUseCase;

import java.io.File;

@RequiredArgsConstructor
public class GetExcelByPathUseCaseImpl implements GetExcelByPathUseCase {
    private final ExcelActionsPort excelActionsPort;

    @Override
    public Sheet apply(String path) {
        try {
            String absolutePath = new File(".").getAbsolutePath() + path;
            return this.excelActionsPort.getByPath(absolutePath);
        } catch (Exception ex) {
            System.err.println("Excel não encontrado.");
            return null;
        }
    }
}
