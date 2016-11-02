package javax.microedition.rms;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a record store. A record store consists of a collection of records
 * which will remain persistent across multiple invocations of the MIDlet. The platform is
 * responsible for making its best effort to maintain the integrity of the MIDlet's record
 * stores throughout the normal use of the platform, including reboots, battery changes, etc.<br/>
 * <br/>
 * Record stores are created in platform-dependent locations, which are not exposed to the MIDlets.
 * The naming space for record stores is controlled at the MIDlet suite granularity. MIDlets within a
 * MIDlet suite are allowed to create multiple record stores, as long as they are each given different
 * names. When a MIDlet suite is removed from a platform all the record stores associated with its
 * MIDlets will also be removed. These APIs only allow the manipulation of the MIDlet suite's own record stores,
 * and does not provide any mechanism for record sharing between MIDlets in different MIDlet suites. MIDlets
 * within a MIDlet suite can access each other's record stores directly.<br/>
 * <br/>
 * Record store names are case sensitive and may consist of any combination of up to
 * 32 Unicode characters. Record store names must be unique within the scope of a given
 * MIDlet suite. In other words, a MIDlets within a MIDlet suite are is not allowed to
 * create more than one record store with the same name, however a MIDlet in different
 * one MIDlet suites are is allowed to each have a record store with the same name as a
 * MIDlet in another MIDlet suite. In that case, the record stores are still distinct and separate.<br/>
 * <br/>
 * No locking operations are provided in this API. Record store implementations ensure
 * that all individual record store operations are atomic, synchronous, and serialized,
 * so no corruption will occur with multiple accesses. However, if a MIDlet uses multiple
 * threads to access a record store, it is the MIDlet's responsibility to coordinate
 * this access or unintended consequences may result. Similarly, if a platform performs
 * transparent synchronization of a record store, it is the platform's responsibility
 * to enforce exclusive access to the record store between the MIDlet and synchronization engine.<br/>
 * <br/>
 * Records are uniquely identified within a given record store by their recordId,
 * which is an integer value. This recordId is used as the primary key for the records.
 * The first record created in a record store will have recordId equal to one (1).
 * Each subsequent record added to a RecordStore will be assigned a recordId one
 * greater than the record added before it. That is, if two records are added to a
 * record store, and the first has a recordId of 'n', the next will have a recordId of 'n + 1'.
 * MIDlets can create other indices by using the RecordEnumeration class.<br/>
 * <br/>
 * This record store uses long integers for time/date stamps, in the format used by
 * System.currentTimeMillis(). The record store is time stamped with the last time it was modified.
 * The record store also maintains a version, which is an integer that is incremented for each
 * operation that modifies the contents of the RecordStore. These are useful for
 * synchronization engines as well as other things.
 */
public class RecordStore {

    private List<RecordListener> recordListeners;
    private boolean isOpened;
    private int version;
    private long lastModified;
    private String name;

    private RecordStore(String name) {
        this.recordListeners = new ArrayList<RecordListener>();
        this.isOpened = true;
        this.version = 1;
        this.name = name;
    }

    /**
     * This method is called when the MIDlet requests to have the record store closed.
     * Note that the record store will not actually be closed until closeRecordStore() is called as many times
     * as openRecordStore() was called. In other words, the MIDlet needs to make a balanced number of
     * close calls as open calls before the record store is closed.<br/>
     * <br/>
     * When the record store is closed, all listeners are removed. If the MIDlet attempts to perform operations
     * on the RecordStore object after it has been closed, the methods will throw a RecordStoreNotOpenException.
     *
     * @throws RecordStoreException if a different record store-related
     * @throws RecordStoreNotOpenException if the record store is not open.
     */
    public void closeRecordStore() throws RecordStoreException, RecordStoreNotOpenException {
        isOpened = false;
        recordListeners.clear();
    }

    /**
     * Returns the name of this RecordStore.
     * @return the name of this RecordStore.
     * @throws RecordStoreNotOpenException if the record store is not open.
     */
    public String getName() throws RecordStoreNotOpenException {
        if(isOpened) {
            return name;
        } else {
            throw new RecordStoreNotOpenException();
        }
    }

