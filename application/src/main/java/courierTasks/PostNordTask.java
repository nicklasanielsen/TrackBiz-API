package courierTasks;

import DTOs.ShipmentDTO;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author Nicklas Nielsen
 */
public class PostNordTask implements Callable<List<ShipmentDTO>> {

    private final String API_KEY = "c6b3ea004ba5ac1d2a02da9518e16800";
    private final String TRACKING_NUMBER;

    public PostNordTask(String trackingNumber) {
        TRACKING_NUMBER = trackingNumber;
    }

    @Override
    public List<ShipmentDTO> call() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
