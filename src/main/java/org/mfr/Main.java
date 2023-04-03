package org.mfr;

import org.mfr.application.*;
import org.mfr.domain.port.ExcelActionsPort;
import org.mfr.domain.usecase.*;
import org.mfr.infra.ExcelActions;

public class Main {
    public static void main(String[] args) {
        ExcelActionsPort excelActionsPort = new ExcelActions();
        ReadExcelUseCase readExcelUseCase = new ReadExcelUseCaseImpl();
        GetExcelByPathUseCase getExcelByPathUseCase = new GetExcelByPathUseCaseImpl(excelActionsPort);
        BuildExcelUseCase buildExcelUseCase = new BuildExcelUseCaseImpl();
        CalculateFeeUseCase calculateFeeUseCase = new CalculateFeeUseCaseImpl();
        ProcessUseCase processUseCase = new ProcessUseCaseImpl(getExcelByPathUseCase, calculateFeeUseCase, buildExcelUseCase, readExcelUseCase, excelActionsPort);

        processUseCase.apply();
    }
}