package javax.microedition.lcdui;

import ru.threedisevenzeror.retrophone.utils.DelegateHolder;

/**
 * A TextField is an editable text component that may be placed into a Form.
 * It can be given a piece of text that is used as the initial value.<br/>
 * <br/>
 * A TextField has a maximum size, which is the maximum number of characters that can be stored
 * in the object at any time (its capacity). This limit is enforced when the TextField instance is constructed,
 * when the user is editing text within the TextField, as well as when the application program calls
 * methods on the TextField that modify its contents. The maximum size is the maximum stored capacity and
 * is unrelated to the number of characters that may be displayed at any given time. The number of characters
 * displayed and their arrangement into rows and columns are determined by the device.<br/>
 * <br/>
 * The implementation may place a boundary on the maximum size, and the maximum size actually
 * assigned may be smaller than the application had requested. The value actually assigned will
 * be reflected in the value returned by getMaxSize(). A defensively-written application should
 * compare this value to the maximum size requested and be prepared to handle cases where they differ.<br/>
 * <br/>
 * Input Constraints<br/>
 * <br/>
 * The TextField shares the concept of input constraints with the TextBox object.
 * The different constraints allow the application to request that the user's input be restricted
 * in a variety of ways. The implementation is required to restrict the user's input as requested
 * by the application. For example, if the application requests the NUMERIC constraint on a TextField,
 * the implementation must allow only numeric characters to be entered.<br/>
 * <br/>
 * The implementation is not required to do any syntactic validation of the contents of the text object.
 * Applications must be prepared to perform such checking themselves.<br/>
 * <br/>
 * The implementation may provide special formatting for the value entered. For example, a
 * PHONENUMBER field may be separated and punctuated as appropriate for the phone number
 * conventions in use, grouping the digits into country code, area code, prefix, etc. Any spaces
 * or punctuation provided are not considered part of the text field's value. For example, a TextField with the
 * PHONENUMBER constraint might display as follows:<br/>
 * (408) 555-1212<br/>
 * but the value of the field visible to the application would be a string representing a legal phone number
 * like "4085551212". Note that in some networks a '+' prefix is part of the number and returned as a part of the string.
 */
public class TextField extends Item {

    public abstract static class TextFieldDelegate extends ItemDelegate {

        public void onMaxSizeChanged(int prevMaxSize, int newMaxSize) {
            // noop
        }

        public void onConstraintsChanged(int prevConstraints, int newConstraints) {
            // noop
        }

        public void onStringChanged(String prevString, String newString) {
            // noop
        }

        public void setCaretPosition(int caretPosition) {
            checkForAttach();
            getAttachedObject().setCaretPosition(caretPosition);
        }

        public boolean isValidForConstraints(String text) {
            checkForAttach();
            TextField field = getAttachedObject();
            return field.isValidForConstraints(text, field.getConstraints());
        }

        @Override
        public TextField getAttachedObject() {
            return (TextField) super.getAttachedObject();
        }
    }

    /**
     * The user is allowed to enter any text.
     */
    public static final int ANY = 0;

    /**
     * The user is allowed to enter an e-mail address.
     */
    public static final int EMAILADDR = 1;

    /**
     * The user is allowed to enter only an integer value.
     * The implementation must restrict the contents to consist of an optional
     * minus sign followed by an optional string of numerals.
     */
    public static final int NUMERIC = 2;

    /**
     * The user is allowed to enter a phone number.
     * The phone number is a special case, since a phone-based implementation may be linked
     * to the native phone dialing application. The implementation may automatically start
     * a phone dialer application that is initialized so that pressing a single key would be
     * enough to make a call. The call must not made automatically without requiring user's
     * confirmation. The exact set of characters allowed is specific to the device and to
     * the device's network and may include non-numeric characters.
     */
    public static final int PHONENUMBER = 3;

    /**
     * The user is allowed to enter a URL.
     */
    public static final int URL = 4;

    /**
     * The text entered must be masked so that the characters typed are not visible.
     * The actual contents of the text field are not affected, but each character is displayed
     * using a mask character such as "*". The character chosen as the mask character is
     * implementation-dependent. This is useful for entering confidential information such as
     * passwords or PINs (personal identification numbers).<br/>
     * <br/>
     * The PASSWORD modifier can be combined with other input constraints by using the logical
     * OR operator (|). However, The PASSWORD modifier is nonsensical with some constraint
     * values such as EMAILADDR, PHONENUMBER, and URL.
     */
    public static final int PASSWORD = 65536;

