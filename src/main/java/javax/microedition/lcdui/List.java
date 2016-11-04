package javax.microedition.lcdui;

import ru.threedisevenzeror.retrophone.utils.DelegateHolder;

/**
 * The List class is a Screen containing list of choices.
 * Most of the behavior is common with class ChoiceGroup and the common API is defined in interface Choice.
 * When a List is present on the display the user can interact with it indefinitely
 * (for instance, traversing from element to element and possibly scrolling).
 * These traversing and scrolling operations do not cause application-visible events.<br/>
 * <br/>
 * The system notifies the application when some Command is fired. The notification of the application is done with commandAction.<br/>
 * <br/>
 * List, like any Choice, utilizes a dedicated "select" or "go" functionality of the devices.
 * Typically, the select functionality is distinct from the soft-buttons, but some devices may use soft-buttons
 * for the select. In any case, the application does not have a mean to set a label for a select key.<br/>
 * <br/>
 * In respect to select functionality here are three types of Lists:<br/>
 * <br/>
 * IMPLICIT where select causes immediate notification of the application if there is a CommandListener registered.
 * The element that has the focus will be selected before any CommandListener for this List is called.
 * An implicit SELECT_COMMAND is a parameter for the notification.<br/>
 * <br/>
 * EXCLUSIVE where select operation changes the selected element in the list. Application is not notified.<br/>
 * <br/>
 * MULTIPLE where select operation toggles the selected state of the focused Element. Application is not notified.<br/>
 * <br/>
 * IMPLICIT List can be used to construct menus by placing logical commands to elements.
 * In this case no application defined Commands have to be attached. Application just has to register a CommandListener
 * that is called when user "selects".<br/>
 * <br/>
 * Another use might be implementation of a Screen with a default operation that takes place when "select" is pressed.
 * For example, the List may contain email headers, and three operations: read, reply, and delete.
 * Read is consider to be the default operation.<br/>
 * <br/>
 * <code>void initialize() {<br/>
 * myScreen = new List("EMAIL", List.IMPLICIT);<br/>
 * readCommand = new Command("read", Command.SCREEN, 1);<br/>
 * replyCommand = new Command("reply", Command.SCREEN, 1);<br/>
 * deleteCommand = new Command("delete", Command.SCREEN, 1);<br/>
 * myScreen.addCommand(readCommand);<br/>
 * myScreen.addCommand(replyCommand);<br/>
 * myScreen.addCommand(deleteCommand);<br/>
 * myScreen.setCommandListener(this);<br/>
 * }</code><br/>
 * <br/>
 * Because the list is of type IMPLICIT, the select operation also calls the method commandAction with parameter SELECT_COMMAND. T
 * he implementation of commandAction() can now do the obvious thing and start the read operation:<br/>
 * <br/>
 * <code>public void commandAction (Command c, Displayable d) {<br/>
 *  if (d == myScreen) {<br/>
 *      if (c == readCommand || c == List.SELECT_COMMAND) {<br/>
 *          // show the mail to the user<br/>
 *      }<br/>
 *      // ...<br/>
 *  }<br/>
 * }</code><br/>
 * <br/>
 * It should be noted that this kind of default operation must be used carefully and the usability of the
 * resulting user interface must always kept in mind.
 * The application can also set the currently selected element(s) prior to displaying the List.<br/>
 * <br/>
 * Note: Many of the essential methods have been documented in interface Choice. <br/>
 */
public class List extends Screen implements Choice {

    public static abstract class ListDelegate extends ScreenDelegate implements ChoiceDelegate {

        @Override
        public void onItemAdded(int index, String stringPart, Image imagePart) {
            // noop
        }

        @Override
        public void onItemChanged(int index, String oldStringPart, Image oldImagePart,
                                  String newStringPart, Image newImagePart) {
            // noop
        }

        @Override
        public void onItemRemoved(int index, String stringPart, Image imagePart) {
            // noop
        }

        @Override
        public void onItemSelectionChange(int index, String stringPart, Image imagePart, boolean isSelected) {
            // noop
        }

        @Override
        public List getAttachedObject() {
            return (List) super.getAttachedObject();
        }
    }

    /**
     * SELECT_COMMAND is a special command that commandAction can use to recognize
     * the user did the select operation on a IMPLICIT List.
     *
     * The application should not use these values for recognizing the SELECT_COMMAND.
     * Instead, object identities of the Command and Displayable (List) should be used.
     */
    public static final Command SELECT_COMMAND = new Command("", Command.SCREEN, 0);

