package javax.microedition.lcdui;

import ru.threedisevenzeror.retrophone.utils.ComponentDelegate;
import ru.threedisevenzeror.retrophone.utils.DelegateHolder;

/**
 * The common superclass of all high-level user interface classes.
 * Adds optional title and ticker-tape output to the Displayable class. The contents displayed and their interaction
 * with the user are defined by subclasses.<br/>
 * <br/>
 * Using subclass-defined methods, the application may change the contents of a Screen object
 * while it is shown to the user. If this occurs, and the Screen object is visible, the display will
 * be updated automatically. That is, the implementation will refresh the display in a timely fashion
 * without waiting for any further action by the application. For example, suppose a
 * List object is currently displayed, and every element of the List is visible. If the application
 * inserts a new element at the beginning of the List, it is displayed immediately, and the other
 * elements will be rearranged appropriately. There is no need for the application to call another
 * method to refresh the display.<br/>
 * <br/>
 * It is recommended that applications change the contents of a Screen only while it is not visible
 * (that is, while another Displayable is current). Changing the contents of a Screen while it is visible
 * may result in performance problems on some devices, and it may also be confusing
 * if the Screen's contents changes while the user is interacting with it.
 */
public abstract class Screen extends Displayable {

    public static abstract class ScreenDelegate extends ComponentDelegate<Screen> {
        public abstract void onTitleChanged(String oldTitle, String newTitle);
        public abstract void onTickerChanged(Ticker oldTicker, Ticker newTicker);
    }

    private String title;
    private Ticker ticker;
    private final DelegateHolder<ScreenDelegate, Screen> delegateHolder;

    public Screen() {
        delegateHolder = new DelegateHolder<ScreenDelegate, Screen>(this);
    }

    /**
     * Gets the title of the Screen. Returns null if there is no title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the Screen. If null is given, removes the title.
     * If the Screen is physically visible, the visible effect should take place no later
     * than immediately after the callback or startApp returns back to the implementation.
     * @param s the new title, or null for no title
     */
    public void setTitle(String s) {
        String oldTitle = title;
        title = s;

        ScreenDelegate delegate = delegateHolder.getDelegate();
        if(delegate != null) {
            delegate.onTitleChanged(oldTitle, title);
        }
    }

    /**
     * Gets the ticker used by this Screen.
     * @return ticker object used, or null if no ticker is present
     */
    public Ticker getTicker() {
        return ticker;
    }

    /**
     * Set a ticker for use with this Screen, replacing any previous ticker.
     * If null, removes the ticker object from this screen. The same ticker is may be shared
     * by several Screen objects within an application. This is done by calling setTicker()
     * on different screens with the same Ticker object. If the Screen is physically visible,
     * the visible effect should take place no later than immediately after the callback or
     * startApp returns back to the implementation.
     * @param ticker the ticker object used on this screen
     */
    public void setTicker(Ticker ticker) {
        Ticker oldTicker = this.ticker;
        this.ticker = ticker;

        ScreenDelegate delegate = delegateHolder.getDelegate();
        if(delegate != null) {
            delegate.onTickerChanged(oldTicker, this.ticker);
        }
    }

    public void attachDelegate(ScreenDelegate delegate) {
        delegateHolder.setDelegate(delegate);
    }
}

