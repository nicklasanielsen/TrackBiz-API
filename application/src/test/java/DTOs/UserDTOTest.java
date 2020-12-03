package DTOs;

import entities.Role;
import entities.User;
import java.text.SimpleDateFormat;
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
public class UserDTOTest {

    private static Role role;
    private static User user;
    private static UserDTO userDTO;

    @BeforeAll
    public static void setUpClass() {
        role = new Role("testRole");
        user = new User("testUserName", "testFirstName", "testLastName", "password123", Arrays.asList(role));
    }

    @BeforeEach
    public void setUp() {
        userDTO = new UserDTO(user);
    }

    @AfterEach
    public void tearDown() {
        userDTO = null;
    }

    @Test
    public void getUserName_Success() {
        // Arrange
        String expected = user.getUserName();

        // Act
        String actual = userDTO.getUserName();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getFullName_Success() {
        // Arrange
        String expected = user.getFirstName() + " " + user.getLastName();

        // Act
        String actual = userDTO.getFullName();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getCreated_Success() {
        // Arrange
        String expected = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").format(user.getCreated());

        // Act
        String actual = userDTO.getCreated();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getRoleList_Success() {
        // Arrange
        List<RoleDTO> expected = new ArrayList();
        expected.add(new RoleDTO(role));

        // Act
        List<RoleDTO> actual = userDTO.getRoleList();

        // Assert
        assertTrue(expected.containsAll(actual));
    }

}
