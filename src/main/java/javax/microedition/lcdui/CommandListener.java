package javax.microedition.lcdui;

/**
 * This interface is used by applications which need to receive high-level events from the implementation.
 * An application will provide an implementation of a Listener (typically by using a nested class or an inner class)
 * and will then provide an instance of it on a Screen in order to receive high-level events on that screen.
 *
 * The specification does not require the platform to create several threads for the event delivery.
 * Thus, if a Listener method does not return or the return is not delayed,
 * the system may be blocked. So, there is the following note to application developers:
 *
 * - the Listener method should return immediately.
 */
public interface CommandListener {

    /**
     * Indicates that a command event has occurred on Displayable d.
     *
     * @param c a Command object identifying the command.
     *          This is either one of the applications have been added to Displayable
     *          with addCommand(Command) or is the implicit SELECT_COMMAND of List.
     * @param d the Displayable on which this event has occurred
     */
    void commandAction(Command c, Displayable d);
}

