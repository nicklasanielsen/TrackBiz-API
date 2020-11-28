package errorhandling.exceptions;

/**
 *
 * @author Nicklas Nielsen
 */
public class NoShipmentsFoundException extends Exception {

    private int errorCode = 400;

    public NoShipmentsFoundException(String message) {
        super("No shipments found for " + message + ".");
    }

    public int getErrorCode() {
        return errorCode;
    }

}
