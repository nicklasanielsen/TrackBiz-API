package errorhandling.mappers;

import DTOs.ExceptionDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import errorhandling.exceptions.NoShipmentsFoundException;
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
public class NoShipmentsfoundExceptionMapper implements ExceptionMapper<NoShipmentsFoundException> {

    @Context
    ServletContext context;

    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public Response toResponse(NoShipmentsFoundException ex) {
        Logger.getLogger(API_ExceptionMapper.class.getName()).log(Level.SEVERE, null, ex);
        ExceptionDTO err = new ExceptionDTO(ex.getErrorCode(), ex.getMessage());

        return Response
                .status(ex.getErrorCode())
                .entity(gson.toJson(err))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
