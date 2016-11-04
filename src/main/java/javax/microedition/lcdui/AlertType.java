package javax.microedition.lcdui;

/**
 * The AlertType provides an indication of the nature of alerts.
 * Alerts are used by an application to present various kinds of information to the user.
 * An AlertType may be used to directly signal the user without changing the current Displayable.
 * The playSound method can be used to spontaneously generate a sound to alert the user.
 * For example, a game using a Canvas can use playSound to indicate success or progress.
 * The predefined types are INFO, WARNING, ERROR, ALARM, and CONFIRMATION.
 */
public class AlertType {

    /**
     * An INFO AlertType typically provides non-threatening information to the user.
     * For example, a simple splash screen might be an INFO AlertType.
     */
    public static final AlertType INFO = new AlertType();

    /**
     * A WARNING AlertType is a hint to warn the user of a potentially dangerous operation.
     * For example, the warning message may contain the message, "Warning: this operation will erase your data."
     */
    public static final AlertType WARNING = new AlertType();

    /**
     * An ERROR AlertType is a hint to alert the user to an erroneous operation.
     * For example, an error alert might show the message, "There is not enough room to install the application."
     */
    public static final AlertType ERROR = new AlertType();

    /**
     * An ALARM AlertType is a hint to alert the user to an event for which the user has previously requested to be notified.
     * For example, the message might say, "Staff meeting in five minutes."
     */
    public static final AlertType ALARM = new AlertType();

    /**
     * A CONFIRMATION AlertType is a hint to confirm user actions.
     * For example, "Saved!" might be shown to indicate that a Save operation has completed.
     */
    public static final AlertType CONFIRMATION = new AlertType();


    protected AlertType() {
    }

    /**
     * Alert the user by playing the sound for this AlertType. The AlertType instance is used as a hint by the device to
     * generate an appropriate sound. Instances other than those predefined above may be ignored.
     * The actual sound made by the device, if any, is determined by the device. The device may ignore the request,
     * use the same sound for several AlertTypes or use any other means suitable to alert the user.
     * @param display to which the AlertType's sound should be played.
     * @return true if the user was alerted, false otherwise.
     *
     * @throws NullPointerException if display is null
     */
    public boolean playSound(Display display) {
        return false;
    }
}

