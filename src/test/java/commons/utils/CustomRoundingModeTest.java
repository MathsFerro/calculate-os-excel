package commons.utils;

import org.junit.jupiter.api.Test;
import org.mfr.commons.utils.CustomRoundingMode;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CustomRoundingModeTest {
    @Test
    void shouldDecreaseScaleToUp() {
        BigDecimal expected = BigDecimal.valueOf(10.58);
        BigDecimal value = BigDecimal.valueOf(10.575266);

        BigDecimal response = CustomRoundingMode.decreaseScaleToTwoDecimalCases(value);

        assertEquals(2, response.scale());
        assertEquals(expected, response);
    }

    @Test
    void shouldDecreaseScaleToDown() {
        BigDecimal expected = BigDecimal.valueOf(10.57);
        BigDecimal value = BigDecimal.valueOf(10.574266);

        BigDecimal response = CustomRoundingMode.decreaseScaleToTwoDecimalCases(value);

        assertEquals(2, response.scale());
        assertEquals(expected, response);
    }
}
