package javax.microedition.rms;

/**
 * Thrown to indicate a general exception occurred in a record store operation.
 */
public class RecordStoreException extends Exception {

    /**
     * Constructs a new RecordStoreException with no detail message.
     */
    public RecordStoreException() {
    }

    /**
     * Constructs a new RecordStoreException with the specified detail message.
     * @param message the detail message.
     */
    public RecordStoreException(String message) {
        super(message);
    }
}

