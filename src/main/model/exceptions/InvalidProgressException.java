package model.exceptions;

public class InvalidProgressException extends IllegalArgumentException {
    /**
     * Constructs an <code>IllegalArgumentException</code> with the
     * specified detail message.
     *
     * @param s the detail message.
     */
    /**
     * Constructs an <code>IllegalArgumentException</code> with no
     * detail message.
     */
    public InvalidProgressException() {
    }

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