    /**
     * The mask value for determining the constraint mode.
     * The application should use the logical AND operation with a value returned by
     * getConstraints() and CONSTRAINT_MASK in order to retrieve the current constraint mode,
     * in order to remove any modifier flags such as the PASSWORD flag.
     */
    public static final int CONSTRAINT_MASK = 65535;

    private String string;
    private int currentMaxSize;
    private int currentConstraints;
    private int caretPosition;
    private final DelegateHolder<TextFieldDelegate> delegateHolder;

    /**
     * Creates a new TextField object with the given label, initial contents, maximum size in characters,
     * and constraints. If the text parameter is null, the TextField is created empty.
     * The maxSize parameter must be greater than zero.
     *
     * @param label item label
     * @param text the initial contents, or null if the TextField is to be empty
     * @param maxSize the maximum capacity in characters
     * @param constraints see input constraints
     *
     * @throws IllegalArgumentException if maxSize is zero or less
     * @throws IllegalArgumentException if the value of the constraints parameter is invalid
     * @throws IllegalArgumentException if text is illegal for the specified constraints
     * @throws IllegalArgumentException if the length of the string exceeds the requested maximum
     * capacity or the maximum capacity actually assigned
     */
    public TextField(String label, String text, int maxSize, int constraints) {
        delegateHolder = new DelegateHolder<>(this);
        setLabel(label);
        setMaxSize(maxSize);
        setConstraints(constraints);
        setString(text);
    }

    /**
     * Gets the contents of the TextField as a string value.
     * @return the current contents
     */
    public String getString() {
        return string;
    }

    /**
     * Sets the contents of the TextField as a string value, replacing the previous contents.
     * @param text the new value of the TextField, or null if the TextField is to be made empty
     *
     * @throws IllegalArgumentException if the text is illegal for the current input constraints
     * @throws IllegalArgumentException if the text would exceed the current maximum capacity
     */
    public void setString(String text) {
        String oldString = string;
        if(string == null) {
            this.string = "";
        } else {
            this.string = text;
        }

        delegateHolder.callIfExists(d -> d.onStringChanged(oldString, string));
    }

    /**
     * Copies the contents of the TextField into a character array starting at index zero.
     * Array elements beyond the characters copied are left unchanged.
     * @param data the character array to receive the value
     * @return the number of characters copied
     *
     * @throws ArrayIndexOutOfBoundsException if the array is too short for the contents
     * @throws NullPointerException if data is null
     */
    public int getChars(char[] data) {
        for(int i = 0; i < string.length(); i++) {
            data[i] = string.charAt(i);
        }
        return string.length();
    }

    /**
     * Sets the contents of the TextField from a character array, replacing the previous contents.
     * Characters are copied from the region of the data array starting at array index offset
     * and running for length characters. If the data array is null, the TextField is set to
     * be empty and the other parameters are ignored.
     * @param data the source of the character data
     * @param offset the beginning of the region of characters to copy
     * @param length the number of characters to copy
     *
     * @throws ArrayIndexOutOfBoundsException if offset and length do not specify a valid range within the data array
     * @throws IllegalArgumentException if the text is illegal for the current input constraints
     * @throws IllegalArgumentException if the text would exceed the current maximum capacity
     */
    public void setChars(char[] data, int offset, int length) {
        if(data != null) {
            setString(new String(data, offset, length));
        } else {
            setString(null);
        }
    }

    /**
     * Inserts a string into the contents of the TextField. The string is inserted just prior to
     * the character indicated by the position parameter, where zero specifies the first character of
     * the contents of the TextField. If position is less than or equal to zero, the insertion occurs
     * at the beginning of the contents, thus effecting a prepend operation. If position is greater
     * than or equal to the current size of the contents, the insertion occurs immediately after the
     * end of the contents, thus effecting an append operation. For example, text.insert(s, text.size())
     * always appends the string s to the current contents.<br/>
     * <br/>
     * The current size of the contents is increased by the number of inserted characters.
     * The resulting string must fit within the current maximum capacity.<br/>
     * <br/>
     * If the application needs to simulate typing of characters it can determining the location of
     * the current insertion point ("caret") using the with getCaretPosition() method. For example,
     * text.insert(s, text.getCaretPosition()) inserts the string s at the current caret position.
     * @param src the String to be inserted
     * @param position the position at which insertion is to occur
     *
     * @throws IllegalArgumentException if the resulting contents are illegal for the current input constraints
     * @throws IllegalArgumentException if the insertion would exceed the current maximum capacity
     * @throws NullPointerException if src is null
     */
    public void insert(String src, int position) {

        if(src == null) {
            throw new NullPointerException("src cannot be null");
        }

        if(position <= 0) {
            setString(src + getString());
        } else if(position >= size()) {
            setString(getString() + src);
        } else {
            String currentText = getString();
            String left = currentText.substring(0, position);
            String right = currentText.substring(position);
            setString(left + src + right);
        }
    }

