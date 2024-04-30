package test;

import com.reverso.entites.Client;
import com.reverso.exception.ReException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    @Test
    public void testSetChiffreAffaireWithValidValue() {
        Client client = new Client();
        assertDoesNotThrow(() -> client.setChiffreAffaire(5000.0));
    }

    @Test
    public void testSetChiffreAffaireWithInvalidValue() {
        Client client = new Client();
        assertThrows(ReException.class, () -> client.setChiffreAffaire(100.0));
    }

    @Test
    public void testSetNbeEmployeWithValidValue() {
        Client client = new Client();
        assertDoesNotThrow(() -> client.setNbeEmploye(20));
    }

    @Test
    public void testSetNbeEmployeWithInvalidValue() {
        Client client = new Client();
        assertThrows(ReException.class, () -> client.setNbeEmploye(0));
    }

}