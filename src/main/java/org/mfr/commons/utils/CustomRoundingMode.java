package org.mfr.commons.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomRoundingMode {
    public static BigDecimal decreaseScaleToTwoDecimalCases(BigDecimal value) {
        int scale = value.scale();
        if(scale<=2)
            return value;

        String valueStr = String.valueOf(value);
        int lastDigit = Integer.parseInt(valueStr.substring(valueStr.length() - 1));
        value = value.setScale(scale-1, lastDigit>=5 ? RoundingMode.UP : RoundingMode.DOWN);
        return decreaseScaleToTwoDecimalCases(value);
    }
}
