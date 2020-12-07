package entities;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Nicklas Nielsen
 */
public class ShipmentTest {

    private static Courier courier;
    private static Shipment shipment;

    @BeforeAll
    public static void setUpClass() {
        courier = new Courier("testCourier");
    }

    @BeforeEach
    public void setUp() {
        shipment = new Shipment(courier, "test123");
    }

    @AfterEach
    public void tearDown() {
        shipment = null;
    }

    @Test
    public void getCourier_Success() {
        // Arrange
        Courier expected = courier;

        // Act
        Courier actual = shipment.getCourier();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void setCourier_Success() {
        // Arrange
        Courier expected = new Courier("tester");

        // Act
        shipment.setCourier(expected);
        Courier actual = shipment.getCourier();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getTrackingNumber_Success() {
        // Arrange
        String expected = "test123";

        // Act
        String actual = shipment.getTrackingNumber();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void setTrackingNumber_Success() {
        // Arrange
        String expected = "tester";

        // Act
        shipment.setTrackingNumber(expected);
        String actual = shipment.getTrackingNumber();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getUsers_Success_Empty() {
        // Act
        List<User> actual = shipment.getUsers();

        // Assert
        assertTrue(actual.isEmpty());
    }

    @Test
    public void getUsers_Success_One_User() {
        // Arrange
        User user = new User("tester", "tester", "tester", "tester", new ArrayList<>());

        // Act
        shipment.addUser(user);
        List<User> actual = shipment.getUsers();

        // Assert
        assertTrue(actual.size() == 1);
        assertTrue(actual.contains(user));
    }

    @Test
    public void getUsers_Success_Multiple_Users() {
        // Arrange
        List<User> expected = new ArrayList();
        expected.add(new User("tester", "tester", "tester", "tester", new ArrayList<>()));
        expected.add(new User("tester2", "tester2", "tester2", "tester2", new ArrayList<>()));

        // Act
        shipment.setUsers(expected);
        List<User> actual = shipment.getUsers();

        // Assert
        assertTrue(expected.containsAll(actual));
    }

    @Test
    public void addUser_Success() {
        // Arrange
        User user = new User("tester", "tester", "tester", "tester", new ArrayList<>());

        // Act
        shipment.addUser(user);
        List<User> actual = shipment.getUsers();

        // Assert
        assertTrue(actual.size() == 1);
        assertTrue(actual.contains(user));
    }

    @Test
    public void addUser_Success_Already_Added() {
        // Arrange
        User user = new User("tester", "tester", "tester", "tester", new ArrayList<>());

        // Act
        shipment.addUser(user);
        shipment.addUser(user);
        List<User> actual = shipment.getUsers();

        // Assert
        assertTrue(actual.size() == 1);
        assertTrue(actual.contains(user));
    }

    @Test
    public void removeUser_Success() {
        // Arrange
        User user = new User("tester", "tester", "tester", "tester", new ArrayList<>());

        // Act
        shipment.addUser(user);
        shipment.removeUser(user);
        List<User> actual = shipment.getUsers();

        // Assert
        assertTrue(actual.isEmpty());
    }

    @Test
    public void removeUser_Success_Not_Added() {
        // Arrange
        User user = new User("tester", "tester", "tester", "tester", new ArrayList<>());

        // Act
        shipment.removeUser(user);
        List<User> actual = shipment.getUsers();

        // Assert
        assertTrue(actual.isEmpty());
    }

}
