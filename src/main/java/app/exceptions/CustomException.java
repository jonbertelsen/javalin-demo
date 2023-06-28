package app.exceptions;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomException extends Exception {

    public CustomException(String message) {
        super(message);
        Logger.getLogger("web").log(Level.SEVERE, message);
    }
    public CustomException(Exception ex, String message) {
        super(message);
        Logger.getLogger("web").log(Level.SEVERE, message, ex.getMessage());
    }
}