    /**
     * Each time a record store is modified (record added, modified, deleted), it's version is incremented.
     * This can be used by MIDlets to quickly tell if anything has been modified.
     * The initial version number is implementation dependent. The increment is a positive integer greater
     * than 0. The version number only increases as the RecordStore is updated.
     *
     * @return the current record store version.
     * @throws RecordStoreNotOpenException if the record store is not open.
     */
    public int getVersion() throws RecordStoreNotOpenException {
        if(isOpened) {
            return version;
        } else {
            throw new RecordStoreNotOpenException();
        }
    }

    /**
     * Returns the number of records currently in the record store.
     * @return the number of records currently in the record store.
     * @throws RecordStoreNotOpenException if the record store is not open.
     */
    public int getNumRecords() throws RecordStoreNotOpenException {
        if(isOpened) {
            return 0;
        } else {
            throw new RecordStoreNotOpenException();
        }
    }

    /**
     * Returns the amount of space, in bytes, that the record store occupies.
     * The size returned includes any overhead associated with the implementation,
     * such as the data structures used to hold the state of the record store, etc.
     *
     * @return the size of the record store in bytes.
     * @throws RecordStoreNotOpenException if the record store is not open.
     */
    public int getSize() throws RecordStoreNotOpenException {

        if(isOpened) {
            return 0;
        } else {
            throw new RecordStoreNotOpenException();
        }
    }

    /**
     * Returns the amount of additional room (in bytes) available for this record store to grow.
     * Note that this is not necessarily the amount of extra MIDlet-level data which can be stored,
     * as implementations may store additional data structures with each record to support integration
     * with native applications, synchronization, etc.
     *
     * @return the amount of additional room (in bytes) available for this record store to grow.
     * @throws RecordStoreNotOpenException if the record store is not open.
     */
    public int getSizeAvailable() throws RecordStoreNotOpenException {
        if(isOpened) {
            return Integer.MAX_VALUE;
        } else {
            throw new RecordStoreNotOpenException();
        }
    }

    /**
     * Returns the last time the record store was modified, in the format used by System.currentTimeMillis().
     * @return the last time the record store was modified, in the format used by System.currentTimeMillis().
     * @throws RecordStoreNotOpenException if the record store is not open.
     */
    public long getLastModified() throws RecordStoreNotOpenException {
        if(isOpened) {
            return lastModified;
        } else {
            throw new RecordStoreNotOpenException();
        }
    }

    /**
     * Adds the specified RecordListener. If the specified listener is already registered, it will not be added a second time.
     * When a record store is closed, all listeners are removed.
     * @param listener the RecordChangedListener.
     */
    public void addRecordListener(RecordListener listener) {
        if(isOpened) {
            if (!recordListeners.contains(listener)) {
                recordListeners.add(listener);
            }
        }
    }

    /**
     * Removes the specified RecordListener. If the specified listener is not registered, this method does nothing.
     * @param listener the RecordChangedListener.
     */
    public void removeRecordListener(RecordListener listener) {
        if(isOpened) {
            recordListeners.remove(listener);
        }
    }

    /**
     * Returns the recordId of the next record to be added to the record store. This can be useful for setting up
     * pseudo-relational relationships. That is, if you have two or more record stores whose records need to
     * refer to one another, you can predetermine the recordIds of the records that will be created in one record store,
     * before populating the fields and allocating the record in another record store. Note that the recordId returned
     * is only valid while the record store remains open and until a call to addRecord().
     *
     * @return the recordId of the next record to be added to the record store.
     * @throws RecordStoreException if a different record store-related exception occurred.
     * @throws RecordStoreNotOpenException if the record store is not open.
     */
    public int getNextRecordID() throws RecordStoreException, RecordStoreNotOpenException {
        return 0;
    }

