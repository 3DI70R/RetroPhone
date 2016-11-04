package javax.microedition.lcdui;

import ru.threedisevenzeror.retrophone.utils.DelegateHolder;

/**
 * An item that can contain a string.
 * A StringItem is display-only; the user cannot edit the contents.
 * Both the label and the textual content of a StringItem may be modified by the application.
 * The visual representation of the label may differ from that of the textual contents.
 */
public class StringItem extends Item {

    public static abstract class StringItemDelegate extends ItemDelegate {

        public void onTextChanged(String oldText, String newText) {
            // noop
        }

        @Override
        public StringItem getAttachedObject() {
            return (StringItem) super.getAttachedObject();
        }
    }

    private String currentText;
    private final DelegateHolder<StringItemDelegate> delegateHolder;

    /**
     * Creates a new StringItem object with empty label and textual content
     */
    public StringItem() {
        this("", "");
    }

    /**
     * Creates a new StringItem object with the given label and textual content. Either label or text may be present or null.
     * @param label the Item label
     * @param text the text contents
     */
    public StringItem(String label, String text)  {
        delegateHolder = new DelegateHolder<>(this);
        setLabel(label);
        setText(text);
    }

    /**
     * Gets the text contents of the StringItem, or null if the StringItem is empty.
     * @return a string with the content of the item
     */
    public String getText() {
        return currentText;
    }

    /**
     * Sets the text contents of the StringItem. If text is null, the StringItem is set to be empty.
     * @param text the new content
     */
    public void setText(String text) {
        String oldText = currentText;
        currentText = text;
        delegateHolder.callIfExists(d -> d.onTextChanged(oldText, currentText));
    }

    @Override
    public String toString() {
        return getText();
    }

    ////////// Implementation methods \\\\\\\\\\

    @Override
    public void attachDelegate(ItemDelegate delegate) {
        delegateHolder.setDelegate(null);
        super.attachDelegate(delegate);
    }

    public void attachDelegate(StringItemDelegate delegate) {
        super.attachDelegate(delegate);
        delegateHolder.setDelegate(delegate);
    }
}

