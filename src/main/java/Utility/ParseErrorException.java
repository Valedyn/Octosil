package Utility;

public class ParseErrorException extends Exception{
    public ParseErrorException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseErrorException(String message) {
        super(message);
    }

    public ParseErrorException(Throwable cause) {
        super(cause);
    }
}
