package rest;

import DTOs.CourierDTO;
import entities.Courier;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;
import io.restassured.parsing.Parser;
import org.glassfish.grizzly.http.util.HttpStatus;
import static org.hamcrest.Matchers.hasItems;

/**
 *
 * @author Nicklas Nielsen
 */
public class CourierResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    private static List<Courier> couriers;
    private static List<CourierDTO> courierDTOs;

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

        couriers = new ArrayList();
        courierDTOs = new ArrayList();
    }

    @AfterAll
    public static void tearDownClass() {
        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Courier.deleteAll").executeUpdate();
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

        couriers.add(new Courier("testCourier1"));
        couriers.add(new Courier("testCourier2"));
        couriers.add(new Courier("testCourier3"));

        try {
            em.getTransaction().begin();
            couriers.forEach(courier -> {
                em.persist(courier);
            });
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
        couriers.clear();
        courierDTOs.clear();

        EntityManager em = emf.createEntityManager();

        try {
            em.getTransaction().begin();
            em.createNamedQuery("Courier.deleteAll").executeUpdate();
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void getAllCouriers_Success() {
        List<String> courierNames = new ArrayList();

        courierDTOs.forEach(courierDTO -> {
            courierNames.add(courierDTO.getName());
        });

        given()
                .accept(ContentType.JSON)
                .when()
                .get("/courier/all")
                .then()
                .statusCode(HttpStatus.OK_200.getStatusCode())
                .assertThat()
                .body("name", hasItems(courierNames.toArray(new String[0])));
    }

}
