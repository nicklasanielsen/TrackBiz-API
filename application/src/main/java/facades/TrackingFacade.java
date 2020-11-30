package facades;

import DTOs.ShipmentDTO;
import entities.Courier;
import errorhandling.exceptions.FetchException;
import errorhandling.exceptions.NoShipmentsFoundException;
import errorhandling.exceptions.UnsupportedCourierException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Nicklas Nielsen
 */
public class TrackingFacade {

    private static TrackingFacade instance;
    private static CourierFacade courierFacade;

    private TrackingFacade() {
        // Private constructor to ensure Singleton
    }

    public static TrackingFacade getTrackingFacade(EntityManagerFactory emf) {
        if (instance == null) {
            instance = new TrackingFacade();
            courierFacade = CourierFacade.getCourierFacade(emf);
        }

        return instance;
    }

    public List<ShipmentDTO> trackShipments(ExecutorService threadPool, String courier, String trackingNumber) throws UnsupportedCourierException, FetchException, NoShipmentsFoundException {
        boolean anyCourier = courier.equals("Any");

        if (!anyCourier && !courierFacade.isSupported(courier)) {
            throw new UnsupportedCourierException(courier);
        }

        List<Courier> couriers = prepareCouriers(courier);

        return processTrackingRequest(threadPool, couriers, trackingNumber);
    }

    private List<Courier> prepareCouriers(String courier) throws UnsupportedCourierException {
        boolean anyCourier = courier.equals("Any");

        if (anyCourier) {
            return courierFacade.getCourierEntities();
        }

        return Arrays.asList(courierFacade.getCourierEntity(courier));
    }

    private List<ShipmentDTO> processTrackingRequest(ExecutorService threadPool, List<Courier> couriers, String trackingNumber) throws FetchException, NoShipmentsFoundException {
        List<Future<List<ShipmentDTO>>> futures = new ArrayList();

        // Starting all threads / callables
        for (Courier courier : couriers) {
            Callable task = CourierTaskFacade.getTask(courier, trackingNumber);
            Future<List<ShipmentDTO>> future = threadPool.submit(task);
            futures.add(future);
        }

        List<ShipmentDTO> shipmentDTOs = new ArrayList();

        // Getting results, timeout set to 10 sec
        try {
            for (Future<List<ShipmentDTO>> future : futures) {
                try {
                    shipmentDTOs.addAll(future.get(10, TimeUnit.SECONDS));
                } catch (NullPointerException e) {

                }
            }
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            if (e instanceof TimeoutException) {
                throw new FetchException("One or more of our partners did not respond when we tried to track your shipment, please try again later..", 503);
            }

            throw new FetchException("A system error occurred while tracking your shipment, please contact us regarding the error, or try again later.", 500);
        }

        if (shipmentDTOs.isEmpty()) {
            throw new NoShipmentsFoundException(trackingNumber);
        }

        return shipmentDTOs;
    }

}
