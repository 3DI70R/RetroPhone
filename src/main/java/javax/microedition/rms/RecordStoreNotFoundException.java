package javax.microedition.rms;

/**
 * Thrown to indicate an operation could not be completed because the record store could not be found.
 */
public class RecordStoreNotFoundException extends RecordStoreException {

    /**
     * Constructs a new RecordStoreNotFoundException with no detail message.
     */
    public RecordStoreNotFoundException() {
    }

    /**
     * Constructs a new RecordStoreNotFoundException with the specified detail message.
     * @param message the detail message.
     */
    public RecordStoreNotFoundException(String message) {
        super(message);
    }
}

