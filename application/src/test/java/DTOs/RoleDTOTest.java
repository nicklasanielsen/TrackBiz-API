package DTOs;

import entities.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Nicklas Nielsen
 */
public class RoleDTOTest {

    private static Role role;
    private static RoleDTO roleDTO;

    @BeforeAll
    public static void setUpClass() {
        role = new Role("testRole");
    }

    @BeforeEach
    public void setUp() {
        roleDTO = new RoleDTO(role);
    }

    @AfterEach
    public void tearDown() {
        roleDTO = null;
    }

    @Test
    public void getRoleName_Success() {
        // Arrange
        String expected = role.getRoleName();

        // Act
        String actual = roleDTO.getRoleName();

        // Assert
        assertEquals(expected, actual);
    }

}
