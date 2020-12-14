package rest;

import entities.CourierRequest;
import entities.Role;
import entities.User;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.ResponseBody;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

/**
 *
 * @author Nicklas Nielsen
 */
public class FeedbackResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    private static User user;
    private static List<Role> roles;
    private static CourierRequest courierRequest;
    private static String securityToken;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
        roles = new ArrayList();

        roles.add(new Role("User"));
        roles.add(new Role("Admin"));

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            for (Role role : roles) {
                em.persist(role);
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterAll
    public static void tearDownClass() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("CourierRequest.deleteAll").executeUpdate();
            em.createNamedQuery("Role.deleteAllRows").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }

        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        courierRequest = new CourierRequest("testName", "testURL", "testMessage");
        user = new User("username", "firstname", "lastname", "password", roles);

        try {
            em.getTransaction().begin();
            em.persist(courierRequest);
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        EntityManager em = emf.createEntityManager();

        courierRequest = null;
        securityToken = null;

        try {
            em.getTransaction().begin();
            em.createNamedQuery("CourierRequest.deleteAll").executeUpdate();
            em.createNamedQuery("User.deleteAllRows").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    private static String login(String username, String password) {
        return given()
                .contentType(ContentType.JSON)
                .body(String.format("{userName: \"%s\", password: \"%s\"}", username, password))
                .when().post("/auth/login")
                .then()
                .extract().path("token");
    }

    @Test
    public void getFeedbackRequests_Success() {
        securityToken = login(user.getUserName(), "password");

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .when()
                .get("/feedback/courier")
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .assertThat()
                .body("size()", is(1));
    }

    @Test
    public void addFeedbackRequest_Success() {
        securityToken = login(user.getUserName(), "password");

        String requestBody = "{"
                + "\"name\": \"courierName\","
                + "\"url\": \"www.example.com\","
                + "\"message\": \"Test\""
                + "}";

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .body(requestBody)
                .when()
                .post("/feedback/courier")
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode());
    }

}
