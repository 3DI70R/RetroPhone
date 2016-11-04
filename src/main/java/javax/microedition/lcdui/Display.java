package javax.microedition.lcdui;

import ru.threedisevenzeror.retrophone.DisplayDevice;
import ru.threedisevenzeror.retrophone.RetroDevice;

import javax.microedition.midlet.MIDlet;

/**
 * Display represents the manager of the display and input devices of the system.
 * It includes methods for retrieving properties of the device and for requesting that objects be displayed on the device.
 * Other methods that deal with device attributes are primarily used with Canvas objects and are thus defined there instead of here.<br/>
 * <br/>
 * There is exactly one instance of Display per MIDlet and the application can get a reference to that instance
 * by calling the getDisplay() method. The application may call the getDisplay() method from the beginning of
 * the startApp() call until the destroyApp() call returns. The Display object returned by all calls to
 * getDisplay() will remain the same during this time.<br/>
 * <br/>
 * A typical application will perform the following actions in response to calls to its MIDlet methods:<br/>
 * startApp - the application is moving from the paused state to the active state.
 * Initialization of objects needed while the application is active should be done.
 * The application may call setCurrent() for the first screen if that has not already been done.
 * Note that startApp() can be called several times if pauseApp() has been called in between.
 * This means that one-time initialization should not take place here but instead should occur within the MIDlet's constructor.<br/>
 * <br/>
 * pauseApp - the application may pause its threads. Also, if it is desirable to start with another screen when the
 * application is re-activated, the new screen should be set with setCurrent().<br/>
 * destroyApp - the application should free resources, terminate threads, etc. The behavior of method calls on
 * user interface objects after destroyApp() has returned is undefined.<br/>
 * <br/>
 * The user interface objects that are shown on the display device are contained within a Displayable object.
 * At any time the application may have at most one Displayable object that it intends to be shown on the display
 * device and through which user interaction occurs. This Displayable is referred to as the current Displayable.<br/>
 * <br/>
 * The Display class has a setCurrent() method for setting the current Displayable and a getCurrent()
 * method for retrieving the current Displayable. The application has control over its current Displayable
 * and may call setCurrent() at any time. Typically, the application will change the current Displayable
 * in response to some user action. This is not always the case, however. Another thread may change the current
 * Displayable in response to some other stimulus. The current Displayable will also be changed when the timer for an Alert elapses.<br/>
 * <br/>
 * The application's current Displayable may not physically be drawn on the screen, nor will user
 * events (such as keystrokes) that occur necessarily be directed to the current Displayable.
 * This may occur because of the presence of other MIDlet applications running simultaneously on the same device.<br/>
 * <br/>
 * An application is said to be in the foreground if its current Displayable is actually visible on the
 * display device and if user input device events will be delivered to it. If the application is not in
 * the foreground, it lacks access to both the display and input devices, and it is said to be in the background.
 * The policy for allocation of these devices to different MIDlet applications is outside the scope of this
 * specification and is under the control of an external agent referred to as the application management software.<br/>
 * <br/>
 * As mentioned above, the application still has a notion of its current Displayable even if it is in the background.
 * The current Displayable is significant, even for background applications, because the current Displayable is always
 * the one that will be shown the next time the application is brought into the foreground. The application can determine
 * whether a Displayable is actually visible on the display by calling isShown(). In the case of Canvas, the showNotify()
 * and hideNotify() methods are called when the Canvas is made visible and is hidden, respectively.<br/>
 * <br/>
 * Each MIDlet application has its own current Displayable. This means that the getCurrent() method returns
 * the MIDlet's current Displayable, regardless of the MIDlet's foreground/background state. For example,
 * suppose a MIDlet running in the foreground has current Displayable F, and a MIDlet running in the background
 * has current Displayable B. When the foreground MIDlet calls getCurrent(), it will return F, and when the
 * background MIDlet calls getCurrent(), it will return B. Furthermore, if either MIDlet changes its current Displayable
 * by calling setCurrent(), this will not affect the any other MIDlet's current Displayable.<br/>
 * <br/>
 * It is possible for getCurrent() to return null. This may occur at startup time, before the MIDlet
 * application has called setCurrent() on its first screen. The getCurrent() method will never return
 * a reference to a Displayable object that was not passed in a prior call to setCurrent() call by this MIDlet.<br/>
 * <br/>
 * System Screens<br/>
 * <br/>
 * Typically, the current screen of the foreground MIDlet will be visible on the display.
 * However, under certain circumstances, the system may create a screen that temporarily obscures the application's
 * current screen. These screens are referred to as system screens. This may occur if the system needs to show a menu
 * of commands or if the system requires the user to edit text on a separate screen instead of within a text field
 * inside a Form. Even though the system screen obscures the application's screen, the notion of the current screen
 * does not change. In particular, while a system screen is visible, a call to getCurrent() will return the
 * application's current screen, not the system screen. The value returned by isShown() is false while the
 * current Displayable is obscured by a system screen.<br/>
 * <br/>
 * If system screen obscures a canvas, its hideNotify() method is called. When the system screen is removed,
 * restoring the canvas, its showNotify() method and then its paint() method are called. If the system
 * screen was used by the user to issue a command, the commandAction() method is called after showNotify() is called.
 */
public class Display {

    public abstract static class DisplayImpl {

        public void setCurrent(Displayable nextDisplayable) {

        }

        public void setCurrent(Alert alert, Displayable nextDisplayable) {

        }

        public void callSerially() {

        }
    }

    private Displayable currentDisplayable;
    private DisplayImpl impl;
    private DisplayDevice displayDevice;

