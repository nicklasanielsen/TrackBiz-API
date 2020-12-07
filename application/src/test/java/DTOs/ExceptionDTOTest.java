package DTOs;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Nicklas Nielsen
 */
public class ExceptionDTOTest {

    private static ExceptionDTO exceptionDTO;

    @BeforeEach
    public void setUp() {
        exceptionDTO = new ExceptionDTO(200, "testMessage");
    }

    @AfterEach
    public void tearDown() {
        exceptionDTO = null;
    }

    @Test
    public void getCode_Success() {
        // Arrange
        int expected = 200;

        // Act
        int actual = exceptionDTO.getCode();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getMessage_Success() {
        // Arrange
        String expected = "testMessage";

        // Act
        String actual = exceptionDTO.getMessage();

        // Assert
        assertEquals(expected, actual);
    }

}
