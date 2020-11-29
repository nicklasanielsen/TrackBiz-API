package DTOs;

import entities.Courier;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Nicklas Nielsen
 */
public class CourierDTOTest {

    private static Courier courier;
    private static CourierDTO courierDTO;

    @BeforeAll
    public static void setUpClass() {
        courier = new Courier("TestCourier");
    }

    @BeforeEach
    public void setUp() {
        courierDTO = new CourierDTO(courier);
    }

    @AfterEach
    public void tearDown() {
        courierDTO = null;
    }

    @Test
    public void getName_Success() {
        // Arrange
        String expected = courier.getName();

        // Act
        String actual = courierDTO.getName();

        // Assert
        assertEquals(expected, actual);
    }

}
