package fr.polytech.rmi.server.exception;

/**
 * Exception thrown when user types a wrong pair name, password
 */
public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
