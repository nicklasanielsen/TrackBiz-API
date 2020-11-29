package DTOs;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Nicklas Nielsen
 */
public class ShipmentDTOTest {

    private static ShipmentDTO shipmentDTO;
    private static List<EventDTO> eventDTOs;

    @BeforeEach
    public void setUp() {
        String courier, trackingNumber, consignor, consignee, originCountry,
                originCity, destinationCountry, destinationCity, volume, weight;

        courier = "testCourier";
        trackingNumber = "testTrackingNumber";
        consignor = "testConsignor";
        consignee = "testConsignee";
        originCountry = "testOriginCountry";
        originCity = "testOriginCity";
        destinationCountry = "testDestinationCountry";
        destinationCity = "testDestinationCity";
        volume = "testVolume";
        weight = "testWeight";

        shipmentDTO = new ShipmentDTO(courier, trackingNumber);
        shipmentDTO.setConsignor(consignor);
        shipmentDTO.setConsignee(consignee);
        shipmentDTO.setOriginCountry(originCountry);
        shipmentDTO.setOriginCity(originCity);
        shipmentDTO.setDestinationCountry(destinationCountry);
        shipmentDTO.setDestinationCity(destinationCity);
        shipmentDTO.setVolume(volume);
        shipmentDTO.setWeight(weight);

    }

    @AfterEach
    public void tearDown() {
        shipmentDTO = null;
    }

    @Test
    public void getCourier_Success() {
        // Arrange
        String expected = "testCourier";

        // Act
        String actual = shipmentDTO.getCourier();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getTrackingNumber_Success() {
        // Arrange
        String expected = "testTrackingNumber";

        // Act
        String actual = shipmentDTO.getTrackingNumber();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getConsignor_Success() {
        // Arrange
        String expected = "testConsignor";

        // Act
        String actual = shipmentDTO.getConsignor();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void setConsignor_Success() {
        fail();
    }

    @Test
    public void getConsignee_Success() {
        // Arrange
        String expected = "testConsignee";

        // Act
        String actual = shipmentDTO.getConsignee();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void setConsignee_Success() {
        fail();
    }

    @Test
    public void getOriginCountry_Success() {
        // Arrange
        String expected = "testOriginCountry";

        // Act
        String actual = shipmentDTO.getOriginCountry();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void setOriginCountry_Success() {
        fail();
    }

    @Test
    public void getOriginCity_Success() {
        // Arrange
        String expected = "testOriginCity";

        // Act
        String actual = shipmentDTO.getOriginCity();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void setOriginCity_Success() {
        fail();
    }

    @Test
    public void getDestinationCountry_Success() {
        // Arrange
        String expected = "testDestinationCountry";

        // Act
        String actual = shipmentDTO.getDestinationCountry();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void setDestinationCountry_Success() {
        fail();
    }

    @Test
    public void getDestinationCity_Success() {
        // Arrange
        String expected = "testDestinationCity";

        // Act
        String actual = shipmentDTO.getDestinationCity();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void setDestinationCity_Success() {
        fail();
    }

    @Test
    public void getVolume_Success() {
        // Arrange
        String expected = "testVolume";

        // Act
        String actual = shipmentDTO.getVolume();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void setVolume_Success() {
        fail();
    }

    @Test
    public void getWeight_Success() {
        // Arrange
        String expected = "testWeight";

        // Act
        String actual = shipmentDTO.getWeight();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void setWeight_Success() {
        fail();
    }

    @Test
    public void getCurrentEvent_Success() {
        fail();
    }

    @Test
    public void setCurrentEvent_Success() {
        fail();
    }

    @Test
    public void addEvent_Success() {
        fail();
    }

    @Test
    public void getEvents_Success() {
        fail();
    }

}
