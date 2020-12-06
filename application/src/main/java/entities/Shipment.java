package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Nicklas Nielsen
 */
@Entity
@Table(name = "SHIPMENTS")
@NamedQueries({
    @NamedQuery(name = "Shipment.deleteAllRows", query = "DELETE FROM Shipment"),
    @NamedQuery(name = "Shipment.getShipment", query = "SELECT s FROM Shipment s JOIN FETCH s.courier c WHERE s.trackingNumber = :trackingNumber AND c.name = :courierName")
})
public class Shipment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Courier courier;

    @Basic(optional = false)
    @NotNull
    private String trackingNumber;

    @ManyToMany(mappedBy = "shipments")
    private List<User> users;

    public Shipment(Courier courier, String trackingNumber) {
        this.courier = courier;
        this.trackingNumber = trackingNumber;
        users = new ArrayList();
    }

    public Shipment() {
        users = new ArrayList();
    }

    public Long getId() {
        return id;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
            user.getShipments().add(this);
        }
    }

    public void removeUser(User user) {
        if (users.contains(user)) {
            users.remove(user);
            user.getShipments().remove(this);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.trackingNumber);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Shipment other = (Shipment) obj;
        if (!Objects.equals(this.trackingNumber, other.trackingNumber)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
