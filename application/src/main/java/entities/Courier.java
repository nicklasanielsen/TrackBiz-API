package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Nicklas Nielsen
 */
@Entity
@Table(name = "COURIERS")
@NamedQueries({
    @NamedQuery(name = "Courier.getAll", query = "SELECT c FROM Courier c"),
    @NamedQuery(name = "Courier.deleteAll", query = "DELETE FROM Courier")})
public class Courier implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "NAME")
    private String name;

    @OneToMany(mappedBy = "courier")
    private List<Shipment> shipments;

    public Courier(String name) {
        this.name = name;
        shipments = new ArrayList();
    }

    public Courier() {
        shipments = new ArrayList();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Shipment> getShipments() {
        return shipments;
    }

    public void setShipments(List<Shipment> shipments) {
        this.shipments = shipments;
    }

    public void addShipment(Shipment shipment) {
        if (!shipments.contains(shipment)) {
            shipments.add(shipment);
            shipment.setCourier(this);
        }
    }

    public void removeShipment(Shipment shipment) {
        if (shipments.contains(shipment)) {
            shipments.remove(shipment);
            shipment.setCourier(null);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.shipments);
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
        final Courier other = (Courier) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.shipments, other.shipments)) {
            return false;
        }
        return true;
    }

}
