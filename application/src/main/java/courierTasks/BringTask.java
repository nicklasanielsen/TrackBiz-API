package courierTasks;

import DTOs.EventDTO;
import DTOs.ShipmentDTO;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import errorhandling.exceptions.FetchException;
import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import utils.HttpUtils;

/**
 *
 * @author Nicklas Nielsen
 */
public class BringTask implements Callable<List<ShipmentDTO>> {

    private final String COURIER_NAME = "Bring";
    private final String TRACKING_NUMBER;

    public BringTask(String trackingNumber) {
        TRACKING_NUMBER = trackingNumber;
    }

    @Override
    public List<ShipmentDTO> call() throws Exception {
        HttpURLConnection connection = HttpUtils.openConnection(getUrl());

        try {
            JsonObject jsonObject = HttpUtils.fetchData(connection);
            return convertToShipmentDTO(jsonObject, COURIER_NAME, TRACKING_NUMBER);
        } catch (FetchException e) {
            return null;
        }
    }

    private String getUrl() {
        return "https://tracking.bring.dk/tracking/api/fetch/" + TRACKING_NUMBER + "?lang=en";
    }

    private List<ShipmentDTO> convertToShipmentDTO(JsonObject jsonObject, String courier, String trackingNumber) {
        List<ShipmentDTO> shipmentDTOs = new ArrayList();
        ShipmentDTO shipmentDTO;

        String consignor, consignee, originCountry, originCity,
                destinationCountry, destinationCity, volume, weight;

        JsonArray shipments = jsonObject.getAsJsonArray("consignmentSet");

        for (JsonElement object : shipments) {
            JsonObject shipment = object.getAsJsonObject();

            shipmentDTO = new ShipmentDTO(courier, trackingNumber);

            // Consignor & Consignee
            consignor = getStringValue(shipment, "senderName");
            consignee = getStringValue(shipment, "recipientName");

            shipmentDTO.setConsignor(consignor);
            shipmentDTO.setConsignee(consignee);

            // Origin
            JsonObject origin = shipment.get("senderAddress").getAsJsonObject();

            originCountry = getStringValue(origin, "country");
            originCity = getStringValue(origin, "city");

            shipmentDTO.setOriginCountry(originCountry);
            shipmentDTO.setOriginCity(originCity);

            // Destination
            JsonObject destination = shipment.get("recipientHandlingAddress").getAsJsonObject();

            destinationCountry = getStringValue(destination, "country");
            destinationCity = getStringValue(destination, "city");

            shipmentDTO.setDestinationCountry(destinationCountry);
            shipmentDTO.setDestinationCity(destinationCity);

            // Dimensions
            volume = getStringValue(shipment, "totalVolumeInDm3");
            weight = getStringValue(shipment, "totalWeightInKgs");

            shipmentDTO.setVolume(volume);
            shipmentDTO.setWeight(weight);

            String status, description, country, city;
            Date timeStamp;
            EventDTO event;

            // Events
            JsonArray events = shipment.getAsJsonArray("packageSet").get(0).getAsJsonObject().getAsJsonArray("eventSet");
            List<EventDTO> eventDTOs = new ArrayList();

            for (JsonElement shippingEvent : events) {
                JsonObject eventObject = shippingEvent.getAsJsonObject();

                status = getStatus(getStringValue(eventObject, "status"));
                description = getStringValue(eventObject, "description");
                country = getStringValue(eventObject, "country");
                city = getStringValue(eventObject, "city");
                timeStamp = getDateValue(eventObject, "dateIso");

                event = new EventDTO(status, description, country, city, timeStamp);
                shipmentDTO.addEvent(event);
            }

            // Current event
            JsonObject currentEvent = events.get(0).getAsJsonObject();

            status = getStatus(getStringValue(currentEvent, "status"));
            description = getStringValue(currentEvent, "description");
            country = getStringValue(currentEvent, "country");
            city = getStringValue(currentEvent, "city");
            timeStamp = getDateValue(currentEvent, "dateIso");

            event = new EventDTO(status, description, country, city, timeStamp);
            shipmentDTO.setCurrentEvent(event);

            shipmentDTOs.add(shipmentDTO);
        }

        return shipmentDTOs;
    }

    private String getStringValue(JsonObject jsonObject, String requestedElement) {
        JsonElement jsonElement = jsonObject.get(requestedElement);

        if (jsonElement.isJsonNull() || jsonElement.getAsString().isEmpty()) {
            return "Unknown";
        }

        return jsonElement.getAsString();
    }

    private Date getDateValue(JsonObject jsonObject, String requestedElement) {
        try {
            String date = getStringValue(jsonObject, requestedElement);
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX").parse(date);
        } catch (ParseException ex) {
            return new Date(0);
        }
    }

    private String getStatus(String status) {
        if (status.equalsIgnoreCase("PRE_NOTIFIED")) {
            return "Info Received";
        } else if (status.equalsIgnoreCase("ARRIVED_DELIVERY")
                || status.equalsIgnoreCase("HANDED_IN")
                || status.equalsIgnoreCase("INTERNATIONAL")
                || status.equalsIgnoreCase("IN_TRANSIT")
                || status.equalsIgnoreCase("TERMINAL")) {
            return "In Transit";
        } else if (status.equalsIgnoreCase("TRANSPORT_TO_RECIPIENT")) {
            return "Out for Delivery";
        } else if (status.equalsIgnoreCase("ATTEMPTED_DELIVERY")) {
            return "Failed Attempt";
        } else if (status.equalsIgnoreCase("COLLECTED")
                || status.equalsIgnoreCase("DELIVERED")) {
            return "Delivered";
        } else if (status.equalsIgnoreCase("ARRIVED_COLLECTION")
                || status.equalsIgnoreCase("READY_FOR_PICKUP")) {
            return "Available for Pickup";
        } else if (status.equalsIgnoreCase("CUSTOMS")
                || status.equalsIgnoreCase("DELIVERED_SENDER")
                || status.equalsIgnoreCase("DELIVERY_CANCELLED")
                || status.equalsIgnoreCase("DELIVERY_CHANGED")
                || status.equalsIgnoreCase("DELIVERY_ORDERED")
                || status.equalsIgnoreCase("DEVIATION")
                || status.equalsIgnoreCase("RETURN")) {
            return "Exception";
        } else if (status.equalsIgnoreCase("NOTIFICATION_SENT")) {
            return "Pending";
        }

        return "Expired";
    }

}
