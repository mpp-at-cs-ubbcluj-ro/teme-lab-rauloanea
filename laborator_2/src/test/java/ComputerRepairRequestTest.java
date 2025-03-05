import org.example.model.ComputerRepairRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComputerRepairRequestTest {
    @Test
    @DisplayName("first test")
    public void firstTest() {
        ComputerRepairRequest cr = new ComputerRepairRequest();
        assertEquals("", cr.getOwnerName());
        assertEquals("", cr.getOwnerAddress());
    }

    @Test
    @DisplayName("second test")
    public void secondTest() {
        ComputerRepairRequest cr = new ComputerRepairRequest();
        cr.setOwnerName("John");
        cr.setOwnerAddress("123 Main St.");
        assertEquals("John", cr.getOwnerName(), "nu se seteaza corect numele");
        assertEquals("123 Main St.", cr.getOwnerAddress());
    }
}
