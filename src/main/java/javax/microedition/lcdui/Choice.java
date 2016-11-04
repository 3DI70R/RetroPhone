package javax.microedition.lcdui;

/**
 * Choice defines an API for a user interface components implementing selection from predefined number of choices.
 * Such UI components are List and ChoiceGroup. The contents of the Choice are represented with strings and optional images.<br/>
 * <br/>
 * Each element of a Choice is composed of a text string and an optional image. The application may provide null for
 * the image if the element does not have an image part. If the application provides an image, the implementation may
 * choose to ignore the image if it exceeds the capacity of the device to display it. If the implementation displays
 * the image, it will be displayed adjacent to the text string and the pair will be treated as a unit.<br/>
 * <br/>
 * Images within any particular Choice object should all be of the same size, because the implementation is allowed
 * to allocate the same amount of vertical space for every element.<br/>
 * <br/>
 * If an element is too long to be displayed, the implementation will provide the user with means to see the whole element.
 * If this is done by wrapping an element to multiple lines, the second and subsequent lines show a clear indication to
 * the user that they are part of the same element and are not a new element.<br/>
 * <br/>
 * After a Choice object has been created, elements may be inserted, appended, and deleted, and each element's string part
 * and image part may be get and set. Elements within a Choice object are referred to by their indexes, which are
 * consecutive integers in the range from zero to size()-1, with zero referring to the first element and size()-1 to the last element.<br/>
 * <br/>
 * There are three types of Choices: implicit-choice (valid only for List), exclusive-choice, and multiple-choice.<br/>
 * <br/>
 * The exclusive-choice presents a series of elements and interacts with the user. That is, when the user selects an element,
 * that element is shown to be selected using a distinct visual representation. Exactly one element must be selected at any
 * given time. If at any time a situation would result where there are elements in the exclusive-choice but none is selected,
 * the implementation will choose an element and select it. This situation can arise when an element is added to an empty
 * Choice, when the selected element is deleted from the Choice, or when a Choice is created and populated with elements
 * by a constructor. In these cases, the choice of which element is selected is left to the implementation. Applications
 * for which the selected element is significant should set the selection explicitly. There is no way for the user to
 * unselect an element within an Exclusive Choice.<br/>
 * <br/>
 * The implicit choice is an exclusive choice where the focused element is implicitly selected when a command is initiated.<br/>
 * <br/>
 * A multiple-choice presents a series of elements and allows the user to select any number of elements in
 * any combination. As with exclusive-choice, the multiple-choice interacts with the user in object-operation
 * mode. The visual appearance of a multiple-choice will likely have a visual representation distinct from
 * the exclusive-choice that shows the selected state of each element as well as indicating to the user that
 * multiple elements may be selected.<br/>
 * <br/>
 * The selected state of an element is a property of the element. This state stays with that element if
 * other elements are inserted or deleted, causing elements to be shifted around. For example, suppose
 * element n is selected, and a new element is inserted at index zero. The selected element would now have
 * index n+1. A similar rule applies to deletion. Assuming n is greater than zero, deleting element zero
 * would leave element n-1 selected. Setting the contents of an element leaves its selected state unchanged.
 * When a new element is inserted or appended, it is always unselected (except in the special case of adding
 * an element to an empty Exclusive Choice as mentioned above).<br/>
 * <br/>
 * When a Choice is present on the display the user can interact with it indefinitely (for instance, traversing from
 * element to element and possibly scrolling). These traversing and scrolling operations do not cause application-visible
 * events. The system notifies the application either when some application-defined Command is fired, or when selection
 * state of ChoiceGroup is changed. When command is fired a high-level event is delivered to the listener of the Screen.
 * The event delivery is done with commandAction . In the case of ChoiceGroup the ItemStateListener is called when the
 * user changes the selection state of the ChoiceGroup. At this time the application can query the
 * Choice for information about the currently selected element(s).
 */
public interface Choice {

    interface ChoiceDelegate {

        void onItemAdded(int index, String stringPart, Image imagePart);

        void onItemChanged(int index, String oldStringPart, Image oldImagePart, String newStringPart, Image newImagePart);

        void onItemRemoved(int index, String stringPart, Image imagePart);

        void onItemSelectionChange(int index, String stringPart, Image imagePart, boolean isSelected);
    }

    /**
     * EXCLUSIVE is a choice having exactly one element selected at time.
     */
    int EXCLUSIVE = 1;

    /**
     * MULTIPLE is a choice that can have arbitrary number of elements selected at a time.
     */
    int MULTIPLE = 2;

    /**
     * IMPLICIT is a choice in which the currently focused item is selected when a Command is initiated.
     * (Note: IMPLICIT is not accepted by ChoiceGroup)
     */
    int IMPLICIT = 3;

    /**
     * Gets the number of elements present.
     * @return the number of elements in the Choice
     */
    int size();

    /**
     * Gets the String part of the element referenced by elementNum.
     * The elementNum parameter must be within the range [0..size()-1], inclusive.
     *
     * @param elementNum the index of the element to be queried
     * @return the string part of the element
     *
     * @throws IndexOutOfBoundsException if elementNum is invalid
     */
    String getString(int elementNum);

    /**
     * Gets the Image part of the element referenced by elementNum.
     * The elementNum parameter must be within the range [0..size()-1], inclusive.
     *
     * @param elementNum the index of the element to be queried
     * @return the image part of the element, or null if there is no image
     * @throws IndexOutOfBoundsException if elementNum is invalid
     */
    Image getImage(int elementNum);

