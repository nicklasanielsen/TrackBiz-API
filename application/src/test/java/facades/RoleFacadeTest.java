package facades;

import entities.Role;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author Nicklas Nielsen
 */
public class RoleFacadeTest {

    private static EntityManagerFactory emf;
    private static RoleFacade facade;

    private static Role role;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = RoleFacade.getRoleFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @BeforeEach
    public void setUp() {
        role = new Role("tester");

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(role);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();

        role = null;

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void getDefaultRoles_Success() {
        // Arrange
        List<Role> expected = Arrays.asList(role);

        // Act
        List<Role> actual = facade.getDefaultRoles();

        // Assert
        assertTrue(expected.containsAll(actual));
    }

}
