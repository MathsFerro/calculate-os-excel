package domain.model;

import org.junit.jupiter.api.Test;
import org.mfr.domain.model.HeaderDaysNameCellGroup;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class HeaderDaysNameCellGroupTest {
    @Test
    void shouldReturnKeyValueGivenDayName() {
        Integer expected = 0;
        Integer response = HeaderDaysNameCellGroup.getIdValueByDayName("Domingo");
        assertEquals(expected, response);
    }

    @Test
    void notShouldReturnKeyValueGivenDayName() {
        Integer expected = 0;
        Integer response = HeaderDaysNameCellGroup.getIdValueByDayName("Segunda-feira");
        assertNotEquals(expected, response);
    }
}
