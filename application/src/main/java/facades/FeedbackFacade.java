package facades;

import DTOs.CourierRequestDTO;
import entities.CourierRequest;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Nicklas Nielsen
 */
public class FeedbackFacade {

    private static EntityManagerFactory emf;
    private static FeedbackFacade instance;

    private FeedbackFacade() {
        // Private constructor to ensure Singleton
    }

    public static FeedbackFacade getFeedbackFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new FeedbackFacade();
        }

        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public List<CourierRequestDTO> getRequests() {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createNamedQuery("CourierRequest.getAll");

            List<CourierRequest> courierRequests = query.getResultList();
            List<CourierRequestDTO> courierRequestDTOs = new ArrayList();

            for (CourierRequest courierRequest : courierRequests) {
                courierRequestDTOs.add(new CourierRequestDTO(courierRequest));
            }

            return courierRequestDTOs;
        } finally {
            em.close();
        }
    }

    public CourierRequestDTO addRequest(String courierName, String courierURL, String msg) {
        EntityManager em = getEntityManager();

        CourierRequest courierRequest = new CourierRequest(courierName, courierURL, msg);

        try {
            em.getTransaction().begin();
            em.persist(courierRequest);
            em.getTransaction().commit();

            return new CourierRequestDTO(courierRequest);
        } finally {
            em.close();
        }
    }

    public boolean deleteRequest(String id) {
        EntityManager em = getEntityManager();

        try {
            em.getTransaction().begin();
            CourierRequest courierRequest = em.find(CourierRequest.class, id);
            em.remove(courierRequest);
            em.getTransaction().commit();

            return true;
        } catch (Exception e) {
            return false;
        } finally {
            em.close();
        }
    }
}
