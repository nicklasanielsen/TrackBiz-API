package entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Nicklas Nielsen
 */
public class UserTest {

    private static User user;
    private static List<Role> roles;

    @BeforeAll
    public static void setUpClass() {
        roles = new ArrayList();
    }

    @BeforeEach
    public void setUp() {
        roles.add(new Role("testRole"));
        user = new User("testUserName", "testFirstName", "testLastName", "password123", roles);
    }

    @AfterEach
    public void tearDown() {
        roles.clear();
        user = null;
    }

    @Test
    public void getUserName_Success() {
        // Arrange
        String expected = "testUserName";

        // Act
        String actual = user.getUserName();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void setUserName_Success() {
        // Arrange
        String expected = "newTestUserName";

        // Act
        user.setUserName(expected);
        String actual = user.getUserName();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getFirstName_Success() {
        // Arrange
        String expected = "testFirstName";

        // Act
        String actual = user.getFirstName();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void setFirstName_Success() {
        // Arrange
        String expected = "newTestFirstName";

        // Act
        user.setFirstName(expected);
        String actual = user.getFirstName();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getLastName_Success() {
        // Arrange
        String expected = "testLastName";

        // Act
        String actual = user.getLastName();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void setLastName_Success() {
        // Arrange
        String expected = "newTestLastName";

        // Act
        user.setLastName(expected);
        String actual = user.getLastName();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getCreated_Success() {
        // Act
        Date actual = user.getCreated();

        // Assert
        assertNotNull(actual);
    }

    @Test
    public void verifyPassword_Success() {
        // Arrange
        String password = "password123";

        // Act
        boolean verified = user.verifyPassword(password);

        // Assert
        assertTrue(verified);
    }

    @Test
    public void setPassword_Success() {
        // Arrange
        String password = "newPassword123";

        // Act
        user.setPassword(password);
        boolean verified = user.verifyPassword(password);

        // Assert
        assertTrue(verified);
    }

    @Test
    public void getRoles_Success_Empty() {
        // Act
        user.removeRole(roles.get(0));
        List<Role> actualRoles = user.getRoles();

        // Assert
        assertTrue(actualRoles.isEmpty());
    }

    @Test
    public void getRoles_Success_One_Role() {
        // Arrange
        List<Role> expectedRoles = roles;
        expectedRoles.add(new Role("testRole"));

        // Act
        for (Role role : expectedRoles) {
            user.addRole(role);
        }
        List<Role> actualRoles = user.getRoles();

        // Assert
        assertTrue(expectedRoles.containsAll(actualRoles));
    }

    @Test
    public void getRoles_Success_Multiple_Roles() {
        // Arrange
        List<Role> expectedRoles = roles;
        expectedRoles.add(new Role("testRole"));
        expectedRoles.add(new Role("testRole2"));

        // Act
        for (Role role : expectedRoles) {
            user.addRole(role);
        }
        List<Role> actualRoles = user.getRoles();

        // Assert
        assertTrue(expectedRoles.containsAll(actualRoles));
    }

    @Test
    public void addRole_Success() {
        // Arrange
        Role expectedRole = new Role("testRole");

        // Act
        user.addRole(expectedRole);
        List<Role> actualRoles = user.getRoles();

        // Assert
        assertTrue(actualRoles.contains(expectedRole) && actualRoles.size() == 1);
    }

    @Test
    public void removeRole_Success() {
        // Arrange
        Role expectedRole = new Role("testRole");

        // Act
        user.addRole(expectedRole);
        user.removeRole(expectedRole);
        List<Role> actualRoles = user.getRoles();

        // Assert
        assertTrue(actualRoles.isEmpty());
    }

    @Test
    public void getShipments_Success_Empty() {
        // Act
        List<Shipment> actual = user.getShipments();

        // Assert
        assertTrue(actual.isEmpty());
    }

    @Test
    public void getShipments_Success_One_Shipment() {
        // Arrange
        Shipment shipment = new Shipment(new Courier("tester"), "123");

        // Act
        user.addShipment(shipment);
        List<Shipment> actual = user.getShipments();

        // Assert
        assertTrue(actual.size() == 1);
        assertTrue(actual.contains(shipment));
    }

    @Test
    public void getShipments_Success_Multiple_Shipments() {
        // Arrange
        List<Shipment> expected = new ArrayList();
        expected.add(new Shipment(new Courier("tester"), "123"));
        expected.add(new Shipment(new Courier("tester2"), "1234"));

        // Act
        user.setShipments(expected);
        List<Shipment> actual = user.getShipments();

        // Assert
        assertTrue(expected.containsAll(actual));
    }

    @Test
    public void setShipments_Success() {
        // Arrange
        List<Shipment> expected = new ArrayList();
        expected.add(new Shipment(new Courier("tester"), "123"));
        expected.add(new Shipment(new Courier("tester2"), "1234"));

        // Act
        user.setShipments(expected);
        List<Shipment> actual = user.getShipments();

        // Assert
        assertTrue(expected.containsAll(actual));
    }

    @Test
    public void addShipment_Success() {
        // Arrange
        Shipment shipment = new Shipment(new Courier("tester"), "123");

        // Act
        user.addShipment(shipment);
        List<Shipment> actual = user.getShipments();

        // Assert
        assertTrue(actual.size() == 1);
        assertTrue(actual.contains(shipment));
    }

    @Test
    public void addShipment_Success_Already_Added() {
        // Arrange
        Shipment shipment = new Shipment(new Courier("tester"), "123");

        // Act
        user.addShipment(shipment);
        user.addShipment(shipment);
        List<Shipment> actual = user.getShipments();

        // Assert
        assertTrue(actual.size() == 1);
        assertTrue(actual.contains(shipment));
    }

    @Test
    public void removeShipment_Success() {
        // Arrange
        Shipment shipment = new Shipment(new Courier("tester"), "123");

        // Act
        user.addShipment(shipment);
        user.removeShipment(shipment);
        List<Shipment> actual = user.getShipments();

        // Assert
        assertTrue(actual.isEmpty());
    }

    @Test
    public void removeShipment_Success_Not_Added() {
        // Arrange
        Shipment shipment = new Shipment(new Courier("tester"), "123");

        // Act
        user.removeShipment(shipment);
        List<Shipment> actual = user.getShipments();

        // Assert
        assertTrue(actual.isEmpty());
    }

}
