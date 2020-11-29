package DTOs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;

/**
 *
 * @author Nicklas Nielsen
 */
public class ShipmentDTOTest {

    private static ShipmentDTO shipmentDTO;
    private static List<EventDTO> eventDTOs;

    @BeforeAll
    public static void setUpClass() {
        eventDTOs = new ArrayList();
    }

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

        eventDTOs.add(new EventDTO("testStatus1", "testDescription1", "testCountry1", "testCity1", new Date()));
        eventDTOs.add(new EventDTO("testStatus2", "testDescription2", "testCountry2", "testCity2", new Date()));
        eventDTOs.add(new EventDTO("testStatus3", "testDescription3", "testCountry3", "testCity3", new Date()));

        shipmentDTO.setCurrentEvent(eventDTOs.get(0));

        eventDTOs.forEach(eventDTO -> {
            shipmentDTO.addEvent(eventDTO);
        });
    }

    @AfterEach
    public void tearDown() {
        shipmentDTO = null;
        eventDTOs.clear();
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
        // Arrange
        String expected = "newTestConsignor";

        // Act
        shipmentDTO.setConsignor(expected);
        String actual = shipmentDTO.getConsignor();

        // Assert
        assertEquals(expected, actual);
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
        // Arrange
        String expected = "newTestConsignee";

        // Act
        shipmentDTO.setConsignee(expected);
        String actual = shipmentDTO.getConsignee();

        // Assert
        assertEquals(expected, actual);
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
        // Arrange
        String expected = "newTestOriginCountry";

        // Act
        shipmentDTO.setOriginCountry(expected);
        String actual = shipmentDTO.getOriginCountry();

        // Assert
        assertEquals(expected, actual);
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
        // Arrange
        String expected = "newTestOriginCity";

        // Act
        shipmentDTO.setOriginCity(expected);
        String actual = shipmentDTO.getOriginCity();

        // Assert
        assertEquals(expected, actual);
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
        // Arrange
        String expected = "newTestDestinationCountry";

        // Act
        shipmentDTO.setDestinationCountry(expected);
        String actual = shipmentDTO.getDestinationCountry();

        // Assert
        assertEquals(expected, actual);
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
        // Arrange
        String expected = "newTestDestinationCity";

        // Act
        shipmentDTO.setDestinationCity(expected);
        String actual = shipmentDTO.getDestinationCity();

        // Assert
        assertEquals(expected, actual);
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
        // Arrange
        String expected = "newTestVolume";

        // Act
        shipmentDTO.setVolume(expected);
        String actual = shipmentDTO.getVolume();

        // Assert
        assertEquals(expected, actual);
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
        // Arrange
        String expected = "newTestWeight";

        // Act
        shipmentDTO.setWeight(expected);
        String actual = shipmentDTO.getWeight();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getCurrentEvent_Success() {
        // Arrange
        EventDTO expected = eventDTOs.get(0);

        // Act
        EventDTO actual = shipmentDTO.getCurrentEvent();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void setCurrentEvent_Success() {
        // Arrange
        EventDTO expected = new EventDTO("newTestStatus", "newTestDescription", "newTestCountry", "newTestCity", new Date(0));

        // Act
        shipmentDTO.setCurrentEvent(expected);
        EventDTO actual = shipmentDTO.getCurrentEvent();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void addEvent_Success() {
        // Arrange
        EventDTO eventDTO = new EventDTO("newTestStatus", "newTestDescription", "newTestCountry", "newTestCity", new Date(0));
        eventDTOs.add(eventDTO);
        List<EventDTO> expected = eventDTOs;

        // Act
        shipmentDTO.addEvent(eventDTO);
        List<EventDTO> actual = shipmentDTO.getEvents();

        // Assert
        assertTrue(expected.containsAll(actual));
    }

    @Test
    public void getEvents_Success() {
        // Arrange
        List<EventDTO> expected = eventDTOs;

        // Act
        List<EventDTO> actual = shipmentDTO.getEvents();

        // Assert
        assertTrue(expected.containsAll(actual));
    }

}
