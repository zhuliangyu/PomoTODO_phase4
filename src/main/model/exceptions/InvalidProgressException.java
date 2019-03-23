package model.exceptions;

public class InvalidProgressException extends IllegalArgumentException {
    /**
     * Constructs an <code>IllegalArgumentException</code> with the
     * specified detail message.
     *
     * @param s the detail message.
     */
    public InvalidProgressException(String s) {
        super(s);
    }
}
