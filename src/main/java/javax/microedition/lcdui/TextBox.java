package javax.microedition.lcdui;

import ru.threedisevenzeror.retrophone.utils.DelegateHolder;

/**
 * The TextBox class is a Screen that allows the user to enter and edit text.<br/
 * <br/>
 * A TextBox has a maximum size, which is the maximum number of characters that can be stored in the object
 * at any time (its capacity). This limit is enforced when the TextBox instance is constructed, when the
 * user is editing text within the TextBox, as well as when the application program calls methods on the
 * TextBox that modify its contents. The maximum size is the maximum stored capacity and is unrelated to
 * the number of characters that may be displayed at any given time. The number of characters displayed
 * and their arrangement into rows and columns are determined by the device.<br/>
 * <br/>
 * The implementation may place a boundary on the maximum size, and the maximum size actually assigned
 * may be smaller than the application had requested. The value actually assigned will be reflected in
 * the value returned by getMaxSize(). A defensively-written application should compare this value to
 * the maximum size requested and be prepared to handle cases where they differ.<br/>
 * <br/>
 * The text contained within a TextBox may be more than can be displayed at one time. If this is the case,
 * the implementation will let the user scroll to view and edit any part of the text. This scrolling occurs
 * transparently to the application.<br/>
 * <br/>
 * TextBox has the concept of input constraints that is identical to TextField.
 * The constraints parameters of methods within the TextBox class use constants defined in the TextField class.
 * See the description of input constraints in the TextField class for the definition of these constants.
 */
public class TextBox extends Screen {

    public static abstract class TextBoxDelegate extends ScreenDelegate {

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
            getAttachedObject().textField.setCaretPosition(caretPosition);
        }

        public boolean isValidForConstraints(String text) {
            checkForAttach();
            TextBox box = getAttachedObject();
            return box.textField.isValidForConstraints(text, box.getConstraints());
        }

