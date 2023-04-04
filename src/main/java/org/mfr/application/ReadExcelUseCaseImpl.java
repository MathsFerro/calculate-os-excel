package org.mfr.application;

import org.apache.poi.ss.usermodel.*;
import org.mfr.commons.utils.CellStyleUtils;
import org.mfr.domain.model.Celula;
import org.mfr.domain.model.TipoPagamento;
import org.mfr.domain.usecase.ReadExcelUseCase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.mfr.commons.utils.Constants.*;

public class ReadExcelUseCaseImpl implements ReadExcelUseCase {
    @Override
    public List<Celula> apply(Sheet sheet) {
        List<Celula> celulas = new ArrayList<>();

        for(Row row : sheet) {
            if(row.getRowNum()==FIRST_ROW || this.emptyCell(row))
                continue;

            Celula celula = Celula.builder()
                    .data(nonNull(row.getCell(DATA_OS_CELL_NUM)) ? row.getCell(DATA_OS_CELL_NUM).getDateCellValue() : null)
                    .numeroOs(nonNull(row.getCell(NUMERO_OS_CELL_NUM)) ? String.valueOf(row.getCell(NUMERO_OS_CELL_NUM)) : null)
                    .valorTotal(nonNull(row.getCell(VALOR_TOTAL_CELL_NUM)) ? BigDecimal.valueOf(row.getCell(VALOR_TOTAL_CELL_NUM).getNumericCellValue()) : null)
                    .tipoPagamento(nonNull(row.getCell(TIPO_PAGAMENTO_CELL_NUM)) ? TipoPagamento.getTipoPagamento(row.getCell(TIPO_PAGAMENTO_CELL_NUM).getStringCellValue()) : null)
                    .parcelas(nonNull(row.getCell(PARCELA_CELL_NUM)) ? Integer.parseInt(row.getCell(PARCELA_CELL_NUM).getStringCellValue()) : 0)
                    .bandeira(nonNull(row.getCell(BANDEIRA_CELL_NUM)) ? row.getCell(BANDEIRA_CELL_NUM).getStringCellValue() : null)
                    .build();

            celulas.add(celula);
        }

        return celulas;
    }

    private boolean emptyCell(Row row) {
        return isNull(row.getCell(DATA_OS_CELL_NUM)) && isNull(row.getCell(NUMERO_OS_CELL_NUM)) && isNull(row.getCell(VALOR_TOTAL_CELL_NUM));
    }
}
