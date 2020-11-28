package DTOs;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Nicklas Nielsen
 */
public class EventDTO {

    private String status, description, country, city, timeStamp;

    public EventDTO(String status, String description, String country, String city, Date timeStamp) {
        this.status = status;
        this.description = description;
        this.country = country;
        this.city = city;
        this.timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z").format(timeStamp);
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

}
