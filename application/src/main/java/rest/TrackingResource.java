package rest;

import DTOs.ShipmentDTO;
import errorhandling.exceptions.FetchException;
import errorhandling.exceptions.NoShipmentsFoundException;
import errorhandling.exceptions.UnsupportedCourierException;
import facades.TrackingFacade;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import utils.EMF_Creator;

/**
 *
 * @author Nicklas Nielsen
 */
@Path("tracking")
public class TrackingResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final TrackingFacade FACADE = TrackingFacade.getTrackingFacade(EMF);
    private static final ExecutorService THREAD_POOL = Executors.newCachedThreadPool();

    @Context
    ServletContext context;

    @GET
    @Path("{courier}/{trackingNumber}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response trackShipments(@PathParam("courier") String courier, @PathParam("trackingNumber") String trackingNumber) throws UnsupportedCourierException, FetchException, NoShipmentsFoundException {
        List<ShipmentDTO> shipmentDTOs = FACADE.trackShipments(THREAD_POOL, courier, trackingNumber);
        return Response.ok(shipmentDTOs).build();
    }

}
