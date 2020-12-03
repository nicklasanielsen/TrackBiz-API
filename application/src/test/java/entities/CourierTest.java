package entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Nicklas Nielsen
 */
public class CourierTest {

    private static Courier courier;

    @BeforeEach
    public void setUp() {
        courier = new Courier("testCourier");
    }

    @AfterEach
    public void tearDown() {
        courier = null;
    }

    @Test
    public void getName_Success() {
        // Arrange
        String expected = "testCourier";

        // Act
        String actual = courier.getName();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void setName_Success() {
        // Arrange
        String expected = "newTestCourier";

        // Act
        courier.setName(expected);
        String actual = courier.getName();

        // Assert
        assertEquals(expected, actual);
    }

}
