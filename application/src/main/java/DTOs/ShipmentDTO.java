package DTOs;

import java.util.ArrayList;
import java.util.List;

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

}
