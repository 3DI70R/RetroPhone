package javax.microedition.rms;

/**
 * An interface defining a filter which examines a record to see if it matches (based on an application-defined criteria).
 * The application implements the match() method to select records to be returned by the RecordEnumeration.
 * Returns true if the candidate record is selected by the RecordFilter. This interface is used in the record store
 * for searching or subsetting records. For example:<br/>
 * <br/>
 * <code>
 * RecordFilter f = new DateRecordFilter(); // class implements RecordFilter<br/>
 * if (f.matches(recordStore.getRecord(theRecordID)) == true)<br/>
 * DoSomethingUseful(theRecordID);</code>
 */
public interface RecordFilter {

    /**
     * Returns true if the candidate matches the implemented criterion.
     *
     * @param candidate The record to consider. Within this method, the application must treat this parameter as read-only.
     * @return true if the candidate matches the implemented criterion.
     */
    boolean matches(byte[] candidate);
}

