package org.mfr.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Celula {
    private static final BigDecimal CEM_PORCENTO = BigDecimal.valueOf(100);

    private Date data;
    private Integer numeroOs;
    private BigDecimal valorTotal;
    private TipoPagamento tipoPagamento;
    private Integer parcelas;
    private String bandeira;
    private BigDecimal taxa;
    private BigDecimal taxaAntecipacao;
    private BigDecimal valorAReceber;

    public void calculateValorAReceber(Flag flag) {
        this.taxa = this.valorTotal.multiply(flag.getDebitPercentage()).divide(CEM_PORCENTO);

        if(TipoPagamento.CRED.equals(this.tipoPagamento)) {
            switch (this.parcelas) {
                case 1 -> this.taxa = this.valorTotal.multiply(flag.getCredit1x()).divide(CEM_PORCENTO);
                case 2, 3, 4, 5, 6 -> this.taxa = this.valorTotal.multiply(flag.getCredit2x6x()).divide(CEM_PORCENTO);
                default -> this.taxa = this.valorTotal.multiply(flag.getCredit7x12x()).divide(CEM_PORCENTO);
            }
        }

        this.valorAReceber = this.valorTotal.subtract(this.taxa);
    }
}
