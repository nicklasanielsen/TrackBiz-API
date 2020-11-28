package errorhandling.exceptions;

/**
 *
 * @author Nicklas Nielsen
 */
public class UnsupportedCourierException extends Exception {

    public UnsupportedCourierException(String courier) {
        super(courier + " isn't supported");
    }

}
