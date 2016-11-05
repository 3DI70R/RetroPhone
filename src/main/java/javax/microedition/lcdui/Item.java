package javax.microedition.lcdui;

import ru.threedisevenzeror.retrophone.utils.ComponentDelegate;
import ru.threedisevenzeror.retrophone.utils.DelegateHolder;

public abstract class Item {

    public static abstract class ItemDelegate extends ComponentDelegate<Item> {

        public abstract void onLabelChanged(String oldLabel, String newLabel);

        public void onOwnerChanged(Displayable oldOwner, Displayable newOwner) {
            // noop
        }

        public Displayable getOwner() {
            checkForAttach();
            return getAttachedObject().owner;
        }

        public void notifyStateChanged() {
            checkForAttach();
            Item item = getAttachedObject();
            if(item.stateListener != null) {
                item.stateListener.itemStateChanged(item);
            }
        }
    }

    private Displayable owner;
    private String itemLabel;
    private ItemStateListener stateListener;
    private final DelegateHolder<ItemDelegate, Item> delegateHolder;

    public Item() {
        delegateHolder = new DelegateHolder<ItemDelegate, Item>(this);
    }

    /**
     * Gets the label of this Item object.
     * @return the label string
     */
    public String getLabel() {
        return itemLabel;
    }

    /**
     * Sets the label of the Item. If label is null, specifies that this item has no label.
     * @param label the label string
     */
    public void setLabel(String label) {
        String oldLabel = itemLabel;
        itemLabel = label;

        ItemDelegate delegate = delegateHolder.getDelegate();
        if(delegate != null) {
            delegate.onLabelChanged(oldLabel, itemLabel);
        }
    }

    ////////// Implementation methods \\\\\\\\\\

    public void attachDelegate(ItemDelegate delegate) {
        delegateHolder.setDelegate(delegate);
    }

    void setItemStateListener(ItemStateListener iListener) {
        stateListener = iListener;
    }

    void setOwner(Displayable newOwner) {
        Displayable oldOwner = this.owner;
        owner = newOwner;
        ItemDelegate delegate = delegateHolder.getDelegate();
        if(delegate != null) {
            delegate.onOwnerChanged(oldOwner, newOwner);
        }
    }

    Displayable getOwner() {
        return owner;
    }
}

