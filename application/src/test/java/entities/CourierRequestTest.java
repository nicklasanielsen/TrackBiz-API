package entities;

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
public class CourierRequestTest {

    private static EntityManagerFactory emf;
    private static CourierRequest courierRequest;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
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
        courierRequest = new CourierRequest("testCourier", "testURL", "testMessage");

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(courierRequest);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        courierRequest = null;

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("CourierRequest.deleteAll").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void getId_Success() {
        // Act
        long actual = courierRequest.getId();

        // Assert
        assertNotNull(actual);
    }

    @Test
    public void getCourierName_Success() {
        // Arrange
        String expected = "testCourier";

        // Act
        String actual = courierRequest.getCourierName();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void setCourierName_Success() {
        // Arrange
        String expected = "newTestCourier";

        // Act
        courierRequest.setCourierName(expected);
        String actual = courierRequest.getCourierName();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getCourierURL_Success() {
        // Arrange
        String expected = "testURL";

        // Act
        String actual = courierRequest.getCourierURL();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void setCourierURL_Success() {
        // Arrange
        String expected = "mewTestURL";

        // Act
        courierRequest.setCourierURL(expected);
        String actual = courierRequest.getCourierURL();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getMsg_Success() {
        // Arrange
        String expected = "testMessage";

        // Act
        String actual = courierRequest.getMsg();

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void setMsg_Success() {
        // Arrange
        String expected = "newTestMessage";

        // Act
        courierRequest.setMsg(expected);
        String actual = courierRequest.getMsg();

        // Assert
        assertEquals(expected, actual);
    }

}
