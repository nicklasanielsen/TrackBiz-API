package DTOs;

import entities.CourierRequest;
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
public class CourierRequestDTOTest {

    private static EntityManagerFactory emf;

    private static CourierRequest courierRequest;
    private static CourierRequestDTO courierRequestDTO;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        courierRequest = new CourierRequest("testName", "testUrl", "testMsg");

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(courierRequest);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterAll
    public static void tearDownClass() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("CourierRequest.deleteAll").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @BeforeEach
    public void setUp() {
        courierRequestDTO = new CourierRequestDTO(courierRequest);
    }

    @AfterEach
    public void tearDown() {
        courierRequestDTO = null;
    }

    @Test
    public void getId_Success() {
        // Act
        long actual = courierRequestDTO.getId();

        // Assert
        assertNotNull(actual);
    }

    @Test
    public void getName_Success() {
        // Arrange
        String expected = "testName";

        // Act
        String actual = courierRequestDTO.getName();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getUrl_Success() {
        // Arrange
        String expected = "testUrl";

        // Act
        String actual = courierRequestDTO.getUrl();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getMessage_Success() {
        // Arrange
        String expected = "testMsg";

        // Act
        String actual = courierRequestDTO.getMessage();

        // Assert
        assertEquals(expected, actual);
    }

}
