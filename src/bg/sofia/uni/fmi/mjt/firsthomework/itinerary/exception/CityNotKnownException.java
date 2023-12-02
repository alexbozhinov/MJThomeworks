package bg.sofia.uni.fmi.mjt.firsthomework.itinerary.exception;

public class CityNotKnownException extends Exception {
    public CityNotKnownException(String message) {
        super(message);
    }

    public CityNotKnownException(String message, Throwable cause) {
        super(message, cause);
    }
}
