package Utility;

public class WrongAmountOfArgumentsException extends Exception{
    public WrongAmountOfArgumentsException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongAmountOfArgumentsException(String message) {
        super(message);
    }

    public WrongAmountOfArgumentsException(Throwable cause) {
        super(cause);
    }
}
