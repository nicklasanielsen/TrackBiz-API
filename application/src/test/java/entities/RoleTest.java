package entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Nicklas Nielsen
 */
public class RoleTest {

    private static Role role;
    private static List<User> users;

    @BeforeAll
    public static void setUpClass() {
        users = new ArrayList();
    }

    @BeforeEach
    public void setUp() {
        role = new Role("testRole");
    }

    @AfterEach
    public void tearDown() {
        users.clear();
    }

    @Test
    public void getRoleName_Success() {
        // Arrange
        String expected = "testRole";

        // Act
        String actual = role.getRoleName();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void setRoleName_Success() {
        // Arrange
        String expected = "newTestRole";

        // Act
        role.setRoleName(expected);
        String actual = role.getRoleName();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void setDefaultRole_Success_True() {
        // Arrange
        boolean status = true;

        // Act
        role.setDefaultRole(status);
        boolean actual = role.getDefaultRole();

        // Assert
        assertTrue(actual);
    }

    @Test
    public void setDefaultRole_Success_False() {
        // Arrange
        boolean status = false;

        // Act
        role.setDefaultRole(status);
        boolean actual = role.getDefaultRole();

        // Assert
        assertFalse(actual);
    }

    @Test
    public void getDefaultRole_Success_True() {
        // Arrange
        boolean status = true;

        // Act
        role.setDefaultRole(status);
        boolean actual = role.getDefaultRole();

        // Assert
        assertTrue(actual);
    }

    @Test
    public void getDefaultRole_Success_False() {
        // Act
        boolean actual = role.getDefaultRole();

        // Assert
        assertFalse(actual);
    }

    @Test
    public void getUserList_Success_Empty() {
        // Act
        List<User> actual = role.getUserList();

        // Assert
        assertTrue(actual.isEmpty());
    }

    @Test
    public void getUserList_Success_One_User() {
        // Arrange
        List<User> expected = users;
        expected.add(new User("testUser", "testFirstName", "testLastName", "password123!", Arrays.asList(role)));

        // Act
        role.addUser(expected.get(0));
        List<User> actual = role.getUserList();

        // Assert
        assertTrue(expected.containsAll(actual));
    }

    @Test
    public void getUserList_Success_Multiple_Users() {
        // Arrange
        List<User> expected = users;
        expected.add(new User("testUser", "testFirstName", "testLastName", "password123!", Arrays.asList(role)));
        expected.add(new User("testUser2", "testFirstName2", "testLastName2", "password123!", Arrays.asList(role)));

        // Act
        for (User user : expected) {
            role.addUser(user);
        }

        List<User> actual = role.getUserList();

        // Assert
        assertTrue(expected.containsAll(actual));
    }

    @Test
    public void addUser_Success() {
        // Arrange
        User user = new User("testUser", "testFirstName", "testLastName", "password123!", new ArrayList());

        // Act
        role.addUser(user);

        // Assert
        assertTrue(user.getRoles().contains(role));
    }

    @Test
    public void removeUser_Success() {
        // Arrange
        User user = new User("testUser", "testFirstName", "testLastName", "password123!", new ArrayList());

        // Act
        role.addUser(user);
        role.removeUser(user);

        // Assert
        assertFalse(user.getRoles().contains(role));
    }

}
