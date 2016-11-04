package javax.microedition.lcdui;

import ru.threedisevenzeror.retrophone.utils.ComponentDelegate;
import ru.threedisevenzeror.retrophone.utils.DelegateHolder;

/**
 * Implements a "ticker-tape," a piece of text that runs continuously across the display.
 * The direction and speed of scrolling are determined by the implementation. While animating,
 * the ticker string scrolls continuously. That is, when the string finishes scrolling off the display,
 * the ticker starts over at the beginning of the string.<br/>
 * <br/>
 * There is no API provided for starting and stopping the ticker. The application model is that the ticker is
 * always scrolling continuously. However, the implementation is allowed to pause the scrolling for power consumption purposes,
 * for example, if the user doesn't interact with the device for a certain period of time. The implementation should
 * resume scrolling the ticker when the user interacts with the device again.<br/>
 * <br/>
 * The same ticker may be shared by several Screen objects. This can be accomplished by calling setTicker() on all
 * such screens. Typical usage is for an application to place the same ticker on all of its screens. When the application
 * switches between two screens that have the same ticker, a desirable effect is for the ticker to be displayed at the same
 * location on the display and to continue scrolling its contents at the same position. This gives the illusion of the
 * ticker being attached to the display instead of to each screen.<br/>
 * <br/>
 * An alternative usage model is for the application to use different tickers on different sets of screens or
 * even a different one on each screen. The ticker is an attribute of the Screen class so that applications may
 * implement this model without having to update the ticker to be displayed as the user switches among screens.
 */
public class Ticker {

    public static abstract class TickerDelegate extends ComponentDelegate {

        public void onStringChanged(String oldString, String newString) {
            // noop
        }

        @Override
        public Ticker getAttachedObject() {
            return (Ticker) super.getAttachedObject();
        }
    }

    private String string;
    private final DelegateHolder<TickerDelegate> delegateHolder;

    /**
     * Constructs a new Ticker object, given its initial contents string.
     *
     * @param str string to be set for the Ticker
     * @throws NullPointerException if str is null
     */
    public Ticker(String str) {
        delegateHolder = new DelegateHolder<TickerDelegate>(this);
        setString(str);
    }

    /**
     * Gets the string currently being scrolled by the ticker.
     * @return string of the ticker
     */
    public String getString() {
        return string;
    }

    /**
     * Sets the string to be displayed by this ticker.
     * If this ticker is active and is on the display, it immediately begins showing the new string.
     * @param str string to be set for the Ticker
     *
     * @throws NullPointerException if str is null
     */
    public void setString(String str) {
        if(str != null) {
            String oldString = string;
            string = str;

            TickerDelegate delegate = delegateHolder.getDelegate();
            if(delegate != null) {
                delegate.onStringChanged(oldString, string);
            }
        } else {
            throw new NullPointerException("Cannot set null string for ticker");
        }
    }

    public void attachDelegate(TickerDelegate delegate) {
        delegateHolder.setDelegate(delegate);
    }
}

