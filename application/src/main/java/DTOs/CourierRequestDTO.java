package DTOs;

import entities.CourierRequest;
import java.util.Objects;

/**
 *
 * @author Nicklas Nielsen
 */
public class CourierRequestDTO {

    private Long id;
    private String name;
    private String url;
    private String message;

    public CourierRequestDTO(CourierRequest courierRequest) {
        id = courierRequest.getId();
        name = courierRequest.getCourierName();
        url = courierRequest.getCourierURL();
        message = courierRequest.getMsg();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.url);
        hash = 89 * hash + Objects.hashCode(this.message);
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
        final CourierRequestDTO other = (CourierRequestDTO) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        if (!Objects.equals(this.message, other.message)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
