package DTOs;

import entities.Courier;
import java.util.Objects;

/**
 *
 * @author Nicklas Nielsen
 */
public class CourierDTO {

    private String name;

    public CourierDTO(Courier courier) {
        name = courier.getName();
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.name);
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
        final CourierDTO other = (CourierDTO) obj;
        return Objects.equals(this.name, other.name);
    }

}
