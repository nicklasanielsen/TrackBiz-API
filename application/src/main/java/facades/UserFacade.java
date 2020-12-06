package facades;

import DTOs.ShipmentDTO;
import DTOs.UserDTO;
import entities.Courier;
import entities.Role;
import entities.Shipment;
import entities.User;
import errorhandling.exceptions.AuthenticationException;
import errorhandling.exceptions.DatabaseException;
import errorhandling.exceptions.FetchException;
import errorhandling.exceptions.NoShipmentsFoundException;
import errorhandling.exceptions.UnsupportedCourierException;
import errorhandling.exceptions.UserCreationException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author Nicklas Nielsen
 */
public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;
    private static RoleFacade roleFacade;

    private UserFacade() {
        // Private constructor to ensure Singleton
    }

    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            roleFacade = RoleFacade.getRoleFacade(emf);
            instance = new UserFacade();
        }

        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public UserDTO createUser(String username, String firstName, String lastName, String password) throws DatabaseException, UserCreationException {
        if (username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || password.isEmpty()) {
            throw new UserCreationException("Not all user credentials was provided.");
        }

        EntityManager em = getEntityManager();

        List<Role> defaultRoles = roleFacade.getDefaultRoles();

        User user = new User(username, firstName, lastName, password, defaultRoles);

        try {
            // Checking if username is in use
            if (em.find(User.class, username) != null) {
                throw new UserCreationException("Username already in use.");
            }

            em.getTransaction().begin();
            em.persist(user);

            for (Role role : user.getRoles()) {
                em.merge(role);
            }

            em.getTransaction().commit();

            return new UserDTO(user);
        } catch (Exception e) {
            if (e instanceof UserCreationException) {
                throw e;
            }

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            throw new DatabaseException("Something went wrong! Failed to create user, please try again later.");
        } finally {
            em.close();
        }
    }

    public UserDTO login(String userName, String password) throws AuthenticationException {
        EntityManager em = getEntityManager();

        try {
            User user = em.find(User.class, userName);

            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid username and/or password.");
            }

            return new UserDTO(user);
        } finally {
            em.close();
        }
    }

    public User getUserByUserName(String userName) {
        EntityManager em = getEntityManager();

        try {
            User user = em.find(User.class, userName);

            if (user == null) {
                throw new UnsupportedOperationException();
            }

            return user;
        } finally {
            em.close();
        }
    }

    public List<ShipmentDTO> getTrackedShipmentsByUser(User user, ExecutorService treadPool) throws FetchException, NoShipmentsFoundException {
        EntityManager em = getEntityManager();

        TrackingFacade trackingFacade = TrackingFacade.getTrackingFacade(emf);
        List<ShipmentDTO> shipmentDTOs = new ArrayList();

        String courier, trackingNumber = "";

        try {
            Query query = em.createNamedQuery("User.getAllShipments");
            query.setParameter("user", user);
            List<Shipment> shipments = query.getResultList();

            if (shipments.isEmpty()) {
                throw new NoShipmentsFoundException("your account");
            }

            for (Shipment shipment : shipments) {
                courier = shipment.getCourier().getName();
                trackingNumber = shipment.getTrackingNumber();

                shipmentDTOs.addAll(trackingFacade.trackShipments(treadPool, courier, trackingNumber));
            }

            return shipmentDTOs;
        } catch (NoShipmentsFoundException | UnsupportedCourierException ex) {
            throw new NoShipmentsFoundException("your account");
        } finally {
            em.close();
        }
    }

    public void addTrackedShipment(User user, Courier courier, String trackingNumber) {
        EntityManager em = getEntityManager();

        Shipment shipment = getShipment(courier, trackingNumber);
        user.addShipment(shipment);

        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void removeTrackedShipment(User user, Courier courier, String trackingNumber) throws NoShipmentsFoundException {
        EntityManager em = getEntityManager();

        Shipment shipment = getShipment(courier, trackingNumber);

        if (!shipment.getUsers().contains(user)) {
            throw new NoShipmentsFoundException(trackingNumber);
        }

        shipment.removeUser(user);

        try {
            em.getTransaction().begin();
            em.merge(shipment);
            em.getTransaction().commit();

            if (shipment.getUsers().isEmpty()) {
                em.getTransaction().begin();
                shipment = em.find(Shipment.class, shipment.getId());
                em.remove(shipment);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }

    private Shipment getShipment(Courier courier, String trackingNumber) {
        EntityManager em = getEntityManager();

        try {
            Query query = em.createNamedQuery("Shipment.getShipment");
            query.setParameter("trackingNumber", trackingNumber);
            query.setParameter("courierName", courier.getName());

            return (Shipment) query.getSingleResult();
        } catch (NoResultException e) {
            return new Shipment(courier, trackingNumber);
        } finally {
            em.close();
        }
    }

}
