package org.mfr.application;

import org.mfr.domain.model.Celula;
import org.mfr.domain.model.Flag;
import org.mfr.domain.usecase.CalculateFeeUseCase;

import java.util.List;

import static java.util.Objects.isNull;
import static org.mfr.domain.model.FlagGroup.getFlagByCode;

public class CalculateFeeUseCaseImpl implements CalculateFeeUseCase {
    @Override
    public void apply(List<Celula> celulas) {
        for(Celula celula : celulas) {
            Flag flag = getFlagByCode(celula.getBandeira());
            if(isNull(flag))
                continue;

            celula.calculateNextDatesAndValorAReceber(flag);
        }
    }
}