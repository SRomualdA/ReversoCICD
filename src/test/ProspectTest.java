package test;

import com.reverso.entites.Prospect;
import com.reverso.exception.ReException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProspectTest {

    @Test
    public void testSetDateProspect_ValidDate() {
        Prospect prospect = new Prospect();

        LocalDate newDate = LocalDate.of(2024, 2, 28);
        assertDoesNotThrow(() -> prospect.setDateProspect(newDate));
    }

    @Test
    public void testSetDateProspect_NullDate() {
        Prospect prospect = new Prospect();

        assertThrows(ReException.class, () -> prospect.setDateProspect(null));
    }

    @Test
    public void testSetProspectInteress_ValidValue() {
        Prospect prospect = new Prospect();

        assertDoesNotThrow(() -> prospect.setProspectInteress(0));
    }

    @Test
    public void testSetProspectInteress_NullValue() {
        Prospect prospect = new Prospect();

        assertThrows(ReException.class, () -> prospect.setProspectInteress(null));
    }

    @Test
    public void testSetProspectInteress_InvalidValue() {
        Prospect prospect = new Prospect();

        assertThrows(ReException.class, () -> prospect.setProspectInteress(2));
    }
}