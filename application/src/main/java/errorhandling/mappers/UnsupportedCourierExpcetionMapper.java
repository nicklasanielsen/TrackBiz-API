package errorhandling.mappers;

import DTOs.ExceptionDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import errorhandling.exceptions.UnsupportedCourierException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author Nicklas Nielsen
 */
@Provider
public class UnsupportedCourierExpcetionMapper implements ExceptionMapper<UnsupportedCourierException> {

    @Context
    ServletContext context;

    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Response toResponse(UnsupportedCourierException ex) {
        Logger.getLogger(UnsupportedCourierException.class.getName()).log(Level.SEVERE, null, ex);
        Logger.getLogger(UnsupportedCourierException.class.getName()).log(Level.SEVERE, null, ex);
        ExceptionDTO err = new ExceptionDTO(Response.Status.NOT_IMPLEMENTED.getStatusCode(), ex.getMessage());

        return Response
                .status(Response.Status.NOT_IMPLEMENTED)
                .entity(gson.toJson(err))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
