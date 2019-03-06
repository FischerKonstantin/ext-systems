package edu.javacourse.city.exception;

public class PersonChecException extends Exception
{
    public PersonChecException() {
    }

    public PersonChecException(String message) {
        super(message);
    }

    public PersonChecException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersonChecException(Throwable cause) {
        super(cause);
    }

    public PersonChecException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
