package javax.microedition.lcdui;

/**
 * This interface is used by applications which need to receive events that indicate changes in the internal
 * state of the interactive items within a Form screen.
 */
public interface ItemStateListener {

    /**
     * Called when internal state of an Item has been changed by the user. This happens when the user:<br/>
     * <br/>
     * - changes the set of selected values in a ChoiceGroup;<br/>
     * - adjusts the value of an interactive Gauge;<br/>
     * - enters or modifies the value in a TextField; and<br/>
     * - enters a new date or time in a DateField.<br/>
     * <br/>
     * It is up to the device to decide when it considers a new value to have been entered into an Item.
     * For example, implementations of text editing within a TextField vary greatly from device to device.<br/>
     * <br/>
     * In general, it is not expected that the listener will be called after every change is made.
     * However, if an item's value has been changed, the listener will be called to notify the
     * application of the change before it is called for a change on another item, and before a
     * command is delivered to the Form's CommandListener. For implementations that have the concept
     * of an input focus, the listener should be called no later than when the focus moves away from
     * an item whose state has been changed. The listener should be called only if the item's value
     * has actually been changed.<br/>
     * <br/>
     * The listener is not called if the application changes the value of an interactive item.
     * @param item the item that was changed
     */
    void itemStateChanged(Item item);
}

