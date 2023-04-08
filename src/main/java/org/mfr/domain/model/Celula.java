package org.mfr.domain.model;

import lombok.*;
import org.mfr.commons.utils.NormalizeStringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Celula {
    private static final BigDecimal CEM_PORCENTO = BigDecimal.valueOf(100);

    private LocalDate data;
    private LocalDate dataPrimeiroPagamento;
    private LocalDate dataUltimoPagamento;
    private LocalDate dataProximoPagamento;
    private String numeroOs;
    private BigDecimal valorTotal;
    private TipoPagamento tipoPagamento;
    private Integer parcelas;
    private String bandeira;
    private BigDecimal taxa;
    private BigDecimal valorAReceber;
    private BigDecimal valorParcelado;

    public void calculateNextDatesAndValorAReceber(Flag flag) {
        if(TipoPagamento.DEB.equals(this.tipoPagamento)) {
            this.dataProximoPagamento = this.data.plusDays(1);
            this.dataPrimeiroPagamento = this.dataProximoPagamento;
            this.dataProximoPagamento = this.normalizePaymentsDaysOfWeek(this.dataProximoPagamento);
            this.dataUltimoPagamento = this.dataProximoPagamento;

            this.taxa = this.valorTotal.multiply(flag.getDebitPercentage()).divide(CEM_PORCENTO);
            this.valorAReceber = this.valorTotal.subtract(this.taxa);
            this.valorParcelado = this.valorAReceber;
        }

        if(TipoPagamento.CRED.equals(this.tipoPagamento)) {
            this.dataProximoPagamento = this.data.plusDays(30);
            this.dataPrimeiroPagamento = this.dataProximoPagamento;
            this.dataProximoPagamento = this.normalizePaymentsDaysOfWeek(this.dataProximoPagamento);
            this.dataUltimoPagamento = this.parcelas>1 ? this.dataProximoPagamento.plusMonths(this.parcelas-1) : this.dataProximoPagamento;

            switch (this.parcelas) {
                case 1 -> this.taxa = this.valorTotal.multiply(flag.getCredit1x()).divide(CEM_PORCENTO);
                case 2, 3, 4, 5, 6 -> this.taxa = this.valorTotal.multiply(flag.getCredit2x6x()).divide(CEM_PORCENTO);
                default -> this.taxa = this.valorTotal.multiply(flag.getCredit7x12x()).divide(CEM_PORCENTO);
            }

            this.valorAReceber = this.valorTotal.subtract(this.taxa);
            this.valorParcelado = this.valorAReceber.divide(BigDecimal.valueOf(this.parcelas), RoundingMode.HALF_UP);
        }
    }

    public Celula markNextPaymentDate() {
        if(TipoPagamento.DEB.equals(this.tipoPagamento))
            return this;

        this.dataUltimoPagamento = this.dataUltimoPagamento.withDayOfMonth(this.dataProximoPagamento.getDayOfMonth());
        this.dataUltimoPagamento = this.normalizePaymentsDaysOfWeek(this.dataUltimoPagamento);

        if(this.dataProximoPagamento.getMonthValue() == this.dataUltimoPagamento.getMonthValue() && this.dataProximoPagamento.getYear() == this.dataUltimoPagamento.getYear())
            return this;

        this.dataProximoPagamento = this.dataProximoPagamento.withDayOfMonth(this.dataPrimeiroPagamento.getDayOfMonth());
        this.dataProximoPagamento = this.dataProximoPagamento.plusMonths(1);
        this.dataProximoPagamento = this.normalizePaymentsDaysOfWeek(this.dataProximoPagamento);

        return this;
    }

    private LocalDate normalizePaymentsDaysOfWeek(LocalDate date) {
        String dayOfWeek = NormalizeStringUtils.normalize(date.getDayOfWeek());

        if (dayOfWeek.equalsIgnoreCase("Sabado"))
            return date.plusDays(2);

        if (dayOfWeek.equalsIgnoreCase("Domingo"))
            return date.plusDays(1);

        return date;
    }
}
