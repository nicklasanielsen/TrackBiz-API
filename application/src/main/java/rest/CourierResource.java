package rest;

import DTOs.CourierDTO;
import facades.CourierFacade;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import utils.EMF_Creator;

/**
 *
 * @author Nicklas Nielsen
 */
@Path("courier")
public class CourierResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final CourierFacade FACADE = CourierFacade.getCourierFacade(EMF);

    @Context
    ServletContext context;

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCouriers() {
        List<CourierDTO> courierDTOs = FACADE.getCouriers();
        return Response.ok(courierDTOs).build();
    }

}
