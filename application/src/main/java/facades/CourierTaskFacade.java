package facades;

import DTOs.ShipmentDTO;
import courierTasks.*;
import entities.Courier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 *
 * @author Nicklas Nielsen
 */
public class CourierTaskFacade {

    public static Callable getTask(Courier courier, String trackingNumber) {
        Map<String, Callable<List<ShipmentDTO>>> tasks = initTasks(trackingNumber);

        Callable<List<ShipmentDTO>> task = tasks.get(courier.getName());

        return task;
    }

    private static Map<String, Callable<List<ShipmentDTO>>> initTasks(String trackingNumber) {
        Map<String, Callable<List<ShipmentDTO>>> tasks = new HashMap<>();

        tasks.put("Bring", new BringTask(trackingNumber));
        tasks.put("GLS", new GLSTask(trackingNumber));
        tasks.put("PostNord", new PostNordTask(trackingNumber));
        tasks.put("DHL", new DHLTask(trackingNumber));
        tasks.put("TNT Express", new TNT_ExpressTask(trackingNumber));

        return tasks;
    }

}
