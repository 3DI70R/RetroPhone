package javax.microedition.lcdui;

import ru.threedisevenzeror.retrophone.utils.DelegateHolder;

import java.util.ArrayList;

/**
 * A Form is a Screen that contains an arbitrary mixture of items: images, read-only text fields,
 * editable text fields, editable date fields, gauges, and choice groups. In general, any subclass
 * of the Item class may be contained within a parentForm. The implementation handles layout, traversal, and scrolling.
 * None of the components contained within has any internal scrolling; the entire contents scrolls together.
 * Note that this differs from the behavior of other classes, the List for example, where only the interior scrolls.<br/>
 * <br/>
 * The items contained within a Form may be edited using append, delete, insert, and set methods.
 * Items within a Form are referred to by their indexes, which are consecutive integers in the range
 * from zero to size()-1, with zero referring to the first item and size()-1 to the last item.<br/>
 * <br/>
 * An item may be placed within at most one Form. If the application attempts to place an item into a Form,
 * and the item is already owned by this or another Form, an IllegalStateException is thrown.
 * The application must remove the item from its currently containing Form before inserting it into the new Form.<br/>
 * <br/>
 * As with other screens, the layout policy in most devices is vertical.
 * In forms this applies to items involving user input. So, a new line is always started for focusable
 * items like TextField, DateField, Gauge or ChoiceGroup.<br/>
 * <br/>
 * Strings and images, which do not involve user interactions, behave differently;
 * they are filled in horizontal lines, unless newline is embedded in the string or layout directives
 * of the ImageItem force a new line. Contents will be wrapped (for text) or clipped (for images)
 * to fit the width of the display, and scrolling will occur vertically as necessary. There will be no horizontal scrolling.<br/>
 * <br/>
 * If the Form is visible on the display when changes to its contents are requested by the application,
 * the changes take place immediately. That is, applications need not take any special action to refresh
 * a Form's display after its contents have been modified.<br/>
 * <br/>
 * When a Form is present on the display the user can interact with it and its Items indefinitely
 * (for instance, traversing from Item to Item and possibly scrolling). These traversing and scrolling
 * operations do not cause application-visible events. The system notifies the application when the
 * user modifies the state of an interactive Item contained within the Form. This notification is
 * accomplished by calling the itemStateChanged() method of the listener declared to the Form with
 * the setItemStateListener() method.<br/>
 * <br/>
 * As with other Displayable objects, a Form can declare commands and declare a command listener with
 * the setCommandListener() method. CommandListener objects are distinct from ItemStateListener objects,
 * and they are declared and invoked separately.<br/>
 * <br/>
 * Notes for application developers:<br/>
 *  Although this class allows creation of arbitrary combination of components the application developers
 *  should keep the small screen size in mind. Form is designed to contain a small number of closely related UI elements.<br/>
 * <br/>
 *  If the number of items does not fit on the screen, the implementation may choose to make it
 *  scrollable or to fold some components so that a new screen is popping up when the element is edited.
 */
public class Form extends Screen {

    public static class FormDelegate extends ScreenDelegate {

        public void onItemAdded(int position, Item newItem) {
            // noop
        }

        public void onItemRemoved(int position, Item removedItem) {
            // noop
        }

        public void onItemChanged(int position, Item oldItem, Item newItem) {
            // noop
        }

        public void onItemStateChanged(int position, Item item) {
            // noop
        }
    }

    private java.util.List<Item> itemList;
    private ItemStateListener itemStateListener;
    private ItemStateListener innerStateListener;
    private final DelegateHolder<FormDelegate> delegateHolder;

    /**
     * Creates a new, empty Form.
     * @param title the Form's title, or null for no title
     */
    public Form(String title) {
        delegateHolder = new DelegateHolder<FormDelegate>(this);
        innerStateListener = new ItemStateListener() {
            @Override
            public void itemStateChanged(Item item) {
                if(itemStateListener != null) {
                    itemStateListener.itemStateChanged(item);
                }

                FormDelegate delegate = delegateHolder.getDelegate();
                if(delegate != null) {
                    delegate.onItemStateChanged(itemList.indexOf(item), item);
                }
            }
        };
        itemList = new ArrayList<Item>();
        setTitle(title);
    }

    /**
     * Creates a new Form with the specified contents.
     * This is identical to creating an empty Form and then using a set of append methods.
     * The items array may be null, in which case the Form is created empty.
     * If the items array is non-null, each element must be a valid Item not already contained within another Form.
     *
     * @param title the Form's title string
     * @param items the array of items to be placed in the Form, or null if there are no items
     *
     * @throws IllegalStateException if one of the items is already owned by another parentForm
     * @throws NullPointerException if an element of the items array is null
     */
    public Form(String title, Item[] items) {
        this(title);

        if(items != null) {
            for(Item i : items) {
                append(i);
            }
        }
    }

