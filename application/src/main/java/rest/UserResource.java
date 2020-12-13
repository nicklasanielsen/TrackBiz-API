package rest;

import DTOs.RoleDTO;
import DTOs.ShipmentDTO;
import DTOs.UserDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import entities.Courier;
import entities.User;
import errorhandling.exceptions.API_Exception;
import errorhandling.exceptions.FetchException;
import errorhandling.exceptions.NoShipmentsFoundException;
import errorhandling.exceptions.UnsupportedCourierException;
import errorhandling.exceptions.UserCreationException;
import facades.CourierFacade;
import facades.UserFacade;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import static rest.AuthenticationResource.TOKEN_EXPIRE_TIME;
import security.SharedSecret;
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
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Context
    ServletContext context;

    @Context
    SecurityContext securityContext;

    @GET
    @RolesAllowed("User")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserAccount() {
        String username = securityContext.getUserPrincipal().getName();
        UserDTO userDTO = FACADE.getUserAccount(username);

        return Response.ok(userDTO).build();
    }

    @PUT
    @RolesAllowed("User")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editUserAccount(String jsonString) throws UserCreationException, API_Exception {
        String oldUserName, newUserName, firstName, lastName, password;

        try {
            JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();

            oldUserName = securityContext.getUserPrincipal().getName();
            newUserName = json.get("userName").getAsString();
            firstName = json.get("firstName").getAsString();
            lastName = json.get("lastName").getAsString();
            password = json.get("password").getAsString();

        } catch (Exception e) {
            throw new API_Exception("Malformed JSON Suplied", 400, e);
        }

        try {
            UserDTO userDTO = FACADE.editUserAccount(oldUserName, newUserName, firstName, lastName, password);

            // Preparing respone
            JsonObject jsonObject = new JsonObject();

            String token = createToken(userDTO);

            jsonObject.addProperty("userName", userDTO.getUserName());
            jsonObject.addProperty("token", token);

            return Response.ok(GSON.toJson(jsonObject)).build();
        } catch (JOSEException e) {
            throw new API_Exception("Something went wrong, please try again later ...");
        }
    }

    @DELETE
    @RolesAllowed("User")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUserAccount() {
        String username = securityContext.getUserPrincipal().getName();
        FACADE.deleteUserAccount(username);

        return Response.ok("{\n"
                + "\"code\": 200,\n"
                + "\"message\": \"User deleted\"\n"
                + "}").build();
    }

    @GET
    @Path("shipments")
    @RolesAllowed("User")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTrackedShipments() throws FetchException, NoShipmentsFoundException {
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
        String trackingNumber = getStringFromJson("shippingNumber", jsonString);

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
        String trackingNumber = getStringFromJson("shippingNumber", jsonString);

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

    private String createToken(UserDTO user) throws JOSEException {
        List<String> roles = new ArrayList<>();

        for (RoleDTO role : user.getRoleList()) {
            roles.add(role.getRoleName());
        }

        Date date = new Date();
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUserName())
                .claim("username", user.getUserName())
                .claim("roles", String.join(",", roles))
                .issuer("NewBiz")
                .issueTime(date)
                .expirationTime(new Date(date.getTime() + TOKEN_EXPIRE_TIME))
                .build();

        SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
        signedJWT.sign(new MACSigner(SharedSecret.getSharedKey()));

        return signedJWT.serialize();
    }

}
