package org.mfr.domain.usecase;

import org.apache.poi.ss.usermodel.Workbook;
import org.mfr.domain.model.Celula;

import java.util.List;

public interface BuildExcelUseCase {
    void apply(Workbook workbook, List<Celula> celulas);
}
