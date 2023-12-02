package bg.sofia.uni.fmi.mjt.firsthomework.itinerary.exception;

public class NoPathToDestinationException extends Exception {
    public NoPathToDestinationException(String message) {
        super(message);
    }

    public NoPathToDestinationException(String message, Throwable cause) {
        super(message, cause);
    }
}
