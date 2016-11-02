package javax.microedition.rms;

/**
 * Thrown to indicate that an operation was attempted on a closed record store.
 */
public class RecordStoreNotOpenException extends RecordStoreException {

    /**
     * Constructs a new RecordStoreNotOpenException with no detail message.
     */
    public RecordStoreNotOpenException() {
    }

    /**
     * Constructs a new RecordStoreNotOpenException with the specified detail message.
     * @param message the detail message.
     */
    public RecordStoreNotOpenException(String message) {
        super(message);
    }
}

