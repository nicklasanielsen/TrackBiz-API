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
public class DHLTask implements Callable<List<ShipmentDTO>> {

    private final String COURIER_NAME = "DHL";
    private final String API_KEY = "3LgKvzF7aKhOIx9ek5Q2RX70i2KPCy7l";
    private final String TRACKING_NUMBER;

    public DHLTask(String trackingNumber) {
        TRACKING_NUMBER = trackingNumber;
    }

    @Override
    public List<ShipmentDTO> call() throws Exception {
        HttpURLConnection connection = HttpUtils.openConnection(getUrl());
        connection = HttpUtils.addRequestProperty(connection, "DHL-API-Key", API_KEY);

        try {
            JsonObject jsonObject = HttpUtils.fetchData(connection);
            return convertToShipmentDTO(jsonObject);
        } catch (FetchException e) {
            return new ArrayList<>();
        } finally {
            connection.disconnect();
        }
    }

    private String getUrl() {
        return "https://api-eu.dhl.com/track/shipments?trackingNumber=" + TRACKING_NUMBER;
    }

    private List<ShipmentDTO> convertToShipmentDTO(JsonObject jsonObject) {
        List<ShipmentDTO> shipmentDTOs = new ArrayList();
        ShipmentDTO shipmentDTO;

        String consignor, consignee, originCountry, originCity,
                destinationCountry, destinationCity, volume, weight;

        try {
            JsonArray shipments = jsonObject.getAsJsonArray("shipments");

            for (JsonElement object : shipments) {
                JsonObject shipment = object.getAsJsonObject();

                shipmentDTO = new ShipmentDTO(COURIER_NAME, TRACKING_NUMBER);

                // Consignor & Consignee
                consignor = "Unknown";
                consignee = "Unknown";

                shipmentDTO.setConsignor(consignor);
                shipmentDTO.setConsignee(consignee);

                // Origin
                JsonObject origin = shipment.get("origin").getAsJsonObject();

                originCountry = getStringValue(origin.getAsJsonObject("address"), "countryCode");
                originCity = "Unknown";

                shipmentDTO.setOriginCountry(originCountry);
                shipmentDTO.setOriginCity(originCity);

                // Destination
                JsonObject destination = shipment.get("destination").getAsJsonObject();

                destinationCountry = getStringValue(origin.getAsJsonObject("address"), "countryCode");
                destinationCity = "Unknown";

                shipmentDTO.setDestinationCountry(destinationCountry);
                shipmentDTO.setDestinationCity(destinationCity);

                // Dimensions
                JsonObject dimensions = shipment.getAsJsonObject("details");

                volume = "";
                weight = getStringValue(dimensions.getAsJsonObject("weight"), "value");

                shipmentDTO.setVolume(volume);
                shipmentDTO.setWeight(weight);

                String status, description, country, city;
                Date timeStamp;
                EventDTO event;

                // Events
                JsonArray events = shipment.getAsJsonArray("events");

                for (JsonElement shippingEvent : events) {
                    JsonObject eventObject = shippingEvent.getAsJsonObject();

                    status = getStatus(getStringValue(eventObject, "statusCode"));
                    description = getStringValue(eventObject, "description");
                    country = "Unknown";
                    city = "Unknown";
                    timeStamp = getDateValue(eventObject, "timestamp");

                    event = new EventDTO(status, description, country, city, timeStamp);
                    shipmentDTO.addEvent(event);
                }

                // Current event
                event = shipmentDTO.getEvents().get(0);
                shipmentDTO.setCurrentEvent(event);

                shipmentDTOs.add(shipmentDTO);
            }
        } catch (Exception e) {

        }

        return shipmentDTOs;
    }

    private String getStringValue(JsonObject jsonObject, String requestedElement) {
        try {
            JsonElement jsonElement = jsonObject.get(requestedElement);

            if (jsonElement.isJsonNull() || jsonElement.getAsString().isEmpty()) {
                return "Unknown";
            }

            return jsonElement.getAsString();
        } catch (Exception e) {
            return "Unknown";
        }
    }

    private Date getDateValue(JsonObject jsonObject, String requestedElement) {
        try {
            String date = getStringValue(jsonObject, requestedElement);
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date);
        } catch (ParseException ex) {
            return new Date(0);
        }
    }

    private String getStatus(String status) {
        switch (status) {
            case "pre-transit":
                return "Info Received";
            case "transit":
                return "In Transit";
            case "failure":
                return "Failed Attempt";
            case "delivered":
                return "Delivered";
            case "unknown":
                return "Exception";
            default:
                break;
        }

        return "Expired";
    }

}
