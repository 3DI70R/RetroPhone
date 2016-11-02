package javax.microedition.rms;

/**
 * Thrown to indicate an operation could not be completed because the record ID was invalid.
 */
public class InvalidRecordIDException extends RecordStoreException {

    /**
     * Constructs a new InvalidRecordIDException with no detail message.
     */
    public InvalidRecordIDException() {
    }

    /**
     * Constructs a new InvalidRecordIDException with the specified detail message.
     * @param message the detail message.
     */
    public InvalidRecordIDException(String message) {
        super(message);
    }
}

