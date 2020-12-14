package facades;

import DTOs.UserDTO;
import entities.Role;
import entities.User;
import errorhandling.exceptions.DatabaseException;
import errorhandling.exceptions.UserCreationException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import errorhandling.exceptions.AuthenticationException;
import utils.EMF_Creator;

/**
 *
 * @author Nicklas Nielsen
 */
public class UserFacadeTest {

    private static EntityManagerFactory emf;
    private static UserFacade facade;
    private static RoleFacade roleFacade;
    private static User user;
    private static UserDTO userDTO;
    private static List<Role> roles;

    @BeforeAll
    public static void setupClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = UserFacade.getUserFacade(emf);
        roleFacade = RoleFacade.getRoleFacade(emf);

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(new Role("User"));
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        roles = roleFacade.getDefaultRoles();
    }

    @AfterAll
    public static void tearDownClass() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @BeforeEach
    public void setup() {
        EntityManager em = emf.createEntityManager();

        user = new User("user", "TestFirstName", "TestLastName", "STRONG_PASSWORD", roles);

        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();

            userDTO = new UserDTO(user);
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();

        user = null;
        userDTO = null;

        try {
            em.getTransaction().begin();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void createUser_Success() throws DatabaseException, UserCreationException {
        // Arrange
        user.setUserName("testUser");
        UserDTO expected = new UserDTO(user);

        // Act
        UserDTO actual = facade.createUser(user.getUserName(),
                user.getFirstName(), user.getLastName(), "STRONG_PASSWORD");

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void createUser_Failed_UserName_Already_In_Use() {
        assertThrows(UserCreationException.class, () -> {
            facade.createUser(user.getUserName(),
                    user.getFirstName(), user.getLastName(), "STRONG_PASSWORD");
        });
    }

    @Test
    public void login_Success() throws AuthenticationException {
        // Arrange
        UserDTO excepted = userDTO;

        // Act
        UserDTO actual = facade.login(user.getUserName(), "STRONG_PASSWORD");

        // Assert
        assertEquals(excepted, actual);
    }

    @Test
    public void login_Failed_Invalid_Username() {
        user.setUserName("testUser");

        assertThrows(AuthenticationException.class, () -> {
            facade.login(user.getUserName(), "STRONG_PASSWORD");
        });
    }

    @Test
    public void login_Failed_Incorrect_Password() {
        assertThrows(AuthenticationException.class, () -> {
            facade.login(user.getUserName(), "INCORRECT_PASSWORD");
        });
    }

    @Test
    public void getUserByUserName_Success() {
        // Arrange
        User expected = user;

        // Act
        User actual = facade.getUserByUserName(expected.getUserName());

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void deleteUserAccount_Success() {
        // Act
        boolean actual = facade.deleteUserAccount(user.getUserName());

        // Assert
        assertTrue(actual);
    }

    @Test
    public void editUserAccount_Success_UserName_Updated() throws UserCreationException {
        // Arrange
        String oldValue = user.getUserName();
        user.setUserName("newUserName");
        UserDTO expected = new UserDTO(user);

        // Act
        UserDTO actual = facade.editUserAccount(oldValue, user.getUserName(), "", "", "");

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void editUserAccount_Success_FirstName_Updated() throws UserCreationException {
        // Arrange
        user.setFirstName("CoolTestName");
        UserDTO expected = new UserDTO(user);

        // Act
        UserDTO actual = facade.editUserAccount(user.getUserName(), user.getUserName(), user.getFirstName(), "", "");

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void editUserAccount_Success_LastName_Updated() throws UserCreationException {
        //
        user.setLastName("CoolLastName");
        UserDTO expected = new UserDTO(user);

        // Act
        UserDTO actual = facade.editUserAccount(user.getUserName(), user.getUserName(), "", user.getLastName(), "");

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void editUserAccount_Success_Password_Updated() throws UserCreationException {
        // Arrange
        user.setPassword("NewPassword");
        UserDTO expected = new UserDTO(user);

        // Act
        UserDTO actual = facade.editUserAccount(user.getUserName(), user.getUserName(), "", "", "NewPassword");

        // Assert
        assertEquals(expected, actual);
    }

}