    /**
     * Adds a new record to the record store. The recordId for this new record is returned. This is a blocking atomic operation.
     * The record is written to persistent storage before the method returns.
     *
     * @param data The data to be stored in this record. If the record is to have zero-length data (no data), this parameter may be null.
     * @param offset The index into the data buffer of the first relevant byte for this record.
     * @param numBytes The number of bytes of the data buffer to use for this record (may be zero).
     * @return the recordId for the new record.
     *
     * @throws RecordStoreException if a different record store-related exception occurred.
     * @throws RecordStoreFullException if the operation cannot be completed because the record store has no more room.
     * @throws RecordStoreNotOpenException if the record store is not open.
     */
    public int addRecord(byte[] data, int offset, int numBytes)
            throws RecordStoreException, RecordStoreFullException, RecordStoreNotOpenException {
        return 0;
    }

    /**
     * The record is deleted from the record store. The recordId for this record is NOT reused.
     *
     * @param recordId The ID of the record to delete.
     *
     * @throws InvalidRecordIDException if the recordId is invalid.
     * @throws RecordStoreException if a general record store exception occurs.
     * @throws RecordStoreNotOpenException if the record store is not open.
     */
    public void deleteRecord(int recordId) throws InvalidRecordIDException,
            RecordStoreException, RecordStoreNotOpenException {
    }

    /**
     * Returns the size (in bytes) of the MIDlet data available in the given record.
     *
     * @param recordId The ID of the record to use in this operation.
     * @return the size (in bytes) of the MIDlet data available in the given record.
     *
     * @throws InvalidRecordIDException if the recordId is invalid.
     * @throws RecordStoreException if a general record store exception occurs.
     * @throws RecordStoreNotOpenException if the record store is not open.
     */
    public int getRecordSize(int recordId) throws InvalidRecordIDException,
            RecordStoreException, RecordStoreNotOpenException {
        return 0;
    }

    /**
     * Returns the data stored in the given record.
     *
     * @param recordId The ID of the record to use in this operation.
     * @param buffer The byte array in which to copy the data.
     * @param offset The index into the buffer in which to start copying.
     * @return the number of bytes copied into the buffer, starting at index offset.
     *
     * @throws InvalidRecordIDException if the recordId is invalid.
     * @throws RecordStoreException if a general record store exception occurs.
     * @throws RecordStoreNotOpenException if the record store is not open.
     * @throws ArrayIndexOutOfBoundsException if the record is larger than the buffer supplied.
     */
    public int getRecord(int recordId, byte[] buffer, int offset)
            throws InvalidRecordIDException, RecordStoreException, RecordStoreNotOpenException {
        return 0;
    }

    /**
     * Returns a copy of the data stored in the given record.
     *
     * @param recordId The ID of the record to use in this operation.
     * @return the data stored in the given record. Note that if the record has no data, this method will return null.
     *
     * @throws InvalidRecordIDException if the recordId is invalid.
     * @throws RecordStoreException if a general record store exception occurs.
     * @throws RecordStoreNotOpenException if the record store is not open.
     */
    public byte[] getRecord(int recordId) throws InvalidRecordIDException,
            RecordStoreException, RecordStoreNotOpenException {
        return null;
    }

