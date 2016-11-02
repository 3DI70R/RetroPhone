package javax.microedition.rms;

/**
 * A class representing a bidirectional record store Record enumerator.
 * The RecordEnumeration logically maintains a sequence of the recordId's of the records in a record store.
 * The enumerator will iterate over all (or a subset, if an optional record filter has been supplied) of the records
 * in an order determined by an optional record comparator.<br/>
 * <br/>
 * By using an optional RecordFilter, a subset of the records can be chosen that match the supplied filter.
 * This can be used for providing search capabilities.<br/>
 * <br/>
 * By using an optional RecordComparator, the enumerator can index through the records in an order
 * determined by the comparator. This can be used for providing sorting capabilities.<br/>
 * <br/>
 * If, while indexing through the enumeration, some records are deleted from the record store,
 * the recordId's returned by the enumeration may no longer represent valid records.
 * To avoid this problem, the RecordEnumeration can optionally become a listener of the RecordStore and
 * react to record additions and deletions by recreating its internal index.
 * Use special care when using this option however, in that every record addition, change and deletion
 * will cause the index to be rebuilt, which may have serious performance impacts.<br/>
 * <br/>
 * The first call to nextRecord() returns the record data from the first record in the sequence.
 * Subsequent calls to nextRecord() return the next consecutive record's data.
 * To return the record data from the previous consecutive from any given point in the enumeration,
 * call previousRecord(). On the other hand, if after creation, the first call is to previousRecord(),
 * the record data of the last element of the enumeration will be returned.
 * Each subsequent call to previousRecord() will step backwards through the sequence.<br/>
 * <br/>
 * Final note, to do record store searches, create a RecordEnumeration with no RecordComparator,
 * and an appropriate RecordFilter with the desired search criterion.
 */
public interface RecordEnumeration {

    /**
     * Returns the number of records available in this enumeration's set.
     * That is, the number of records that have matched the filter criterion.
     * Note that this forces the RecordEnumeration to fully build the enumeration by applying the filter to all records,
     * which may take a non-trivial amount of time if there are a lot of records in the record store.
     *
     * @return the number of records available in this enumeration's set.
     * That is, the number of records that have matched the filter criterion.
     */
    int numRecords();

    /**
     * Returns a copy of the next record in this enumeration, where next is defined by the comparator
     * and/or filter supplied in the constructor of this enumerator. The byte array returned is a copy of
     * the record. Any changes made to this array will NOT be reflected in the record store. After calling
     * this method, the enumeration is advanced to the next available record.
     *
     * @return the next record in this enumeration.
     *
     * @throws InvalidRecordIDException when no more records are available. Subsequent calls to this method will
     * continue to throw this exception until reset() has been called to reset the enumeration.
     * @throws RecordStoreException if the record store is not open.
     * @throws RecordStoreNotOpenException if a general record store exception occurs.
     */
    byte[] nextRecord() throws InvalidRecordIDException, RecordStoreException, RecordStoreNotOpenException;

    /**
     * Returns the recordId of the next record in this enumeration, where next is defined by the comparator
     * and/or filter supplied in the constructor of this enumerator. After calling this method, the enumeration
     * is advanced to the next available record.
     *
     * @return the recordId of the next record in this enumeration.
     * @throws InvalidRecordIDException - when no more records are available. Subsequent calls to this method will
     * continue to throw this exception until reset() has been called to reset the enumeration.
     */
    int nextRecordId() throws InvalidRecordIDException;

    /**
     * Returns a copy of the previous record in this enumeration, where previous is defined by the comparator
     * and/or filter supplied in the constructor of this enumerator. The byte array returned is a copy of the
     * record. Any changes made to this array will NOT be reflected in the record store. After calling this
     * method, the enumeration is advanced to the next (previous) available record.
     *
     * @return the previous record in this enumeration.
     * @throws InvalidRecordIDException when no more records are available. Subsequent calls to this method will continue
     * to throw this exception until reset() has been called to reset the enumeration.
     * @throws RecordStoreException if the record store is not open.
     * @throws RecordStoreNotOpenException if a general record store exception occurs.
     */
    byte[] previousRecord() throws InvalidRecordIDException, RecordStoreException, RecordStoreNotOpenException;

    /**
     * Returns the recordId of the previous record in this enumeration, where previous is defined by
     * the comparator and/or filter supplied in the constructor of this enumerator. After calling
     * this method, the enumeration is advanced to the next (previous) available record.
     *
     * @return the recordId of the previous record in this enumeration.
     * @throws InvalidRecordIDException when no more records are available. Subsequent calls to this method will
     * continue to throw this exception until reset() has been called to reset the enumeration.
     */
    int previousRecordId() throws InvalidRecordIDException;

    /**
     * Returns true if more elements exist in the next direction.
     * @return true if more elements exist in the next direction.
     */
    boolean hasNextElement();

    /**
     * Returns true if more elements exist in the previous direction.
     * @return true if more elements exist in the previous direction.
     */
    boolean hasPreviousElement();

    /**
     * Returns the enumeration index to the same state as right after the enumeration was created.
     */
    void reset();

    /**
     * Request that the enumeration be updated to reflect the current record set. Useful for when a MIDlet makes a number
     * of changes to the record store, and then wants an existing RecordEnumeration to enumerate the new changes.
     */
    void rebuild();

    /**
     * Used to set whether the enumeration will be keep its internal index up to date with the record
     * store record additions/deletions/changes. Note that this should be used carefully due to the
     * potential performance problems associated with maintaining the enumeration with every change.
     *
     * @param keepUpdated If true, the enumerator will keep its enumeration current with any changes
     *                    in the records of the record store. Use with caution as there are possible
     *                    performance consequences. If false the enumeration will not be kept current
     *                    and may return recordIds for records that have been deleted or miss records
     *                    that are added later. It may also return records out of order that have been
     *                    modified after the enumeration was built. Note that any changes to records
     *                    in the record store are accurately reflected when the record is later retrieved,
     *                    either directly or through the enumeration. The thing that is risked by setting
     *                    this parameter false is the filtering and sorting order of the enumeration
     *                    when records are modified, added, or deleted.
     */
    void keepUpdated(boolean keepUpdated);

    /**
     * Returns true if the enumeration keeps its enumeration current with any changes in the records.
     * @return true if the enumeration keeps its enumeration current with any changes in the records.
     */
    boolean isKeptUpdated();


    /**
     * Frees internal resources used by this RecordEnumeration. MIDlets should call this method when they are done using a
     * RecordEnumeration. If a MIDlet tries to use a RecordEnumeration after this method has been called, it will throw a
     * IllegalStateException.
     */
    void destroy();
}

