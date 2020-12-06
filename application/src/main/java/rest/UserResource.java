package rest;

import DTOs.ShipmentDTO;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import entities.Courier;
import entities.User;
import errorhandling.exceptions.API_Exception;
import errorhandling.exceptions.FetchException;
import errorhandling.exceptions.NoShipmentsFoundException;
import errorhandling.exceptions.UnsupportedCourierException;
import facades.CourierFacade;
import facades.UserFacade;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import utils.EMF_Creator;

/**
 *
 * @author Nicklas Nielsen
 */
@Path("user")
public class UserResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final UserFacade FACADE = UserFacade.getUserFacade(EMF);
    private static final CourierFacade COURIER_FACADE = CourierFacade.getCourierFacade(EMF);
    private static final ExecutorService THREAD_POOL = Executors.newCachedThreadPool();

    @Context
    ServletContext context;

    @Context
    SecurityContext securityContext;

    @GET
    @Path("shipments")
    @RolesAllowed("User")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrackedShipments() throws FetchException {
        User user = getUser();
        List<ShipmentDTO> shipmentDTOs = FACADE.getTrackedShipmentsByUser(user, THREAD_POOL);

        return Response.ok(shipmentDTOs).build();
    }

    @POST
    @Path("shipments")
    @RolesAllowed("User")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addTrackedShipment(String jsonString) throws API_Exception, UnsupportedCourierException {
        User user = getUser();

        Courier courier = getCourier(jsonString);
        String trackingNumber = getStringFromJson("trackingNumber", jsonString);

        FACADE.addTrackedShipment(user, courier, trackingNumber);

        return Response.ok("{\n"
                + "\"code\": 200,\n"
                + "\"message\": \"Shipment added\"\n"
                + "}").build();
    }

    @DELETE
    @Path("shipments")
    @RolesAllowed("User")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response removeTrackedShipment(String jsonString) throws API_Exception, UnsupportedCourierException, NoShipmentsFoundException {
        User user = getUser();

        Courier courier = getCourier(jsonString);
        String trackingNumber = getStringFromJson("trackingNumber", jsonString);

        FACADE.removeTrackedShipment(user, courier, trackingNumber);

        return Response.ok("{\n"
                + "\"code\": 200,\n"
                + "\"message\": \"Shipment removed\"\n"
                + "}").build();
    }

    private User getUser() {
        String username = securityContext.getUserPrincipal().getName();
        return FACADE.getUserByUserName(username);
    }

    private Courier getCourier(String jsonString) throws API_Exception, UnsupportedCourierException {
        String courierName = getStringFromJson("courier", jsonString);
        return COURIER_FACADE.getCourierEntity(courierName);
    }

    private String getStringFromJson(String keyword, String jsonString) throws API_Exception {
        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
            return (json.get(keyword).getAsString());
        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }
    }

}
