package rest;

import DTOs.CourierRequestDTO;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import errorhandling.exceptions.API_Exception;
import facades.FeedbackFacade;
import java.util.List;
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
@Path("feedback")
public class FeedbackResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final FeedbackFacade FACADE = FeedbackFacade.getFeedbackFacade(EMF);

    @Context
    ServletContext context;

    @Context
    SecurityContext securityContext;

    @GET
    @Path("courier")
    @RolesAllowed("Admin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFeedbackRequests() {
        List<CourierRequestDTO> courierRequestDTOs = FACADE.getRequests();
        return Response.ok(courierRequestDTOs).build();
    }

    @POST
    @Path("courier")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addFeedbackRequest(String jsonString) throws API_Exception {
        String courierName, courierURL, msg;

        courierName = getStringFromJson("name", jsonString);
        courierURL = getStringFromJson("url", jsonString);
        msg = getStringFromJson("message", jsonString);

        CourierRequestDTO courierRequestDTO = FACADE.addRequest(courierName, courierURL, msg);
        return Response.ok(courierRequestDTO).build();
    }

    @DELETE
    @Path("courier")
    @RolesAllowed("Admin")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteFeedbackRequest(String jsonString) throws API_Exception {
        String id = getStringFromJson("id", jsonString);

        FACADE.deleteRequest(id);

        return Response.ok("{\n"
                + "\"code\": 200,\n"
                + "\"message\": \"Feedback request removed\"\n"
                + "}").build();
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