    /**
     * Inserts a subrange of an array of characters into the contents of the TextField.
     * The offset and length parameters indicate the subrange of the data array to be used for insertion.
     * Behavior is otherwise identical to insert(String, int).
     * @param data the source of the character data
     * @param offset the beginning of the region of characters to copy
     * @param length the number of characters to copy
     * @param position the position at which insertion is to occur
     *
     * @throws ArrayIndexOutOfBoundsException if offset and length do not specify a valid range within the data array
     * @throws IllegalArgumentException if the resulting contents are illegal for the current input constraints
     * @throws IllegalArgumentException if the insertion would exceed the current maximum capacity
     * @throws NullPointerException if data is null
     */
    public void insert(char[] data, int offset, int length, int position) {
        insert(new String(data, offset, length), position);
    }

    /**
     * Deletes characters from the TextField.
     * @param offset the beginning of the region to be deleted
     * @param length the number of characters to be deleted
     *
     * @throws StringIndexOutOfBoundsException if offset and length do not specify a valid range within the contents of the TextField
     */
    public void delete(int offset, int length) {
        setString(new StringBuilder(getString()).delete(offset, offset + length).toString());
    }

    /**
     * Returns the maximum size (number of characters) that can be stored in this TextField.
     * @return the maximum size in characters
     */
    public int getMaxSize() {
        return currentMaxSize;
    }

    /**
     * Sets the maximum size (number of characters) that can be contained in this TextField.
     * If the current contents of the TextField are larger than maxSize, the contents are truncated to fit.
     * @param maxSize the new maximum size
     * @return assigned maximum capacity - may be smaller than requested.
     *
     * @throws IllegalArgumentException if maxSize is zero or less.
     */
    public int setMaxSize(int maxSize) {
        if(maxSize <= 0) {
            throw new IllegalArgumentException("currentMaxSize is zero or less");
        }

        int oldMaxSize = currentMaxSize;
        currentMaxSize = maxSize;

        delegateHolder.callIfExists(d -> d.onMaxSizeChanged(oldMaxSize, maxSize));

        return maxSize;
    }

    /**
     * Gets the number of characters that are currently stored in this TextField.
     * @return number of characters in the TextField
     */
    public int size() {
        return string.length();
    }

    /**
     * Gets the current input position.
     * For some UIs this may block some time and ask the user about the intended caret position,
     * on some UIs may just return the caret position.
     * @return the current caret position, 0 if in the beginning.
     */
    public int getCaretPosition() {
        return caretPosition;
    }

    /**
     * Sets the input constraints of the TextField.
     * If the the current contents of the TextField do not match the new constraints, the contents are set to empty.
     * @param constraints see input constraints
     *
     * @throws IllegalArgumentException if constraints is not any of the ones specified in input constraints
     */
    public void setConstraints(int constraints) {
        switch (constraints & CONSTRAINT_MASK) {
            case ANY:
            case EMAILADDR:
            case NUMERIC:
            case PHONENUMBER:
            case URL:
                int oldConstraints = currentConstraints;
                currentConstraints = constraints;
                if(!isValidForConstraints(getString(), getConstraints())) {
                    setString(null);
                }

                delegateHolder.callIfExists(d -> d.onConstraintsChanged(oldConstraints, constraints));

                break;
            default:
                throw new IllegalArgumentException("Unknown contstraints value: " + constraints);
        }
    }

    /**
     * Get the current input constraints of the TextField.
     * @return the current constraints value (see input constraints)
     */
    public int getConstraints() {
        return currentConstraints;
    }

    ////////// Implementation methods \\\\\\\\\\

    public void attachDelegate(TextFieldDelegate delegate) {
        super.attachDelegate(delegate);
        delegateHolder.setDelegate(delegate);
    }

    @Override
    public void attachDelegate(ItemDelegate delegate) {
        delegateHolder.setDelegate(null);
        super.attachDelegate(delegate);
    }

    void setCaretPosition(int caretPosition) {
        this.caretPosition = caretPosition;
    }

    boolean isValidForConstraints(String text, int constraints) {
        return true; // TODO: IMPL
    }
}