        @Override
        public TextBox getAttachedObject() {
            return (TextBox) super.getAttachedObject();
        }
    }

    private TextField textField;
    private final DelegateHolder<TextBoxDelegate> delegateHolder;

    /**
     * Creates a new TextBox object with the given title string, initial contents, maximum size in characters,
     * and constraints. If the text parameter is null, the TextBox is created empty. The maxSize parameter must be greater than zero.
     *
     * @param title the title text to be shown with the display
     * @param text the initial contents of the text editing area, null may be used to indicate no initial content.
     * @param maxSize the maximum capacity in characters. The implementation may limit boundary maximum capacity and the
     *                actually assigned capacity may me smaller than requested. A defensive application
     *                will test the actually given capacity with getMaxSize().
     * @param constraints see input constraints
     *
     * @throws IllegalArgumentException if maxSize is zero or less
     * @throws IllegalArgumentException if the constraints parameter is invalid
     * @throws IllegalArgumentException if text is illegal for the specified constraints
     * @throws IllegalArgumentException if the length of the string exceeds the requested maximum capacity or the maximum capacity actually assigned
     */
    public TextBox(String title, String text, int maxSize, int constraints) {
        delegateHolder = new DelegateHolder<TextBoxDelegate>(this);
        textField = new TextField(title, text, maxSize, constraints);
        textField.attachDelegate(new TextField.TextFieldDelegate() {
            @Override
            public void onMaxSizeChanged(int prevMaxSize, int newMaxSize) {
                super.onMaxSizeChanged(prevMaxSize, newMaxSize);

                TextBoxDelegate delegate = delegateHolder.getDelegate();
                if(delegate != null) {
                    delegate.onMaxSizeChanged(prevMaxSize, newMaxSize);
                }
            }

            @Override
            public void onConstraintsChanged(int prevConstraints, int newConstraints) {
                super.onConstraintsChanged(prevConstraints, newConstraints);

                TextBoxDelegate delegate = delegateHolder.getDelegate();
                if(delegate != null) {
                    delegate.onConstraintsChanged(prevConstraints, newConstraints);
                }
            }

            @Override
            public void onStringChanged(String prevString, String newString) {
                super.onStringChanged(prevString, newString);

                TextBoxDelegate delegate = delegateHolder.getDelegate();
                if(delegate != null) {
                    delegate.onStringChanged(prevString, newString);
                }
            }
        });

        setMaxSize(maxSize);
        setConstraints(constraints);
    }

    /**
     * Gets the contents of the TextBox as a string value.
     * @return the current contents
     */
    public String getString() {
        return textField.getString();
    }

    /**
     * Sets the contents of the TextBox as a string value, replacing the previous contents.
     * @param text the new value of the TextBox, or null if the TextBox is to be made empty
     *
     * @throws IllegalArgumentException if the text is illegal for the current input constraints
     * @throws IllegalArgumentException if the text would exceed the current maximum capacity
     */
    public void setString(String text) {
        textField.setString(text);
    }

    /**
     * Copies the contents of the TextBox into a character array starting at index zero.
     * Array elements beyond the characters copied are left unchanged.
     * @param data the character array to receive the value
     * @return the number of characters copied
     *
     * @throws ArrayIndexOutOfBoundsException if the array is too short for the contents
     * @throws NullPointerException if data is null
     */
    public int getChars(char[] data) {
        return textField.getChars(data);
    }

    /**
     * Sets the contents of the TextBox from a character array, replacing the previous contents.
     * Characters are copied from the region of the data array starting at array index offset
     * and running for length characters. If the data array is null, the TextBox is set to be
     * empty and the other parameters are ignored.
     *
     * @param data the source of the character data
     * @param offset the beginning of the region of characters to copy
     * @param length the number of characters to copy
     *
     * @throws ArrayIndexOutOfBoundsException if offset and length do not specify a valid range within the data array
     * @throws IllegalArgumentException if the text is illegal for the current input constraints
     * @throws IllegalArgumentException if the text would exceed the current maximum capacity
     */
    public void setChars(char[] data, int offset, int length) {
        textField.setChars(data, offset, length);
    }

    /**
     * Inserts a string into the contents of the TextBox. The string is inserted just prior to the character
     * indicated by the position parameter, where zero specifies the first character of the contents of the TextBox.
     * If position is less than or equal to zero, the insertion occurs at the beginning of the contents, thus
     * effecting a prepend operation. If position is greater than or equal to the current size of the contents,
     * the insertion occurs immediately after the end of the contents, thus effecting an append operation.
     * For example, text.insert(s, text.size()) always appends the string s to the current contents.<br/>
     * <br/>
     * The current size of the contents is increased by the number of inserted characters.
     * The resulting string must fit within the current maximum capacity.<br/>
     * <br/>
     * If the application needs to simulate typing of characters it can determining the location of the
     * current insertion point ("caret") using the with getCaretPosition() method. For example,
     * text.insert(s, text.getCaretPosition()) inserts the string s at the current caret position.
     *
     * @param src the String to be inserted
     * @param position the position at which insertion is to occur
     *
     * @throws IllegalArgumentException if the resulting contents are illegal for the current input constraints
     * @throws IllegalArgumentException if the insertion would exceed the current maximum capacity
     * @throws NullPointerException if src is null
     */
    public void insert(String src, int position) {
        textField.insert(src, position);
    }

    /**
     * Inserts a subrange of an array of characters into the contents of the TextBox.
     * The offset and length parameters indicate the subrange of the data array to be used for insertion.
     * Behavior is otherwise identical to insert(String, int).
     *
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
        textField.insert(data, offset, length, position);
    }

    /**
     * Deletes characters from the TextBox.
     * @param offset the beginning of the region to be deleted
     * @param length the number of characters to be deleted
     *
     * @throws StringIndexOutOfBoundsException if offset and length do not specify a valid range within the contents of the TextField
     */
    public void delete(int offset, int length) {
        textField.delete(offset, length);
    }

    /**
     * Returns the maximum size (number of characters) that can be stored in this TextBox.
     * @return the maximum size in characters
     */
    public int getMaxSize() {
        return textField.getMaxSize();
    }

    /**
     * Sets the maximum size (number of characters) that can be contained in this TextBox.
     * If the current contents of the TextBox are larger than maxSize, the contents are truncated to fit.
     * @param maxSize the new maximum size
     * @return assigned maximum capacity - may be smaller than requested.
     *
     * @throws IllegalArgumentException if maxSize is zero or less.
     */
    public int setMaxSize(int maxSize) {
        return textField.setMaxSize(maxSize);
    }

    /**
     * Gets the number of characters that are currently stored in this TextBox.
     * @return the number of characters
     */
    public int size() {
        return textField.size();
    }

    /**
     * Gets the current input position. For some UIs this may block some time and ask the user about the
     * intended caret position, on some UIs may just return the caret position.
     * @return the current caret position, 0 if in the beginning.
     */
    public int getCaretPosition() {
        return textField.getCaretPosition();
    }

    /**
     * Sets the input constraints of the TextBox. If the current contents of the TextBox do not match the new constraints, the contents are set to empty.
     * @param constraints see input constraints
     * @throws IllegalArgumentException if constraints is not any of the ones specified in input constraints
     */
    public void setConstraints(int constraints) {
        textField.setConstraints(constraints);
    }

    /**
     * Get the current input constraints of the TextBox.
     * @return the current constraints value (see input constraints)
     */
    public int getConstraints() {
        return textField.getConstraints();
    }

    public void attachDelegate(TextBoxDelegate delegate) {
        super.attachDelegate(delegate);
        delegateHolder.setDelegate(delegate);
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

