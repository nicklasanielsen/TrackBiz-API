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
public class GLSTask implements Callable<List<ShipmentDTO>> {

    private final String COURIER_NAME = "GLS";
    private final String API_KEY = "300348be-4f73-48e4-a6e7-cc2f04742924";
    private final String TRACKING_NUMBER;

    public GLSTask(String trackingNumber) {
        TRACKING_NUMBER = trackingNumber;
    }

    @Override
    public List<ShipmentDTO> call() throws Exception {
        HttpURLConnection connection = HttpUtils.openConnection(getUrl());

        try {
            JsonObject jsonObject = HttpUtils.fetchData(connection);
            return convertToShipmentDTO(jsonObject);
        } catch (FetchException e) {
            return new ArrayList();
        } finally {
            connection.disconnect();
        }
    }

    private String getUrl() {
        return "https://gls-group.eu/app/service/open/rest/DK/en/rstt001?match=" + TRACKING_NUMBER;
    }

    private List<ShipmentDTO> convertToShipmentDTO(JsonObject jsonObject) {
        List<ShipmentDTO> shipmentDTOs = new ArrayList();
        ShipmentDTO shipmentDTO;

        String consignor, consignee, originCountry, originCity,
                destinationCountry, destinationCity, volume, weight;

        try {
            JsonArray shipments = jsonObject.getAsJsonArray("tuStatus");

            for (JsonElement object : shipments) {
                JsonObject shipment = object.getAsJsonObject();

                shipmentDTO = new ShipmentDTO(COURIER_NAME, TRACKING_NUMBER);

                // Consignor & Consignee
                consignor = "Unknown";
                consignee = "Unknown";

                shipmentDTO.setConsignor(consignor);
                shipmentDTO.setConsignee(consignee);

                // Origin
                originCountry = "Unknown";
                originCity = "Unknown";

                shipmentDTO.setOriginCountry(originCountry);
                shipmentDTO.setOriginCity(originCity);

                // Destination
                destinationCountry = "Unknown";
                destinationCity = "Unknown";

                shipmentDTO.setDestinationCountry(destinationCountry);
                shipmentDTO.setDestinationCity(destinationCity);

                // Dimensions
                volume = "Unknown";
                weight = getStringValue(shipment.getAsJsonArray("infos").get(0).getAsJsonObject(), "value");

                shipmentDTO.setVolume(volume);
                shipmentDTO.setWeight(weight);

                String status, description, country, city;
                Date timeStamp;
                EventDTO event;

                // Events
                JsonArray events = shipment.getAsJsonArray("history");

                for (JsonElement shippingEvent : events) {
                    JsonObject eventObject = shippingEvent.getAsJsonObject();

                    status = "Pending";
                    description = getStringValue(eventObject, "evtDscr");
                    country = getStringValue(eventObject.getAsJsonObject("address"), "countryName");
                    city = getStringValue(eventObject.getAsJsonObject("address"), "city");
                    timeStamp = getDateValue(eventObject);

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

    private Date getDateValue(JsonObject jsonObject) {
        try {
            String date = getStringValue(jsonObject, "time");
            date += " " + getStringValue(jsonObject, "date");
            return new SimpleDateFormat("HH:mm:ss yyyy-MM-DD").parse(date);
        } catch (ParseException ex) {
            return new Date(0);
        }
    }

}
