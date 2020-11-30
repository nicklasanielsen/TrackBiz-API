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
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import utils.HttpUtils;

/**
 *
 * @author Nicklas Nielsen
 */
public class PostNordTask implements Callable<List<ShipmentDTO>> {

    private final String COURIER_NAME = "PostNord";
    private final String API_KEY = "c6b3ea004ba5ac1d2a02da9518e16800";
    private final String TRACKING_NUMBER;

    public PostNordTask(String trackingNumber) {
        TRACKING_NUMBER = trackingNumber;
    }

    @Override
    public List<ShipmentDTO> call() throws Exception {
        HttpURLConnection connection = HttpUtils.openConnection(getUrl());
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
        return "https://api2.postnord.com/rest/shipment/v5/trackandtrace/findByIdentifier.json?apikey=" + API_KEY + "&id=" + TRACKING_NUMBER;
    }

    private List<ShipmentDTO> convertToShipmentDTO(JsonObject jsonObject) {
        List<ShipmentDTO> shipmentDTOs = new ArrayList();
        ShipmentDTO shipmentDTO;

        String consignor, consignee, originCountry, originCity,
                destinationCountry, destinationCity, volume, weight;

        try {
            JsonArray shipments = jsonObject.get("TrackingInformationResponse").getAsJsonObject().getAsJsonArray("shipments");

            for (JsonElement object : shipments) {
                JsonObject shipment = object.getAsJsonObject();

                shipmentDTO = new ShipmentDTO(COURIER_NAME, TRACKING_NUMBER);

                // Consignor & Consignee
                JsonObject consignorObject = shipment.getAsJsonObject("consignor");

                consignor = getStringValue(consignorObject, "name");
                consignee = "Unknown"; // Value not available due to privacy

                shipmentDTO.setConsignor(consignor);
                shipmentDTO.setConsignee(consignee);

                // Origin
                JsonObject originObject = consignorObject.getAsJsonObject("address");

                originCountry = getStringValue(originObject, "country");
                originCity = "Unknown"; // Value not available due to privacy

                shipmentDTO.setOriginCountry(originCountry);
                shipmentDTO.setOriginCity(originCity);

                // Destination
                JsonObject destinationObject = shipment.getAsJsonObject("consignee").getAsJsonObject("address");

                destinationCountry = getStringValue(destinationObject, "country");
                destinationCity = getStringValue(destinationObject, "city");

                shipmentDTO.setDestinationCountry(destinationCountry);
                shipmentDTO.setDestinationCity(destinationCity);

                // Dimensions
                JsonObject volumeObject = shipment.getAsJsonObject("totalVolume");
                JsonObject weightObject = shipment.getAsJsonObject("totalWeight");

                volume = getStringValue(volumeObject, "value");
                weight = getStringValue(weightObject, "value");

                shipmentDTO.setVolume(volume);
                shipmentDTO.setWeight(weight);

                String status, description, country, city;
                Date timeStamp;
                EventDTO event;

                // Events
                JsonArray events = shipment.getAsJsonArray("items").get(0).getAsJsonObject().getAsJsonArray("events");

                for (JsonElement shippingEvent : events) {
                    JsonObject eventObject = shippingEvent.getAsJsonObject();
                    JsonObject locationObject = eventObject.getAsJsonObject("location");

                    status = getStatus(getStringValue(eventObject, "status"));
                    description = getStringValue(eventObject, "eventDescription");
                    country = getStringValue(locationObject, "country");
                    city = getStringValue(locationObject, "city");
                    timeStamp = getDateValue(eventObject, "eventTime");

                    event = new EventDTO(status, description, country, city, timeStamp);
                    shipmentDTO.addEvent(event);
                }

                // Current event
                Collections.reverse(shipmentDTO.getEvents()); // Normally listed oldest to newest

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
        if (status.equalsIgnoreCase("INFORMED")) {
            return "Info Received";
        } else if (status.equalsIgnoreCase("EN_ROUTE")) {
            return "In Transit";
        } else if (status.equalsIgnoreCase("AVAILABLE_FOR_DELIVERY_PAR_LOC")) {
            return "Out for Delivery";
        } else if (status.equalsIgnoreCase("DELIVERY_REFUSED")) {
            return "Failed Attempt";
        } else if (status.equalsIgnoreCase("DELIVERED")) {
            return "Delivered";
        } else if (status.equalsIgnoreCase("AVAILABLE_FOR_DELIVERY")) {
            return "Available for Pickup";
        } else if (status.equalsIgnoreCase("DELAYED")
                || status.equalsIgnoreCase("DELIVERY_IMPOSSIBLE")
                || status.equalsIgnoreCase("EXPECTED_DELAY")
                || status.equalsIgnoreCase("OTHER")
                || status.equalsIgnoreCase("RETURNED")
                || status.equalsIgnoreCase("Stopped")) {
            return "Exception";
        } else if (status.equalsIgnoreCase("CREATED")) {
            return "Pending";
        }

        return "Expired";
    }

}
