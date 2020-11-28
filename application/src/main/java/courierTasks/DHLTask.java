package courierTasks;

import DTOs.ShipmentDTO;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author Nicklas Nielsen
 */
public class DHLTask implements Callable<List<ShipmentDTO>> {

    private final String TRACKING_NUMBER;

    public DHLTask(String trackingNumber) {
        TRACKING_NUMBER = trackingNumber;
    }

    @Override
    public List<ShipmentDTO> call() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