    /**
     * Appends an element to the Choice. The added element will be the last element of the Choice.
     * The size of the Choice grows by one.
     *
     * @param stringPart the string part of the element to be added
     * @param imagePart the image part of the element to be added, or null if there is no image part
     * @return the assigned index of the element
     *
     * @throws IllegalArgumentException if the image is mutable
     * @throws NullPointerException if stringPart is null
     */
    int append(String stringPart, Image imagePart);

    /**
     * Inserts an element into the Choice just prior to the element specified. The size of the Choice grows by one.
     * The elementNum parameter must be within the range [0..size()], inclusive. The index of the last element
     * is size()-1, and so there is actually no element whose index is size(). If this value is used for elementNum,
     * the new element is inserted immediately after the last element. In this case, the effect is identical to append().
     *
     * @param elementNum the index of the element where insertion is to occur
     * @param stringPart the string part of the element to be
     * @param imagePart the image part of the element to be inserted, or null if there is no image part
     *
     * @throws IndexOutOfBoundsException if elementNum is invalid
     * @throws IllegalArgumentException if the image is mutable
     * @throws NullPointerException if stringPart is null
     */
    void insert(int elementNum, String stringPart, Image imagePart);

    /**
     * Deletes the element referenced by elementNum. The size of the Choice shrinks by one.
     * It is legal to delete all elements from a Choice.
     * The elementNum parameter must be within the range [0..size()-1], inclusive.
     *
     * @param elementNum the index of the element to be deleted
     *
     * @throws IndexOutOfBoundsException - if elementNum is invalid
     */
    void delete(int elementNum);

    /**
     * Sets the element referenced by elementNum to the specified element, replacing the previous contents of the element.
     * The elementNum parameter must be within the range [0..size()-1], inclusive.
     *
     * @param elementNum the index of the element to be set
     * @param stringPart the string part of the new element
     * @param imagePart the image part of the element, or null if there is no image part
     *
     * @throws IndexOutOfBoundsException if elementNum is invalid
     * @throws IllegalArgumentException if the image is mutable
     * @throws NullPointerException if stringPart is null
     */
    void set(int elementNum, String stringPart, Image imagePart);

    /**
     * Gets a boolean value indicating whether this element is selected.
     * The elementNum parameter must be within the range [0..size()-1], inclusive.
     *
     * @param elementNum the index of the element to be queried
     * @return selection state of the element
     *
     * @throws IndexOutOfBoundsException if elementNum is invalid
     */
    boolean isSelected(int elementNum);

    /**
     * Returns the index number of an element in the Choice that is selected.
     * For Choice types EXCLUSIVE and IMPLICIT there is at most one element selected,
     * so this method is useful for determining the user's choice.
     * Returns -1 if the Choice has no elements (and therefore has no selected elements).<br/>
     * <br/>
     * For MULTIPLE, this always returns -1 because no single value can in general represent
     * the state of such a Choice. To get the complete state of a MULTIPLE Choice, see getSelectedFlags.
     *
     * @return index of selected element, or -1 if none
     */
    int getSelectedIndex();

    /**
     * Queries the state of a Choice and returns the state of all elements in the boolean array selectedArray_return.<br/>
     * <br/>
     * NOTE: this is a result parameter. It must be at least as long as the size of the Choice as returned by size().
     * If the array is longer, the extra elements are set to false.<br/>
     * <br/>
     * This call is valid for all types of Choices. For MULTIPLE, any number of elements may be
     * selected and set to true in the result array. For EXCLUSIVE and IMPLICIT exactly one element
     * will be selected (unless there are zero elements in the Choice).<br/>
     *
     * @param selectedArray_return array to contain the results
     * @return the number of selected elements in the Choice
     *
     * @throws IllegalArgumentException if selectedArray_return is shorter than the size of the Choice.
     * @throws NullPointerException if selectedArray_return is null
     */
    int getSelectedFlags(boolean[] selectedArray_return);

    /**
     * For MULTIPLE, this simply sets an individual element's selected state.<br/>
     * <br/>
     * For EXCLUSIVE, this can be used only to select any element, that is, the selected parameter must be true.
     * When an element is selected, the previously selected element is deselected. If selected is false,
     * this call is ignored. If element was already selected, the call has no effect.<br/>
     * <br/>
     * For IMPLICIT, this can be used only to select any element, that is, the selected parameter must be true.
     * When an element is selected, the previously selected element is deselected. If selected is false,
     * this call is ignored. If element was already selected, the call has no effect.<br/>
     * <br/>
     * The call to setSelectedIndex does not cause implicit activation of any Command.
     * For all list types, the elementNum parameter must be within the range [0..size()-1], inclusive.
     *
     * @param elementNum the index of the element, starting from zero
     * @param selected the state of the element, where true means selected and false means not selected
     *
     * @throws IndexOutOfBoundsException if elementNum is invalid
     */
    void setSelectedIndex(int elementNum, boolean selected);

    /**
     * Attempts to set the selected state of every element in the Choice.
     * The array must be at least as long as the size of the Choice. If the array is longer, the additional
     * values are ignored.<br/>
     * <br/>
     * For Choice objects of type MULTIPLE, this sets the selected state of every element in the Choice.
     * An arbitrary number of elements may be selected.<br/>
     * <br/>
     * For Choice objects of type EXCLUSIVE and IMPLICIT, exactly one array element must have the value true.
     * If no element is true, the first element in the Choice will be selected.
     * If two or more elements are true, the implementation will choose the first true element and select it.
     *
     * @param selectedArray an array in which the method collect the selection status
     * @throws IllegalArgumentException if selectedArray is shorter than the size of the Choice
     * @throws NullPointerException if selectedArray is null
     */
    void setSelectedFlags(boolean[] selectedArray);
}

