package entities;

import java.util.ArrayList;
import java.util.List;
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

    @Test
    public void getShipments_Success_Empty() {
        // Act
        List<Shipment> actual = courier.getShipments();

        // Assert
        assertTrue(actual.isEmpty());
    }

    @Test
    public void getShipments_Success_One_Shipment() {
        // Arrange
        List<Shipment> expected = new ArrayList<>();
        expected.add(new Shipment(courier, "123"));

        // Act
        courier.addShipment(expected.get(0));
        List<Shipment> actual = courier.getShipments();

        // Assert
        assertTrue(expected.containsAll(actual));
    }

    @Test
    public void getShipments_Success_Multiple_Shipments() {
        // Arrange
        List<Shipment> expected = new ArrayList<>();
        expected.add(new Shipment(courier, "123"));
        expected.add(new Shipment(courier, "1233"));

        // Act
        courier.addShipment(expected.get(0));
        courier.addShipment(expected.get(1));
        List<Shipment> actual = courier.getShipments();

        // Assert
        assertTrue(expected.containsAll(actual));
    }

    @Test
    public void setShipments_Success() {
        // Arrange
        List<Shipment> expected = new ArrayList<>();
        expected.add(new Shipment(courier, "123"));
        expected.add(new Shipment(courier, "1233"));

        // Act
        courier.setShipments(expected);
        List<Shipment> actual = courier.getShipments();

        // Assert
        assertTrue(expected.containsAll(actual));
    }

    @Test
    public void addShipment_Success() {
        // Arrange
        Shipment expected = new Shipment(courier, "123");

        // Act
        courier.addShipment(expected);
        List<Shipment> actual = courier.getShipments();

        // Assert
        assertTrue(actual.size() == 1);
        assertTrue(actual.contains(expected));
    }

    @Test
    public void addShipment_Success_Already_Added() {
        // Arrange
        Shipment expected = new Shipment(courier, "123");

        // Act
        courier.addShipment(expected);
        courier.addShipment(expected);
        List<Shipment> actual = courier.getShipments();

        // Assert
        assertTrue(actual.size() == 1);
        assertTrue(actual.contains(expected));
    }

    @Test
    public void removeShipment_Success() {
        // Arrange
        Shipment expected = new Shipment(courier, "123");

        // Act
        courier.addShipment(expected);
        courier.removeShipment(expected);
        List<Shipment> actual = courier.getShipments();

        // Assert
        assertTrue(actual.isEmpty());
    }

    @Test
    public void removeShipment_Success_Not_Added() {
        // Arrange
        Shipment expected = new Shipment(courier, "123");

        // Act
        courier.removeShipment(expected);
        List<Shipment> actual = courier.getShipments();

        // Assert
        assertTrue(actual.isEmpty());
    }

}
