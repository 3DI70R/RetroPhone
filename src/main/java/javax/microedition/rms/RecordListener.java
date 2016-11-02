package javax.microedition.rms;

/**
 * A listener interface for receiving Record Changed/Added/Deleted events from a record store.
 */
public interface RecordListener {

    /**
     * Called when a record has been added to a record store.
     *
     * @param recordStore the RecordStore in which the record is stored.
     * @param recordId the recordId of the record that has been added.
     */
    void recordAdded(RecordStore recordStore, int recordId);


    /**
     * Called after a record in a record store has been changed.
     * If the implementation of this method retrieves the record, it will receive the changed version.
     *
     * @param recordStore the RecordStore in which the record is stored.
     * @param recordId the recordId of the record that has been changed.
     */
    void recordChanged(RecordStore recordStore, int recordId);

    /**
     * Called after a record has been deleted from a record store.
     * If the implementation of this method tries to retrieve the record from the record store, an
     * InvalidRecordIDException will be thrown.
     *
     * @param recordStore the RecordStore in which the record was stored.
     * @param recordId the recordId of the record that has been deleted.
     */
    void recordDeleted(RecordStore recordStore, int recordId);
}

