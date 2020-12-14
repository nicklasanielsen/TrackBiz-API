package facades;

import DTOs.CourierRequestDTO;
import entities.CourierRequest;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Nicklas Nielsen
 */
public class FeedbackFacadeTest {

    private static EntityManagerFactory emf;
    private static FeedbackFacade facade;

    private static CourierRequest courierRequest;
    private static CourierRequestDTO courierRequestDTO;
    private static List<CourierRequestDTO> courierRequestDTOs;

    @BeforeAll
    public static void setUpClass() {
        emf = utils.EMF_Creator.createEntityManagerFactoryForTest();
        facade = FeedbackFacade.getFeedbackFacade(emf);

        courierRequestDTOs = new ArrayList<>();
    }

    @AfterAll
    public static void tearDownClass() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("CourierRequest.deleteAll");
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        courierRequest = new CourierRequest("courierName", "courierURL", "msg");

        try {
            em.getTransaction().begin();
            em.persist(courierRequest);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        courierRequestDTO = new CourierRequestDTO(courierRequest);
        courierRequestDTOs.add(courierRequestDTO);
    }

    @AfterEach
    public void tearDown() {
        courierRequest = null;
        courierRequestDTO = null;
        courierRequestDTOs.clear();

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
    public void getRequests_Success() {
        // Arrange
        List<CourierRequestDTO> expected = courierRequestDTOs;

        // Act
        List<CourierRequestDTO> actual = facade.getRequests();

        // Assert
        System.out.println(expected.size());
        System.out.println(actual.size());
        assertTrue(expected.containsAll(actual));
    }

    @Test
    public void addRequest_Success() {
        // Arrange
        courierRequest = new CourierRequest("testCourierName", "testCourierURL", "testMSG");
        CourierRequestDTO expected = new CourierRequestDTO(courierRequest);

        // Act
        CourierRequestDTO actual = facade.addRequest(courierRequest.getCourierName(), courierRequest.getCourierURL(), courierRequest.getMsg());

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void deleteRequest_Success() {
        // Arrange
        String id = String.valueOf(courierRequest.getId());

        // Act
        boolean actual = facade.deleteRequest(id);

        // Assert
        assertTrue(actual);
    }

    @Test
    public void deleteRequest_Failed_Invalid_ID() {
        // Arrange
        String id = "0";

        // Act
        boolean actual = facade.deleteRequest(id);

        // Assert
        assertFalse(actual);
    }

}
