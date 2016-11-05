package javax.microedition.lcdui;

import ru.threedisevenzeror.retrophone.utils.ComponentDelegate;
import ru.threedisevenzeror.retrophone.utils.DelegateHolder;

/**
 * The Gauge class implements a bar graph display of a value intended for use in a form.
 * Gauge is optionally interactive. The values accepted by the object are small integers in the range zero through a maximum
 * value established by the application. The application is expected to normalize its values into this range.
 * The device is expected to normalize this range into a smaller set of values for display purposes.
 * Doing so will not change the actual value contained within the object. The range of values specified by the application
 * may be larger than the number of distinct visual states possible on the device,
 * so more than one value may have the same visual representation.<br/>
 * <br/>
 * For example, consider a Gauge object that has a range of values from zero to 99, running on a device that displays
 * the Gauge's approximate value using a set of one to ten bars. The device might show one bar for values zero
 * through nine, two bars for values ten through 19, three bars for values 20 through 29, and so forth.<br/>
 * <br/>
 * A Gauge may be interactive or non-interactive. Applications may set or retrieve the Gauge's value at any time
 * regardless of the interaction mode. The implementation may change the visual appearance of the bar graph depending
 * on whether the object is created in interactive mode.<br/>
 * <br/>
 * In interactive mode, the user is allowed to modify the value. The user will always have the means to change the value
 * up or down by one and may also have the means to change the value in greater increments. The user is prohibited
 * from moving the value outside the established range. The expected behavior is that the application sets the initial
 * value and then allows the user to modify the value thereafter. However, the application is not prohibited from
 * modifying the value even while the user is interacting with it.<br/>
 * <br/>
 * In many cases the only means for the user to modify the value will be to press a button to increase or decrease
 * the value by one unit at a time. Therefore, applications should specify a range of no more than a few dozen values.<br/>
 * <br/>
 * In non-interactive mode, the user is prohibited from modifying the value.
 * An expected use of the non-interactive mode is as a "progress indicator" to give the user some feedback as
 * progress occurs during a long-running operation. The application is expected to update the value
 * periodically using the setValue() method. An application using the Gauge as a progress indicator
 * should typically also attach a STOP command to the Form containing the Gauge to allow the user to halt the operation in progress.
 */
public class Gauge extends Item {

    public static abstract class GaugeDelegate extends ComponentDelegate<Gauge> {
        public abstract void onValueChanged(int oldValue, int newValue);
        public abstract void onMaxValueChanged(int oldMaxValue, int newMaxValue);
    }

    private boolean isInteractive;
    private int maxValue;
    private int value;
    private final DelegateHolder<GaugeDelegate, Gauge> delegateHolder;

    /**
     * Creates a new Gauge object with the given label, in interactive or non-interactive mode, with the given maximum and initial values.
     * The maximum value must be greater than zero, otherwise an exception is thrown. The initial value must be within the
     * range zero to maxValue, inclusive. If the initial value is less than zero, the value is set to zero. If the initial
     * value is greater than maxValue, it is set to maxValue.
     *
     * @param label the Gauge's label
     * @param interactive tells whether the user can change the value
     * @param maxValue the maximum value
     * @param initialValue the initial value in the range [0..maxValue]
     *
     * @throws IllegalArgumentException if maxValue is invalid
     */
    public Gauge(String label, boolean interactive, int maxValue, int initialValue)  {

        this.delegateHolder = new DelegateHolder<GaugeDelegate, Gauge>(this);
        this.isInteractive = interactive;

        setLabel(label);
        setMaxValue(maxValue);
        setValue(initialValue);
    }

    /**
     * Sets the current value of this Gauge object. If the value is less than zero, zero is used.
     * If the current value is greater than the maximum value, the current value is set to be equal to the maximum value.
     *
     * @param value the new value
     */
    public void setValue(int value) {
        int oldValue = this.value;
        if(value < 0) {
            this.value = 0;
        } else if(value > maxValue) {
            this.value = maxValue;
        } else {
            this.value = value;
        }

        GaugeDelegate delegate = delegateHolder.getDelegate();
        if(delegate != null) {
            delegate.onValueChanged(oldValue, this.value);
        }
    }

    /**
     * Gets the current value of this Gauge object.
     * @return current value of the Gauge
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the maximum value of this Gauge object. The new maximum value must be greater than zero, otherwise an exception is thrown.
     * If the current value is greater than new maximum value, the current value is set to be equal to the new maximum value.
     * @param maxValue the new maximum value
     *
     * @throws IllegalArgumentException if maxValue is invalid
     */
    public void setMaxValue(int maxValue) {
        if(maxValue < 0) {
            throw new IllegalArgumentException("Max value cannot be less than zero");
        }

        if(value > maxValue) {
            setValue(maxValue);
        }

        int oldValue = this.maxValue;
        this.maxValue = maxValue;

        GaugeDelegate delegate = delegateHolder.getDelegate();
        if(delegate != null) {
            delegate.onMaxValueChanged(oldValue, maxValue);
        }
    }

    /**
     * Gets the maximum value of this Gauge object.
     * @return the maximum value of the Gauge
     */
    public int getMaxValue() {
        return maxValue;
    }

    /**
     * Tells whether the user is allowed to change the value of the Gauge.
     * @return a boolean indicating whether the Gauge is interactive
     */
    public boolean isInteractive() {
        return isInteractive;
    }

    public void attachDelegate(GaugeDelegate newDelegate) {
        delegateHolder.setDelegate(newDelegate);
    }
}

