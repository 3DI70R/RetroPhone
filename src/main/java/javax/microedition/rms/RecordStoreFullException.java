package javax.microedition.rms;

/**
 * Thrown to indicate an operation could not be completed because the record store system storage is full.
 */
public class RecordStoreFullException extends RecordStoreException {

    /**
     * Constructs a new RecordStoreFullException with no detail message.
     */
    public RecordStoreFullException() {
    }

    /**
     * Constructs a new RecordStoreFullException with the specified detail message.
     * @param message the detail message.
     */
    public RecordStoreFullException(String message) {
        super(message);
    }
}

