package courierTasks;

import DTOs.ShipmentDTO;
import java.util.List;
import java.util.concurrent.Callable;

/**
 *
 * @author Nicklas Nielsen
 */
public class DAOTask implements Callable<List<ShipmentDTO>> {

    private final String API_KEY = "";
    private final String TRACKING_NUMBER;

    public DAOTask(String trackingNumber) {
        TRACKING_NUMBER = trackingNumber;
    }

    @Override
    public List<ShipmentDTO> call() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String getUrl() {
        return "https://api.coolrunner.dk/v3/shipments/" + TRACKING_NUMBER + "/tracking";
    }

}