    /**
     * Adds an Item into the Form.
     *
     * Strings are filled so that current line is continued if possible.
     * If the text width is greater that the remaining horizontal space on the current line, the implementation
     * inserts a new line and appends the rest of the text. Whenever possible the implementation should
     * avoid breaking words into two lines. Instead, occurrences of white space (space or tab) should be used as potential
     * places for splitting the lines. Also, a newline character in the string causes starting of a new line.
     *
     * Images are laid out in the same manner as strings, unless the layout directives of ImageItem specify otherwise.
     * Focusable items (TextField, ChoiceGroup, DateField, and Gauge) are placed on their own horizontal lines.
     * @param item the Item to be added.
     * @return the assigned index of the Item
     *
     * @throws IllegalStateException if the item is already owned by a parentForm
     * @throws NullPointerException if item is null
     */
    public int append(Item item) {
        int position = itemList.size();
        insert(position, item);
        return position;
    }

    /**
     * Adds an item consisting of one String to the parentForm. The effect visible to the application is identical to
     * append(new StringItem(null, str))
     * @param str the String to be added
     * @return the assigned index of the Item
     *
     * @throws NullPointerException if str is null
     */
    public int append(String str) {
        return append(new StringItem(null, str));
    }

    /**
     * Adds an item consisting of one Image to the parentForm. The effect visible to the application is identical to
     *
     * append(new ImageItem(null, img, ImageItem.LAYOUT_DEFAULT, null))
     * @param img the image to be added
     * @return the assigned index of the Item
     *
     * @throws IllegalArgumentException - if the image is mutable
     * @throws NullPointerException - if img is null
     */
    public int append(Image img) {
        return append(new ImageItem(null, img, ImageItem.LAYOUT_DEFAULT, null));
    }

    /**
     * Inserts an item into the Form just prior to the item specified. The size of the Form grows by one.
     * The itemNum parameter must be within the range [0..size()], inclusive. The index of the last item is size()-1,
     * and so there is actually no item whose index is size(). If this value is used for itemNum, the new item is
     * inserted immediately after the last item. In this case, the effect is identical to append(Item).
     *
     * The semantics are otherwise identical to append(Item).
     * @param itemNum the index where insertion is to occur
     * @param item the item to be inserted
     *
     * @throws IndexOutOfBoundsException - if itemNum is invalid
     * @throws IllegalStateException - if the item is already owned by a container
     * @throws NullPointerException - if item is null
     */
    public void insert(int itemNum, Item item)  {
        validateItem(item);
        itemList.add(itemNum, item);
        item.setItemStateListener(innerStateListener);
        item.setOwner(this);

        FormDelegate delegate = delegateHolder.getDelegate();
        if(delegate != null) {
            delegate.onItemAdded(itemNum, item);
        }
    }

    /**
     * Deletes the Item referenced by itemNum. The size of the Form shrinks by one.
     * It is legal to delete all items from a Form. The itemNum parameter must be within the range [0..size()-1], inclusive.
     *
     * @param itemNum the index of the item to be deleted
     * @throws IndexOutOfBoundsException if itemNum is invalid
     */
    public void delete(int itemNum) {
        Item item = itemList.remove(itemNum);
        item.setOwner(null);
        item.setItemStateListener(null);

        FormDelegate delegate = delegateHolder.getDelegate();
        if(delegate != null) {
            delegate.onItemRemoved(itemNum, item);
        }
    }

    /**
     * Sets the item referenced by itemNum to the specified item, replacing the previous item.
     * The previous item is removed from this Form. The itemNum parameter must be within the range [0..size()-1], inclusive.
     *
     * The end result is equal to
     * insert(n, item); delete(n+1);
     * although the implementation may optimize the repainting and usage of the array that stores the items.
     *
     * @param itemNum the index of the item to be replaced
     * @param item the new item to be placed in the Form
     *
     * @throws IndexOutOfBoundsException if itemNum is invalid
     * @throws IllegalStateException if the item is already owned by a parentForm
     * @throws NullPointerException if item is null
     */
    public void set(int itemNum, Item item) {
        validateItem(item);
        item.setOwner(this);
        item.setItemStateListener(innerStateListener);

        Item oldItem = itemList.set(itemNum, item);
        oldItem.setOwner(null);
        oldItem.setItemStateListener(null);

        FormDelegate delegate = delegateHolder.getDelegate();
        if(delegate != null) {
            delegate.onItemChanged(itemNum, oldItem, item);
        }
    }

    /**
     * Gets the item at given position. The contents of the Form are left unchanged.
     * The itemNum parameter must be within the range [0..size()-1], inclusive.
     * @param itemNum the index of item
     * @return the item at the given position
     *
     * @throws IndexOutOfBoundsException if itemNum is invalid
     */
    public Item get(int itemNum) {
        return itemList.get(itemNum);
    }

    /**
     * Sets the ItemStateListener for the Form, replacing any previous ItemStateListener.
     * If iListener is null, simply removes the previous ItemStateListener.
     * @param iListener the new listener, or null to remove it
     */
    public void setItemStateListener(ItemStateListener iListener) {
        itemStateListener = iListener;
    }

    /**
     * Gets the number of items in the Form.
     * @return the number of items
     */
    public int size() {
        return itemList.size();
    }

    public void attachDelegate(FormDelegate delegate) {
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

    private void validateItem(Item item) {
        Displayable owner = item.getOwner();
        if(owner != null) {
            throw new IllegalStateException("Cannot append item, its already added to displayable \"" + owner + "\"");
        }
    }
}

