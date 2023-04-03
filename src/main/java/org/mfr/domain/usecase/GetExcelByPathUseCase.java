package org.mfr.domain.usecase;

import org.apache.poi.ss.usermodel.Sheet;

public interface GetExcelByPathUseCase extends UseCase<String, Sheet> {
    @Override
    Sheet apply(String s);
}
