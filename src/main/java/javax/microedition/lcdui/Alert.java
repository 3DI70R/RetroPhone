package javax.microedition.lcdui;

import ru.threedisevenzeror.retrophone.utils.DelegateHolder;

/**
 * An alert is a screen that shows data to the user and waits for a certain period of time
 * before proceeding to the next screen. An alert is an ordinary screen that can contain
 * text (String) and image, and which handles events like other screens.<br/>
 * <br/>
 * The intended use of Alert is to inform the user about errors and other exceptional conditions.<br/>
 * <br/>
 * The application can set the alert time to be infinity with setTimeout(Alert.FOREVER)
 * in which case the Alert is considered to be modal and the implementation provide a
 * feature that allows the user to "dismiss" the alert, whereupon the next screen
 * is displayed as if the timeout had expired immediately.<br/>
 * <br/>
 * If an application specifies an alert to be of a timed variety and gives it too much
 * content such that it must scroll, then it automatically becomes a modal alert.<br/>
 * <br/>
 * An alert may have an AlertType associated with it to provide an indication of
 * the nature of the alert. The implementation may use this type to play an appropriate
 * sound when the Alert is presented to the user. See AlertType.playSound(). <br/>
 * <br/>
 * Alerts do not accept application-defined commands.<br/>
 * <br/>
 * If the Alert is visible on the display when changes to its contents are requested
 * by the application, the changes take place automatically.
 * That is, applications need not take any special action to refresh a Alert's
 * display after its contents have been modified.
 */
public class Alert extends Screen {

    public abstract static class AlertDelegate extends ScreenDelegate {

        void onAlertStringChanged(String oldAlertText, String newAlertText) {
            // noop
        }

        void onAlertImageChanged(Image oldImage, Image newImage) {
            // noop
        }

        void onAlertTypeChanged(AlertType oldType, AlertType newType) {
            // noop
        }

        void onAlertTimeoutChanged(int oldTimeout, int newTimeout) {
            // noop
        }

        @Override
        public Alert getAttachedObject() {
            return (Alert) super.getAttachedObject();
        }
    }

    /**
     * FOREVER indicates that an Alert is kept visible until the user dismisses it.
     * It is used as a value for the parameter to setTimeout() to indicate that the alert is modal.
     * Instead of waiting for a specified period of time, a modal Alert will wait for the user to take some
     * explicit action, such as pressing a button, before proceeding to the next screen.
     */
    public static final int FOREVER = -2;

    private String alertText;
    private Image alertImage;
    private AlertType alertType;
    private int timeout;

    private DelegateHolder<AlertDelegate> delegateHolder;

    /**
     * Constructs a new, empty Alert object with the given title.
     * If null is passed, the Alert will have no title. Calling this constructor is equivalent to calling
     *
     * Alert(title, null, null, null)
     * @param title the title string, or null
     */
    public Alert(String title) {
        this(title, null, null, null);
    }

    /**
     * Constructs a new Alert object with the given title, content string and image, and alert type.
     * The layout of the contents is implementation dependent. The timeout value of this new alert is
     * the same value that is returned by getDefaultTimeout(). If an image is provided it must be immutable.
     * The handling and behavior of specific AlertTypes is described in AlertType.
     * Null is allowed as the value of the alertType parameter and indicates that the Alert
     * is not to have a specific alert type.
     *
     * @param title the title string, or null if there is no title
     * @param alertText the string contents, or null if there is no string
     * @param alertImage the image contents, or null if there is no image
     * @param alertType the type of the Alert, or null if the Alert has no specific type
     */
    public Alert(String title, String alertText, Image alertImage, AlertType alertType) {
        setTitle(title);
        setString(alertText);
        setImage(alertImage);
        setType(alertType);
    }

    /**
     * Gets the default time for showing an Alert.
     * This is either a positive value, which indicates a time in milliseconds, or the special value FOREVER,
     * which indicates that Alerts are modal by default. The value returned will vary across implementations
     * and is presumably tailored to be suitable for each.
     *
     * @return default timeout in milliseconds, or FOREVER
     */
    public int getDefaultTimeout() {
        return FOREVER;
    }

    /**
     * Gets the time this Alert will be shown. This is either a positive value, which indicates a time in milliseconds,
     * or the special value FOREVER, which indicates that this Alert is modal.
     * @return timeout in milliseconds, or FOREVER
     */
    public int getTimeout() {
        return timeout;
    }

    /**
     * Set the time for which the Alert is to be shown.
     * This must either be a positive time value in milliseconds, or the special value FOREVER.
     * @param time timeout in milliseconds, or FOREVER
     *
     * @throws IllegalArgumentException if time is not positive and is not FOREVER
     */
    public void setTimeout(int time) {
        int oldTimeout = this.timeout;
        this.timeout = time;
        delegateHolder.callIfExists(d -> d.onAlertTimeoutChanged(oldTimeout, timeout));
    }

    /**
     * Gets the type of the Alert.
     * @return a reference to an instance of AlertType, or null if the Alert has no specific type
     */
    public AlertType getType() {
        return alertType;
    }

    /**
     * Sets the type of the Alert.
     * The handling and behavior of specific AlertTypes is described in AlertType.
     * @param alertType type - an AlertType, or null if the Alert has no specific type
     */
    public void setType(AlertType alertType) {
        AlertType oldType = this.alertType;
        this.alertType = alertType;
        delegateHolder.callIfExists(d -> d.onAlertTypeChanged(oldType, alertType));
    }

    /**
     * Gets the text string used in the Alert.
     * @return the Alert's text string, or null if there is no text
     */
    public String getString() {
        return alertText;
    }

    /**
     * Sets the text string used in the Alert.
     * @param str the Alert's text string, or null if there is no text
     */
    public void setString(String str) {
        String oldText = this.alertText;
        alertText = str;
        delegateHolder.callIfExists(d -> d.onAlertStringChanged(oldText, alertText));
    }

    /**
     * Gets the Image used in the Alert.
     * @return the Alert's image, or null if there is no image
     */
    public Image getImage() {
        return alertImage;
    }

    /**
     * Sets the Image used in the Alert.
     * @param img the Alert's image, or null if there is no image
     *
     * @throws IllegalArgumentException - if img is mutable
     */
    public void setImage(Image img) {
        if(img != null && img.isMutable()) {
            throw new IllegalArgumentException("Cannot set mutable image to alert");
        }
        Image oldImage = alertImage;
        alertImage = img;
        delegateHolder.callIfExists(d -> d.onAlertImageChanged(oldImage, alertImage));
    }

    /**
     * Commands are not allowed on Alerts, so this method will always throw IllegalStateException whenever it is called.
     *
     * @param cmd the Command
     * @throws IllegalStateException always
     */
    public void addCommand(Command cmd) {
        throw new IllegalArgumentException("Commands are not allowed on Alerts");
    }

    /**
     * Listeners are not allowed on Alerts, so this method will always throw IllegalStateException whenever it is called.
     *
     * @param l the Listener
     * @throws IllegalStateException always
     */
    public void setCommandListener(CommandListener l) {
        throw new IllegalArgumentException("Listeners are not allowed on Alerts");
    }

    public void attachDelegate(AlertDelegate newDelegate) {
        super.attachDelegate(newDelegate);
        delegateHolder.setDelegate(newDelegate);
    }

    @Override
    public void attachDelegate(DisplayableDelegate delegate) {
        delegateHolder.setDelegate(null);
        super.attachDelegate(delegate);
    }

    @Override
    public void attachDelegate(ScreenDelegate delegate) {
        delegateHolder.setDelegate(null);
        super.attachDelegate(delegate);
    }
}

