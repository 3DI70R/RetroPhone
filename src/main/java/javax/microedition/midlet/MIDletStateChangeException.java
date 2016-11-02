package javax.microedition.midlet;

/**
 * Signals that a requested javax.microedition.midlet.MIDlet state change failed.<br/>
 * This exception is thrown by the javax.microedition.midlet.MIDlet in response to state change calls into
 * the application via the javax.microedition.midlet.MIDlet interface
 */
public class MIDletStateChangeException extends Exception {

    /**
     * Constructs an exception with no specified detail message.
     */
    public MIDletStateChangeException() {
    }

    /**
     * Constructs an exception with the specified detail message.
     * @param s the detail message
     */
    public MIDletStateChangeException(String s) {
        super(s);
    }
}

