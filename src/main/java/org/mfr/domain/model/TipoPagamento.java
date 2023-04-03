package org.mfr.domain.model;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TipoPagamento {
    CRED("C"), DEB("D");

    private String tipo;

    TipoPagamento(String tipo) {
        this.tipo = tipo;
    }

    public static TipoPagamento getTipoPagamento(String tipo) {
        return Arrays.stream(TipoPagamento.values())
                .filter(tipoPagamento -> tipoPagamento.getTipo().equals(tipo))
                .findFirst()
                .orElse(null);
    }
}
