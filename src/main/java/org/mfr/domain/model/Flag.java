package org.mfr.domain.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Flag {
    private String name;
    private String code;
    private BigDecimal debitPercentage;
    private BigDecimal credit1x;
    private BigDecimal credit2x6x;
    private BigDecimal credit7x12x;
}
