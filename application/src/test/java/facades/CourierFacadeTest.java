package facades;

import DTOs.CourierDTO;
import entities.Courier;
import errorhandling.exceptions.UnsupportedCourierException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import utils.EMF_Creator;

/**
 *
 * @author Nicklas Nielsen
 */
public class CourierFacadeTest {

    private static EntityManagerFactory emf;
    private static CourierFacade facade;

    private static List<Courier> couriers;
    private static List<CourierDTO> courierDTOs;

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = CourierFacade.getCourierFacade(emf);

        couriers = new ArrayList();
        courierDTOs = new ArrayList();
    }

    @AfterAll
    public static void tearDownClass() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Courier.deleteAll").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        couriers.add(new Courier("PostNord"));
        couriers.add(new Courier("FedEx"));
        couriers.add(new Courier("GLS"));

        try {
            em.getTransaction().begin();

            for (Courier courier : couriers) {
                em.persist(courier);
                courierDTOs.add(new CourierDTO(courier));
            }

            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();

        couriers.clear();
        courierDTOs.clear();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Courier.deleteAll").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void getCourierEntities_Success() {
        // Arrange
        List<Courier> expceted = couriers;

        // Act
        List<Courier> actual = facade.getCourierEntities();

        // Assert
        assertTrue(expceted.containsAll(actual));
    }

    @Test
    public void getCouriers_Success() {
        // Arrange
        List<CourierDTO> expected = courierDTOs;

        // Act
        List<CourierDTO> actual = facade.getCouriers();

        // Assert
        assertTrue(expected.containsAll(actual));
    }

    @Test
    public void getCourierEntity_Success() throws UnsupportedCourierException {
        // Arrange
        Courier expected = couriers.get(0);
        String courierName = expected.getName();

        // Act
        Courier actual = facade.getCourierEntity(courierName);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getCourierEntity_Fail_UnsupportedCourierException() {
        // Arrange
        String courierName = "UnsupportedCourier";

        // Assert
        assertThrows(UnsupportedCourierException.class, () -> {
            // Act
            facade.getCourierEntity(courierName);
        });
    }

    @Test
    public void getCourier_Success() throws UnsupportedCourierException {
        // Arrange
        CourierDTO expected = courierDTOs.get(0);
        String courierName = expected.getName();

        // Act
        CourierDTO actual = facade.getCourier(courierName);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    public void getCourier_Fail_UnsupportedCourierException() {
        // Arrange
        String courierName = "UnsupportedCourier";

        // Assert
        assertThrows(UnsupportedCourierException.class, () -> {
            // Act
            facade.getCourier(courierName);
        });
    }

    @Test
    public void isSupported_True() {
        // Arrange
        String courierName = couriers.get(0).getName();

        // Act
        boolean actual = facade.isSupported(courierName);

        // Assert
        assertTrue(actual);
    }

    @Test
    public void isSupported_False() {
        // Arrange
        String courierName = "UnsupportedCourier";

        // Act
        boolean actual = facade.isSupported(courierName);

        // Assert
        assertFalse(actual);
    }

}
