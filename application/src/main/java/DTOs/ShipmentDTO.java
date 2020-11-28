package DTOs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Nicklas Nielsen
 */
public class ShipmentDTO {

    private String courier, trackingNumber;
    private String consignor, consignee, originCountry, originCity, destinationCountry, destinationCity, volume, weight;
    private EventDTO currentEvent;
    private List<EventDTO> events;

    public ShipmentDTO(String courier, String trackingNumber) {
        this.courier = courier;
        this.trackingNumber = trackingNumber;
        events = new ArrayList();
    }

    public String getCourier() {
        return courier;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public String getConsignor() {
        return consignor;
    }

    public void setConsignor(String consignor) {
        this.consignor = consignor;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public String getOriginCity() {
        return originCity;
    }

    public void setOriginCity(String originCity) {
        this.originCity = originCity;
    }

    public String getDestinationCountry() {
        return destinationCountry;
    }

    public void setDestinationCountry(String destinationCountry) {
        this.destinationCountry = destinationCountry;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public EventDTO getCurrentEvent() {
        return currentEvent;
    }

    public void setCurrentEvent(EventDTO currentEvent) {
        this.currentEvent = currentEvent;
    }

    public void addEvent(EventDTO event) {
        events.add(event);
    }

    public List<EventDTO> getEvents() {
        return events;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.courier);
        hash = 47 * hash + Objects.hashCode(this.trackingNumber);
        hash = 47 * hash + Objects.hashCode(this.consignor);
        hash = 47 * hash + Objects.hashCode(this.consignee);
        hash = 47 * hash + Objects.hashCode(this.originCountry);
        hash = 47 * hash + Objects.hashCode(this.originCity);
        hash = 47 * hash + Objects.hashCode(this.destinationCountry);
        hash = 47 * hash + Objects.hashCode(this.destinationCity);
        hash = 47 * hash + Objects.hashCode(this.volume);
        hash = 47 * hash + Objects.hashCode(this.weight);
        hash = 47 * hash + Objects.hashCode(this.currentEvent);
        hash = 47 * hash + Objects.hashCode(this.events);
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
        final ShipmentDTO other = (ShipmentDTO) obj;
        if (!Objects.equals(this.courier, other.courier)) {
            return false;
        }
        if (!Objects.equals(this.trackingNumber, other.trackingNumber)) {
            return false;
        }
        if (!Objects.equals(this.consignor, other.consignor)) {
            return false;
        }
        if (!Objects.equals(this.consignee, other.consignee)) {
            return false;
        }
        if (!Objects.equals(this.originCountry, other.originCountry)) {
            return false;
        }
        if (!Objects.equals(this.originCity, other.originCity)) {
            return false;
        }
        if (!Objects.equals(this.destinationCountry, other.destinationCountry)) {
            return false;
        }
        if (!Objects.equals(this.destinationCity, other.destinationCity)) {
            return false;
        }
        if (!Objects.equals(this.volume, other.volume)) {
            return false;
        }
        if (!Objects.equals(this.weight, other.weight)) {
            return false;
        }
        if (!Objects.equals(this.currentEvent, other.currentEvent)) {
            return false;
        }
        if (!Objects.equals(this.events, other.events)) {
            return false;
        }
        return true;
    }

}
