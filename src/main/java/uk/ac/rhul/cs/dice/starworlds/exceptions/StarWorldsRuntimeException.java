package uk.ac.rhul.cs.dice.starworlds.exceptions;

public class StarWorldsRuntimeException extends RuntimeException {
    private static final long serialVersionUID = -495733219752639842L;

    public StarWorldsRuntimeException() {
	super();
    }

    public StarWorldsRuntimeException(String message) {
	super(message);
    }

    public StarWorldsRuntimeException(Throwable cause) {
	super(cause);
    }

    public StarWorldsRuntimeException(String message, Throwable cause) {
	super(message, cause);
    }

    public StarWorldsRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }
}