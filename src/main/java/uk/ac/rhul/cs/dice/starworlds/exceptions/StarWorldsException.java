package uk.ac.rhul.cs.dice.starworlds.exceptions;

public class StarWorldsException extends Exception {
    private static final long serialVersionUID = 4672616551750877117L;

    public StarWorldsException() {
	super();
    }

    public StarWorldsException(String message) {
	super(message);
    }

    public StarWorldsException(Throwable cause) {
	super(cause);
    }

    public StarWorldsException(String message, Throwable cause) {
	super(message, cause);
    }

    public StarWorldsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }
}