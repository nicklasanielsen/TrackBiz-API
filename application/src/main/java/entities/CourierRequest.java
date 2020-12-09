package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Nicklas Nielsen
 */
@Entity
@Table(name = "FEEDBACK")
@NamedQueries({
    @NamedQuery(name = "CourierRequest.getAll", query = "SELECT c FROM CourierRequest c")})
public class CourierRequest implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Basic(optional = false)
    @NotNull
    @Column(name = "COURIER_NAME")
    private String courierName;

    @Basic(optional = false)
    @NotNull
    @Column(name = "COURIER_URL")
    private String courierURL;

    @Basic(optional = false)
    @NotNull
    @Column(name = "MESSAGE")
    private String msg;

    public CourierRequest(String courierName, String courierURL, String msg) {
        this.courierName = courierName;
        this.courierURL = courierURL;
        this.msg = msg;
    }

    public CourierRequest() {
    }

    public Long getId() {
        return id;
    }

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public String getCourierURL() {
        return courierURL;
    }

    public void setCourierURL(String courierURL) {
        this.courierURL = courierURL;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.courierName);
        hash = 59 * hash + Objects.hashCode(this.courierURL);
        hash = 59 * hash + Objects.hashCode(this.msg);
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
        final CourierRequest other = (CourierRequest) obj;
        if (!Objects.equals(this.courierName, other.courierName)) {
            return false;
        }
        if (!Objects.equals(this.courierURL, other.courierURL)) {
            return false;
        }
        if (!Objects.equals(this.msg, other.msg)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
