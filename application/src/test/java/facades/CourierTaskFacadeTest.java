package facades;

import entities.Courier;
import java.util.concurrent.Callable;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Nicklas Nielsen
 */
public class CourierTaskFacadeTest {

    private static CourierTaskFacade facade;

    private static Courier courier;
    private static String trackingNumber;

    @BeforeAll
    public static void setUpClass() {
        facade = new CourierTaskFacade();
        trackingNumber = "tester";
    }

    @BeforeEach
    public void setUp() {
        courier = new Courier("Bring");
    }

    @AfterEach
    public void tearDown() {
        courier = null;
    }

    @Test
    public void getTask_Success() {
        // Act
        Callable actual = CourierTaskFacade.getTask(courier, trackingNumber);

        // Assert
        assertNotNull(actual);
    }

    @Test
    public void getTask_Courier_Not_Implemented() {
        // Arrange
        courier = new Courier("Unsupported");

        // Act
        Callable actual = CourierTaskFacade.getTask(courier, trackingNumber);

        // Assert
        assertNull(actual);
    }

}
