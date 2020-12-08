package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Nicklas Nielsen
 */
@Entity
@Table(name = "USERS")
@NamedQueries({
    @NamedQuery(name = "User.getAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findUser", query = "SELECT u FROM User u WHERE u.userName = :userName"),
    @NamedQuery(name = "User.deleteAllRows", query = "DELETE FROM User"),
    @NamedQuery(name = "User.getAllShipments", query = "SELECT s FROM Shipment s JOIN FETCH s.users u WHERE u = :user")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "USERNAME", length = 25)
    private String userName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "FIRSTNAME")
    private String firstName;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "LASTNAME")
    private String lastName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "PASSWORD")
    private String password;

    @ManyToMany
    @JoinTable(name = "LK_USERS_ROLES", joinColumns = {
        @JoinColumn(name = "USER", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "ROLE", referencedColumnName = "ROLE_NAME")})
    private List<Role> roles;

    @ManyToMany
    @JoinTable(name = "LK_USERS_SHIPMENTS", joinColumns = {
        @JoinColumn(name = "USER", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "SHIPMENT", referencedColumnName = "ID")
    })
    private List<Shipment> shipments;

    public User(String username, String firstname, String lastname, String password, List<Role> roles) {
        this.userName = username;
        this.firstName = firstname;
        this.lastName = lastname;
        this.roles = new ArrayList<>();
        shipments = new ArrayList();
        this.password = getHashWithSalt(password);
        created = new Date();

        roles.forEach((Role role) -> {
            addRole(role);
        });
    }

    public User() {
        this.roles = new ArrayList<>();
        shipments = new ArrayList();
    }

    public Long getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getCreated() {
        return created;
    }

    public boolean verifyPassword(String password) {
        return BCrypt.checkpw(password, this.password);
    }

    public void setPassword(String password) {
        this.password = getHashWithSalt(password);
    }

    private String getHashWithSalt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(4));
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void addRole(Role role) {
        if (!roles.contains(role)) {
            roles.add(role);
            role.getUserList().add(this);
        }
    }

    public void removeRole(Role role) {
        if (roles.contains(role)) {
            roles.remove(role);
            role.getUserList().remove(this);
        }
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
            shipment.getUsers().add(this);
        }
    }

    public void removeShipment(Shipment shipment) {
        if (shipments.contains(shipment)) {
            shipments.remove(shipment);
            shipment.getUsers().remove(this);
        }
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.userName);
        hash = 41 * hash + Objects.hashCode(this.firstName);
        hash = 41 * hash + Objects.hashCode(this.lastName);
        hash = 41 * hash + Objects.hashCode(this.created);
        hash = 41 * hash + Objects.hashCode(this.password);
        hash = 41 * hash + Objects.hashCode(this.roles);
        hash = 41 * hash + Objects.hashCode(this.shipments);
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
        final User other = (User) obj;
        if (!Objects.equals(this.userName, other.userName)) {
            return false;
        }
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        if (!Objects.equals(this.created, other.created)) {
            return false;
        }
        if (!Objects.equals(this.roles, other.roles)) {
            return false;
        }
        if (!Objects.equals(this.shipments, other.shipments)) {
            return false;
        }
        return true;
    }

}