    public Display() {
        displayDevice = RetroDevice.getInstance().getDisplay();
        impl = displayDevice.getDisplayImpl();
    }

    /**
     * Gets information about color support of the device.
     * @return true if the display supports color, false otherwise
     */
    public boolean isColor() {
        return displayDevice.isColor();
    }

    /**
     * Gets the number of colors (if isColor() is true) or graylevels (if isColor() is false) that can be represented on the device.<br/>
     * <br/>
     * Note that number of Colors for black and white display is 2.
     * @return number of colors
     */
    public int numColors() {
        return displayDevice.getColorCount();
    }

    /**
     * Gets the current Displayable object for this MIDlet. The Displayable object returned may not actually
     * be visible on the display if the MIDlet is running in the background, or if the Displayable is obscured
     * by a system screen. The Displayable.isShown() method may be called to determine whether the Displayable
     * is actually visible on the display.<br/>
     * <br/>
     * The value returned by getCurrent() may be null. This occurs after the application has been initialized but before the first call to setCurrent().
     * @return the MIDlet's current Displayable object
     */
    public Displayable getCurrent() {
        return currentDisplayable;
    }

    /**
     * Requests that a different Displayable object be made visible on the display.
     * The change will typically not take effect immediately. It may be delayed so that it occurs between event delivery
     * method calls, although it is not guaranteed to occur before the next event delivery method is called.
     * The setCurrent() method returns immediately, without waiting for the change to take place. Because of this delay,
     * a call to getCurrent() shortly after a call to setCurrent() is unlikely to return the value passed to setCurrent().<br/>
     * <br/>
     * Calls to setCurrent() are not queued. A delayed request made by a setCurrent()
     * call may be superseded by a subsequent call to setCurrent().
     * For example, if screen S1 is current, then <br/>
     * <br/>
     * d.setCurrent(S2);<br/>
     * d.setCurrent(S3);<br/>
     * <br/>
     * may eventually result in S3 being made current, bypassing S2 entirely.<br/>
     * <br/>
     * When a MIDlet application is first started, there is no current Displayable object.
     * It is the responsibility of the application to ensure that a Displayable is visible and can interact
     * with the user at all times. Therefore, the application should always call setCurrent() as part of its initialization.<br/>
     * <br/>
     * The application may pass null as the argument to setCurrent(). This does not have the effect of setting
     * the current Displayable to null; instead, the current Displayable remains unchanged. However, the application
     * management software may interpret this call as a hint from the application that it is requesting to be placed
     * into the background. Similarly, if the application is in the background, passing a non-null reference to
     * setCurrent() may be interpreted by the application management software as a hint that the application is
     * requesting to be brought to the foreground. The request should be considered to be made even if the current
     * Displayable is passed to the setCurrent(). For example, the code<br/>
     * <br/>
     * d.setCurrent(d.getCurrent());<br/>
     * <br/>
     * generally will have no effect other than requesting that the application be brought to the foreground.
     * These requests are only hints, and there is no requirement that the application management software
     * comply with these requests in a timely fashion if at all.<br/>
     * <br/>
     * If the Displayable passed to setCurrent() is an Alert, the previous Displayable is restored after
     * the Alert has been dismissed. The effect is as if setCurrent(Alert, getCurrent()) had been called.
     * Note that this will result in an exception being thrown if the current Displayable is already an alert.
     * To specify the Displayable to be shown after an Alert is dismissed, the application should use the
     * setCurrent(Alert, Displayable) method. If the application calls setCurrent() while an Alert is current,
     * the Alert is removed from the display and any timer it may have set is cancelled.<br/>
     * <br/>
     * If the application calls setCurrent() while a system screen is active, the effect may be delayed until
     * after the system screen is dismissed. The implementation may choose to interpret setCurrent() in such a
     * situation as a request to cancel the effect of the system screen, regardless of whether setCurrent() has
     * been delayed.<br/>
     * @param nextDisplayable  the Displayable requested to be made current; null is allowed
     */
    public void setCurrent(final Displayable nextDisplayable) {
        impl.setCurrent(nextDisplayable);
        currentDisplayable = nextDisplayable;
    }

    /**
     * Requests that this Alert be made current, and that nextDisplayable be made current after the Alert is dismissed.
     * This call returns immediately regardless of the Alert's timeout value or whether it is a modal alert.
     * The nextDisplayable must not be an Alert, and it must not be null.<br/>
     * <br/>
     * In other respects, this method behaves identically to setCurrent(Displayable).
     * @param alert the alert to be shown
     * @param nextDisplayable the Displayable to be shown after this alert is dismissed
     *
     * @throws NullPointerException if alert or nextDisplayable is null
     * @throws IllegalArgumentException if nextDisplayable is an Alert
     */
    public void setCurrent(Alert alert, final Displayable nextDisplayable) {
        if(alert == null || nextDisplayable == null) {
            throw new NullPointerException("Alert and Displayable should not be null");
        }

        if(nextDisplayable instanceof Alert) {
            throw new IllegalArgumentException("Next displayable should not be an Alert");
        }

        impl.setCurrent(alert, nextDisplayable);
        currentDisplayable = alert;
    }

    public void callSerially(Runnable runnable) {
        impl.callSerially();
    }

    /**
     * Gets the Display object that is unique to this MIDlet.
     * @param m Midlet of the application
     * @return the display object that application can use for its user interface
     * @throws NullPointerException if m is null
     */
    public static Display getDisplay(MIDlet m) {

        if(m == null) {
            throw new NullPointerException("Cannot get display for null midlet instance");
        }

        return new Display();
    }
}