    /**
     * Returns an enumeration for traversing a set of records in the record store in an optionally specified order.
     * The filter, if non-null, will be used to determine what subset of the record store records will be used.
     *
     * The comparator, if non-null, will be used to determine the order in which the records are returned.
     *
     * If both the filter and comparator are null, the enumeration will traverse all records in the record
     * store in an undefined order. This is the most efficient way to traverse all of the records in a record store.
     *
     * The first call to RecordEnumeration.nextRecord() returns the record data from the first record in the sequence.
     * Subsequent calls to RecordEnumeration.nextRecord() return the next consecutive record's data.
     * To return the record data from the previous consecutive from any given point in the enumeration,
     * call previousRecord(). On the other hand, if after creation the first call is to previousRecord(),
     * the record data of the last element of the enumeration will be returned. Each subsequent call to
     * previousRecord() will step backwards through the sequence.
     *
     * @param filter if non-null, will be used to determine what subset of the record store records will be used.
     * @param comparator if non-null, will be used to determine the order in which the records are returned.
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
     * @return an enumeration for traversing a set of records in the record store in an optionally specified order.
     * @throws RecordStoreNotOpenException if the record store is not open.
     */
    public RecordEnumeration enumerateRecords(RecordFilter filter, RecordComparator comparator, boolean keepUpdated)
            throws RecordStoreNotOpenException {
        return new RecordEnumeration() {
            public int numRecords() {
                return 0;
            }

            public byte[] nextRecord() throws InvalidRecordIDException, RecordStoreException, RecordStoreNotOpenException {
                return new byte[0];
            }

            public int nextRecordId() throws InvalidRecordIDException {
                return 0;
            }

            public byte[] previousRecord() throws InvalidRecordIDException, RecordStoreException, RecordStoreNotOpenException {
                return new byte[0];
            }

            public int previousRecordId() throws InvalidRecordIDException {
                return 0;
            }

            public boolean hasNextElement() {
                return false;
            }

            public boolean hasPreviousElement() {
                return false;
            }

            public void reset() {

            }

            public void rebuild() {

            }

            public void keepUpdated(boolean keepUpdated) {

            }

            public boolean isKeptUpdated() {
                return false;
            }

            public void destroy() {

            }
        };
    }

    /**
     * Sets the data in the given record to that passed in. After this method returns, a call to getRecord(int recordId)
     * will return an array of numBytes size containing the data supplied here.
     *
     * @param recordId The ID of the record to use in this operation.
     * @param newData The new data to store in the record.
     * @param offset The index into the data buffer of the first relevant byte for this record.
     * @param numBytes The number of bytes of the data buffer to use for this record.
     *
     * @throws InvalidRecordIDException if the recordId is invalid.
     * @throws RecordStoreException if a general record store exception occurs.
     * @throws RecordStoreFullException if the operation cannot be completed because the record store has no more room.
     * @throws RecordStoreNotOpenException if the record store is not open.
     */
    public void setRecord(int recordId, byte[] newData, int offset, int numBytes)
            throws InvalidRecordIDException, RecordStoreException, RecordStoreFullException, RecordStoreNotOpenException {
    }

    /**
     * Deletes the named record store. MIDlet suites are only allowed to operate on their own record stores,
     * including deletions. If the record store is currently open by a MIDlet when this method is called,
     * or if the named record store does not exist, a RecordStoreException will be thrown.
     *
     * @param recordStoreName if a record store-related exception occurred.
     *
     * @throws RecordStoreException if a record store-related exception occurred.
     * @throws RecordStoreNotFoundException if the record store could not be found.
     */
    public static void deleteRecordStore(String recordStoreName)
            throws RecordStoreException, RecordStoreNotFoundException {
    }

    /**
     * Open (and possibly create) a record store associated with the given MIDlet suite.
     * If this method is called by a MIDlet when the record store is already open by a MIDlet in the
     * MIDlet suite, this method returns a reference to the same RecordStore object.
     *
     * @param recordStoreName The MIDlet suite unique name, not to exceed 32 characters, of the record store.
     * @param createIfNecessary If true, the record store will be created if necessary.
     * @return The RecordStore object for the record store.
     *
     * @throws RecordStoreException if a record store-related exception occurred.
     * @throws RecordStoreFullException if the record store could not be found.
     * @throws RecordStoreNotFoundException if the operation cannot be completed because the record store is full.
     */
    public static RecordStore openRecordStore(String recordStoreName, boolean createIfNecessary)
            throws RecordStoreException, RecordStoreFullException, RecordStoreNotFoundException {
        return new RecordStore(recordStoreName); // TODO: Implementation
    }

    /**
     * Returns an array of the names of record stores owned by the MIDlet suite.
     * Note that if the MIDlet suite does not have any record stores, this function will return NULL.
     * The order of RecordStore names returned is implementation dependent.
     *
     * @return an array of the names of record stores owned by the MIDlet suite.
     * Note that if the MIDlet suite does not have any record stores,
     * this function will return NULL.
     */
    public static String[] listRecordStores() {
        return null;
    }
}