    private ChoiceGroup choice;
    private ChoiceGroup.ChoiceGroupDelegate choiceDelegate;
    private final DelegateHolder<ListDelegate> delegateHolder;

    /**
     * Creates a new, empty List, specifying its title and the type of the list.
     *
     * @param title the screen's title
     * @param listType one of IMPLICIT, EXCLUSIVE, or MULTIPLE
     *
     * @throws IllegalArgumentException if listType is not one of IMPLICIT, EXCLUSIVE, or MULTIPLE.
     */
    public List(String title, int listType) {
        this(title, listType, new String[0], null);
    }

    /**
     * Creates a new List, specifying its title, the type of the List, and an array of
     * Strings and Images to be used as its initial contents.<br/>
     * <br/>
     * The stringElements array must be non-null and every array element must also be non-null.
     * The length of the stringElements array determines the number of elements in the List.
     * The imageElements array may be null to indicate that the List elements have no images.
     * If the imageElements array is non-null, it must be the same length as the stringElements array.
     * Individual elements of the imageElements array may be null in order to indicate the absence of an image
     * for the corresponding List element. Any elements present in the imageElements array must refer to immutable images.<br/>
     *
     * @param title the screen's title
     * @param listType one of IMPLICIT, EXCLUSIVE, or MULTIPLE
     * @param stringElements set of strings specifying the string parts of the List elements
     * @param imageElements set of images specifying the image parts of the List elements
     *
     * @throws NullPointerException if stringElements is null
     * @throws NullPointerException if the stringElements array contains any null elements
     * @throws IllegalArgumentException if the imageElements array is non-null and has a different length from the stringElements array
     * @throws IllegalArgumentException if listType is not one of IMPLICIT, EXCLUSIVE, or MULTIPLE.
     * @throws IllegalArgumentException if any image in the imageElements array is mutable
     */
    public List(String title, int listType, String[] stringElements, Image[] imageElements) {
        delegateHolder = new DelegateHolder<>(this);
        choice = new ChoiceGroup(title, listType, stringElements, imageElements, true);
        choice.setOwner(this);
        choiceDelegate = new ChoiceGroup.ChoiceGroupDelegate() {

            @Override
            public void onItemAdded(int index, String stringPart, Image imagePart) {
                delegateHolder.callIfExists(d -> d.onItemAdded(index, stringPart, imagePart));
            }

            @Override
            public void onItemChanged(int index, String oldStringPart, Image oldImagePart, String newStringPart, Image newImagePart) {
                delegateHolder.callIfExists(d -> d.onItemChanged(index, oldStringPart, oldImagePart, newStringPart, newImagePart));
            }

            @Override
            public void onItemRemoved(int index, String stringPart, Image imagePart) {
                delegateHolder.callIfExists(d -> d.onItemRemoved(index, stringPart, imagePart));
            }

            @Override
            public void onItemSelectionChange(int index, String stringPart, Image imagePart, boolean isSelected) {
                delegateHolder.callIfExists(d -> d.onItemSelectionChange(index, stringPart, imagePart, isSelected));
            }
        };

        choice.attachDelegate(choiceDelegate);

        setTitle(title);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return choice.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getString(int elementNum) {
        return choice.getString(elementNum);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image getImage(int elementNum) {
        return choice.getImage(elementNum);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int append(String stringPart, Image imagePart) {
        return choice.append(stringPart, imagePart);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(int elementNum, String stringPart, Image imagePart) {
        choice.insert(elementNum, stringPart, imagePart);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(int elementNum) {
        choice.delete(elementNum);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(int elementNum, String stringPart, Image imagePart) {
        choice.set(elementNum, stringPart, imagePart);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSelected(int elementNum) {
        return choice.isSelected(elementNum);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSelectedIndex() {
        return choice.getSelectedIndex();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSelectedFlags(boolean[] selectedArray_return) {
        return choice.getSelectedFlags(selectedArray_return);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectedIndex(int elementNum, boolean selected) {
        choice.setSelectedIndex(elementNum, selected);

        if(selected && choiceDelegate.getChoiceType() != IMPLICIT) {
            delegateHolder.callIfExists(d -> d.invokeCommand(SELECT_COMMAND));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectedFlags(boolean[] selectedArray) {
        choice.setSelectedFlags(selectedArray);
    }

    ////////// Implementation methods \\\\\\\\\\

    public void attachDelegate(ListDelegate delegate) {
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

