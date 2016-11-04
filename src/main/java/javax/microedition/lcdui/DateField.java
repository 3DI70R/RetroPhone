package javax.microedition.lcdui;

import ru.threedisevenzeror.retrophone.utils.DelegateHolder;

import java.util.Date;
import java.util.TimeZone;

/**
 * A DateField is an editable component for presenting date and time (calendar) information that may be placed into a Form.
 * Value for this field can be initially set or left unset. If value is not set then the UI for the field shows this clearly.
 * The field value for "not initialized state" is not valid value and getDate() for this state returns null.<br/>
 * <br/>
 * Instance of a DateField can be configured to accept date or time information or both of them.
 * This input inputMode configuration is done by DATE, TIME or DATE_TIME static fields of this class.
 * DATE input inputMode allows to set only date information and TIME only time information (hours, minutes).
 * DATE_TIME allows to set both clock time and date values.<br/>
 * <br/>
 * In TIME input inputMode the date components of Date object must be set to the "zero epoch" value of January 1, 1970.<br/>
 * <br/>
 * Calendar calculations in this field are based on default locale and defined time zone.
 * Because of the calculations and different input modes date object may not contain same millisecond
 * value when set to this field and get back from this field.
 */
public class DateField extends Item {

    public static abstract class DateFieldDelegate extends ItemDelegate {

        public void onDateChanged(Date oldDate, Date newDate) {
            // noop
        }

        public void onInputModeChanged(int oldInputMode, int newInputMode) {
            // noop
        }

        public TimeZone getTimeZone() {
            checkForAttach();
            return getAttachedObject().timeZone;
        }

        @Override
        public DateField getAttachedObject() {
            return (DateField) super.getAttachedObject();
        }
    }

    /**
     * Input inputMode for date information (day, month, year).
     * With this inputMode this DateField presents and allows only to modify date value.
     * The time information of date object is ignored.
     */
    public static final int DATE = 1;

    /**
     * Input inputMode for time information (hours and minutes).
     * With this inputMode this DateField presents and allows only to modify time.
     * The date components should be set to the "zero epoch" value of January 1, 1970 and should not be accessed.
     */
    public static final int TIME = 2;

    /**
     * Input inputMode for date (day, month, year) and time (minutes, hours) information.
     * With this inputMode this DateField presents and allows to modify both time and date information.
     */
    public static final int DATE_TIME = 3;

    private int inputMode;
    private TimeZone timeZone;
    private Date currentDate;
    private final DelegateHolder<DateFieldDelegate> delegateHolder;

    /**
     * Creates a DateField object with the specified label and mode.
     * This call is identical to DateField(label, mode, null).
     *
     * @param label item label
     * @param mode the input mode, one of DATE, TIME or DATE_TIME
     *
     * @throws IllegalArgumentException if the input inputMode's value is invalid
     */
    public DateField(String label, int mode) {
        this(label, mode, null);
    }

    /**
     * reates a date field in which calendar calculations are based on specific TimeZone object and
     * the default calendaring system for the current locale. The value of the DateField is initially
     * in the "uninitialized" state. If timeZone is null, the system's default time zone is used.
     *
     * @param label item label
     * @param mode the input inputMode, one of DATE, TIME or DATE_TIME
     * @param timeZone a specific time zone, or null for the default time zone
     *
     * @throws IllegalArgumentException if the input inputMode's value is invalid
     */
    public DateField(String label, int mode, TimeZone timeZone) {
        if(mode != DATE && mode != TIME && mode != DATE_TIME) {
            throw new IllegalArgumentException("input inputMode value is invalid");
        }

        delegateHolder = new DelegateHolder<DateFieldDelegate>(this);
        inputMode = mode;
        currentDate = null;

        if(timeZone == null) {
            this.timeZone = TimeZone.getDefault();
        } else {
            this.timeZone = timeZone;
        }

        setLabel(label);
    }

    /**
     * Returns date value of this field. Returned value is null if field value is not initialized.
     * The date object is constructed according the rules of locale specific calendaring system and defined time zone.
     * In TIME mode field the date components are set to the "zero epoch" value of January 1, 1970.
     * If a date object that presents time beyond one day from this "zero epoch" then this field is in
     * "not initialized" state and this method returns null. In DATE mode field the time component of
     * the calendar is set to zero when constructing the date object.
     *
     * @return date object representing time or date depending on input mode
     */
    public Date getDate() {
        return currentDate;
    }

    /**
     * Sets a new value for this field. Null can be passed to set the field state to "not initialized" state.
     * The input mode of this field defines what components of passed Date object is used.<br/>
     * <br/>
     * In TIME input mode the date components must be set to the "zero epoch" value of January 1, 1970.
     * If a date object that presents time beyond one day then this field is in "not initialized" state.
     * In TIME input mode the date component of Date object is ignored and time component is used to precision of minutes.<br/>
     * <br/>
     * In DATE input mode the time component of Date object is ignored.<br/>
     * <br/>
     * In DATE_TIME input mode the date and time component of Date are used but only to precision of minutes.
     * @param date - new value for this field
     */
    public void setDate(Date date) {
        Date oldDate = currentDate;
        currentDate = date;

        DateFieldDelegate delegate = delegateHolder.getDelegate();
        if(delegate != null) {
            delegate.onDateChanged(oldDate, currentDate);
        }
    }

    /**
     * Gets input mode for this date field. Valid input modes are DATE, TIME and DATE_TIME.
     * @return input mode of this field
     */
    public int getInputMode() {
        return inputMode;
    }

    /**
     * Set input mode for this date field. Valid input modes are DATE, TIME and DATE_TIME.
     * @param mode the input mode, must be one of DATE, TIME or DATE_TIME
     *
     * @throws IllegalArgumentException if an invalid value is specified
     */
    public void setInputMode(int mode) {
        int oldInputMode = inputMode;
        inputMode = mode;

        DateFieldDelegate delegate = delegateHolder.getDelegate();
        if(delegate != null) {
            delegate.onInputModeChanged(oldInputMode, inputMode);
        }
    }

    public void attachDelegate(DateFieldDelegate newDelegate) {
        super.attachDelegate(newDelegate);
        delegateHolder.setDelegate(newDelegate);
    }

    @Override
    public void attachDelegate(ItemDelegate delegate) {
        delegateHolder.setDelegate(null);
        super.attachDelegate(delegate);
    }
}

