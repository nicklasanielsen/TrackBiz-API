package facades;

import DTOs.CourierDTO;
import entities.Courier;
import errorhandling.exceptions.UnsupportedCourierException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author Nicklas Nielsen
 */
public class CourierFacade {

    private static EntityManagerFactory emf;
    private static CourierFacade instance;

    private CourierFacade() {
        // Private constructor to ensure Singleton
    }

    public static CourierFacade getCourierFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CourierFacade();
        }

        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<Courier> getCourierEntities() {
        EntityManager em = getEntityManager();

        List<Courier> couriers;

        try {
            Query query = em.createNamedQuery("Courier.getAll");
            couriers = query.getResultList();

            return couriers;
        } finally {
            em.close();
        }
    }

    public List<CourierDTO> getCouriers() {
        List<Courier> couriers = getCourierEntities();
        List<CourierDTO> courierDTOs = new ArrayList();

        couriers.forEach(courier -> {
            courierDTOs.add(new CourierDTO(courier));
        });

        return courierDTOs;
    }

    public Courier getCourierEntity(String courierName) throws UnsupportedCourierException {
        EntityManager em = getEntityManager();

        try {
            Courier courier = em.find(Courier.class, courierName);

            if (courier == null) {
                throw new UnsupportedCourierException(courierName);
            }

            return courier;
        } finally {
            em.close();
        }
    }

    public CourierDTO getCourier(String courierName) throws UnsupportedCourierException {
        Courier courier = getCourierEntity(courierName);

        return new CourierDTO(courier);
    }

    public boolean isSupported(String courierName) {
        EntityManager em = getEntityManager();

        try {
            getCourierEntity(courierName);

            return true;
        } catch (UnsupportedCourierException e) {
            return false;
        } finally {
            em.close();
        }
    }

}
