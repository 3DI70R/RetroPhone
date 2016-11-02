package javax.microedition.rms;

/**
 * An interface defining a comparator which compares two records (in an implementation-defined manner)
 * to see if they match or what their relative sort order is.
 * The application implements this interface to compare two candidate records.
 * The return value must indicate the ordering of the two records.
 * The compare method is called by RecordEnumeration to sort and return records in an application specified order. For example:<br/>
 * <br/>
 * <code>
 * RecordComparator c = new AddressRecordComparator(); // class implements RecordComparator <br/>
 * if (c.compare(recordStore.getRecord(rec1), recordStore.getRecord(rec2)) == RecordComparator.PRECEDES) <br/>
 * return rec1;</code>
 */
public interface RecordComparator {

    /**
     * PRECEDES means that the left (first parameter) record precedes the right (second parameter) record in terms of search or sort order.
     */
    int PRECEDES = -1;

    /**
     * EQUIVALENT means that in terms of search or sort order, the two records are the same.
     * This does not necessarily mean that the two records are identical.
     */
    int EQUIVALENT = 0;

    /**
     * FOLLOWS means that the left (first parameter) record follows the right (second parameter) record in terms of search or sort order.
     */
    int FOLLOWS = 1;

    /**
     * Returns RecordComparator.PRECEDES if rec1 precedes rec2 in sort order, or RecordComparator.FOLLOWS
     * if rec1 follows rec2 in sort order, or RecordComparator.EQUIVALENT if rec1 and rec2 are equivalent in terms of sort order.
     * @param rec1 The first record to use for comparison. Within this method, the application must treat this parameter as read-only.
     * @param rec2 The second record to use for comparison. Within this method, the application must treat this parameter as read-only.
     *
     * @return RecordComparator.PRECEDES if rec1 precedes rec2 in sort order, or RecordComparator.FOLLOWS if rec1 follows rec2
     * in sort order, or RecordComparator.EQUIVALENT if rec1 and rec2 are equivalent in terms of sort order.
     */
    int compare(byte[] rec1, byte[] rec2);
}

