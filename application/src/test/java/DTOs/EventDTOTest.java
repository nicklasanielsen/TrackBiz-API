package DTOs;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author Nicklas Nielsen
 */
public class EventDTOTest {

    private static EventDTO eventDTO;
    private static Date timeStamp;

    @BeforeAll
    public static void setUpClass() {
        timeStamp = new Date();
    }

    @AfterAll
    public static void tearDownClass() {

    }

    @BeforeEach
    public void setUp() {
        String status, description, country, city;

        status = "testStatus";
        description = "testDescription";
        country = "testCountry";
        city = "testCity";

        eventDTO = new EventDTO(status, description, country, city, timeStamp);
    }

    @AfterEach
    public void tearDown() {
        eventDTO = null;
    }

    @Test
    public void getStatus_Success() {
        // Arrange
        String expected = "testStatus";

        // Act
        String actual = eventDTO.getStatus();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getDescription_Success() {
        // Arrange
        String expected = "testDescription";

        // Act
        String actual = eventDTO.getDescription();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getCountry_Success() {
        // Arrange
        String expected = "testCountry";

        // Act
        String actual = eventDTO.getCountry();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getCity_Success() {
        // Arrange
        String expected = "testCity";

        // Act
        String actual = eventDTO.getCity();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getTimeStamp_Success() {
        // Arrange
        String expected = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").format(timeStamp);

        // Act
        String actual = eventDTO.getTimeStamp();

        // Assert
        assertEquals(expected, actual);
    }

}
