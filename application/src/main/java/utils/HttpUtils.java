package utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import errorhandling.exceptions.FetchException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 *
 * @author Nicklas Nielsen
 */
public class HttpUtils {

    public static HttpURLConnection openConnection(String _URL) throws FetchException {
        URL URL;
        HttpURLConnection connection;

        try {
            // Creating URL
            URL = new URL(_URL);

            // Creating connection
            connection = (HttpURLConnection) URL.openConnection();

            // Setting Headers
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("User-Agent", "server");

            return connection;
        } catch (IOException e) {
            throw new FetchException("An error occurred while establishing a connection to one of our partners, please let us know about this error, or try again later.", 503);
        }
    }

    public static HttpURLConnection addRequestProperty(HttpURLConnection connection, String propertyKey, String propertyValue) {
        connection.addRequestProperty(propertyKey, propertyValue);
        return connection;
    }

    public static JsonObject fetchData(HttpURLConnection connection) throws FetchException {
        Scanner scan;
        String jsonStr = "";

        try {
            scan = new Scanner(connection.getInputStream(), "UTF-8");

            while (scan.hasNext()) {
                jsonStr += scan.nextLine();
            }

            return new Gson().fromJson(jsonStr, JsonObject.class);
        } catch (IOException e) {
            throw new FetchException("An error occurred while trying to track your shipment, please let us know about this error.", 500);
        }
    }

}
