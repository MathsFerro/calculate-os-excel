package org.mfr.domain.usecase;

import org.apache.poi.ss.usermodel.Sheet;
import org.mfr.domain.model.Celula;

import java.util.List;

public interface ReadExcelUseCase extends UseCase<Sheet, List<Celula>> {
    @Override
    List<Celula> apply(Sheet rows);
}
