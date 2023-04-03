package org.mfr.domain.usecase;

import org.mfr.domain.model.Celula;

import java.util.List;

public interface CalculateFeeUseCase extends UseCase.BaseUseCase<List<Celula>> {
    @Override
    void apply(List<Celula> celulas);
}
