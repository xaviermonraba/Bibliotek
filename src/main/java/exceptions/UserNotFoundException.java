package exceptions;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String userId) {
        super("The user with ID " + userId + " was not found.");
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
