package facades;

import DTOs.EventDTO;
import DTOs.ShipmentDTO;
import entities.Courier;
import errorhandling.exceptions.FetchException;
import errorhandling.exceptions.NoShipmentsFoundException;
import errorhandling.exceptions.UnsupportedCourierException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
public class TrackingFacadeTest {

    private static EntityManagerFactory emf;
    private static TrackingFacade facade;

    private static ExecutorService threadPool = Executors.newCachedThreadPool();

    private static List<ShipmentDTO> shipmentDTOs;
    private static List<String> courierDTOs = Arrays.asList("Bring", "PostNord");

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = TrackingFacade.getTrackingFacade(emf);
        shipmentDTOs = new ArrayList();

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();

            for (String courierName : courierDTOs) {
                em.persist(new Courier(courierName));
            }

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
            em.createNamedQuery("Courier.deleteAll").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @BeforeEach
    public void setUp() throws ParseException {
        ShipmentDTO shipmentDTO = new ShipmentDTO("Bring", "TESTPACKAGE-AT-PICKUPPOINT");
        shipmentDTO.setConsignee("Unknown");
        shipmentDTO.setConsignor("POSTEN NORGE AS");
        shipmentDTO.setDestinationCity("VINTERBRO");
        shipmentDTO.setDestinationCountry("Unknown");
        shipmentDTO.setOriginCity("Unknown");
        shipmentDTO.setOriginCountry("Unknown");
        shipmentDTO.setVolume("45.2");
        shipmentDTO.setWeight("16.5");
        shipmentDTO.setCurrentEvent(new EventDTO("Available for Pickup", "The shipment has arrived at pickup point.", "Norway", "LØTEN", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").parse("2010-10-01 08:30:25 CEST")));
        shipmentDTO.addEvent(new EventDTO("Available for Pickup", "The shipment has arrived at pickup point.", "Norway", "LØTEN", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").parse("2010-10-01 08:30:25 CEST")));
        shipmentDTO.addEvent(new EventDTO("In Transit", "The shipment has been handed in at terminal and forwarded.", "Norway", "OSLO", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").parse("2010-09-30 08:27:08 CEST")));

        shipmentDTOs.add(shipmentDTO);
    }

    @AfterEach
    public void tearDown() {
        shipmentDTOs.clear();
    }

    @Test
    public void trackShipments_Success() throws UnsupportedCourierException, FetchException, NoShipmentsFoundException {
        // Arrange
        List<ShipmentDTO> expected = shipmentDTOs;

        String courier = expected.get(0).getCourier();
        String trackingNumber = expected.get(0).getTrackingNumber();

        // Act
        List<ShipmentDTO> actual = facade.trackShipments(threadPool, courier, trackingNumber);

        // Assert
        assertTrue(expected.containsAll(actual));
    }

    @Test
    public void trackShipments_Fail() {
        // Arrange
        String trackingNumber = "TEST_NEWBIZ_TEST_FAIL";

        // Assert
        assertThrows(NoShipmentsFoundException.class, () -> {
            // Act
            facade.trackShipments(threadPool, "Any", trackingNumber);
        });
    }

}
