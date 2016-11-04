package javax.microedition.lcdui;

import ru.threedisevenzeror.retrophone.utils.DelegateHolder;

import java.util.ArrayList;

/**
 * A ChoiceGroup is a group of selectable elements intended to be placed within a Form.
 * The group may be created with a mode that requires a single choice to be made or that allows multiple choices.
 * The implementation is responsible for providing the graphical representation of these modes and must provide
 * visually different graphics for different modes. For example, it might use "radio buttons"
 * for the single choice mode and "check boxes" for the multiple choice mode.
 *
 * Note: most of the essential methods have been specified in the Choice interface.
 */
public class ChoiceGroup extends Item implements Choice {

    public static abstract class ChoiceGroupDelegate extends ItemDelegate implements ChoiceDelegate {

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

        public int getChoiceType() {
            checkForAttach();
            return getAttachedObject().choiceType;
        }

        @Override
        public ChoiceGroup getAttachedObject() {
            return (ChoiceGroup) super.getAttachedObject();
        }
    }

    private static class ChoiceEntry {

        public Image image;
        public String string;
        public boolean isSelected;

        public ChoiceEntry(String string, Image image) {
            this.string = string;
            this.image = image;
        }

        @Override
        public String toString() {
            return string;
        }
    }

    private int choiceType;
    private java.util.List<ChoiceEntry> itemList;
    private final DelegateHolder<ChoiceGroupDelegate> delegateHolder;

    /**
     * Creates a new, empty ChoiceGroup, specifying its title and its type.
     * The type must be one of EXCLUSIVE or MULTIPLE.
     * The IMPLICIT choice type is not allowed within a ChoiceGroup.
     *
     * @param label the item's label (see Item)
     * @param choiceType either EXCLUSIVE or MULTIPLE
     */
    public ChoiceGroup(String label, int choiceType) {
        this(label, choiceType, new String[0], null);
    }

    /**
     * Creates a new ChoiceGroup, specifying its title, the type of the ChoiceGroup,
     * and an array of Strings and Images to be used as its initial contents.
     *
     * The type must be one of EXCLUSIVE or MULTIPLE. The IMPLICIT type is not allowed for ChoiceGroup.
     *
     * The stringElements array must be non-null and every array element must also be non-null.
     * The length of the stringElements array determines the number of elements in the ChoiceGroup.
     * The imageElements array may be null to indicate that the ChoiceGroup elements have no images.
     * If the imageElements array is non-null, it must be the same length as the stringElements array.
     * Individual elements of the imageElements array may be null in order to indicate the absence of
     * an image for the corresponding ChoiceGroup element. Any elements present in the
     * imageElements array must refer to immutable images.
     *
     * @param label the item's label (see Item)
     * @param choiceType EXCLUSIVE or MULTIPLE
     * @param stringElements set of strings specifying the string parts of the ChoiceGroup elements
     * @param imageElements set of images specifying the image parts of the ChoiceGroup elements
     *
     * @throws NullPointerException if stringElements is null
     * @throws NullPointerException if the stringElements array contains any null elements
     * @throws IllegalArgumentException if the imageElements array is non-null and has a different length from the stringElements array
     * @throws IllegalArgumentException if choiceType is neither EXCLUSIVE nor MULTIPLE
     * @throws IllegalArgumentException if any image in the imageElements array is mutable
     */
    public ChoiceGroup(String label, int choiceType, String[] stringElements, Image[] imageElements) {
        this(label, choiceType, stringElements, imageElements, false);
    }

    protected ChoiceGroup(String label, int choiceType, String[] stringElements, Image[] imageElements, boolean implicitAllowed) {

        if(choiceType != EXCLUSIVE && choiceType != MULTIPLE) {
            if(!implicitAllowed) {
                throw new IllegalArgumentException("Cannot create choice group with implicit type");
            } else if(choiceType != IMPLICIT) {
                throw new IllegalArgumentException("Unknown choice group type: " + choiceType);
            }
        }

        if(imageElements != null && imageElements.length != stringElements.length) {
            throw new IllegalArgumentException("the imageElements has a different length from the stringElements array");
        }

        this.delegateHolder = new DelegateHolder<>(this);
        this.choiceType = choiceType;
        this.itemList = new ArrayList<>();

        setLabel(label);

        for(int i = 0; i < stringElements.length; i++) {
            append(stringElements[i], imageElements != null ? imageElements[i] : null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int size() {
        return itemList.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getString(int elementNum) {
        return itemList.get(elementNum).string;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image getImage(int elementNum) {
        return itemList.get(elementNum).image;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int append(String stringPart, Image imagePart) {
        int index = itemList.size();
        ChoiceEntry entry = new ChoiceEntry(stringPart, imagePart);
        checkItem(entry);
        itemList.add(entry);
        delegateHolder.callIfExists(d -> d.onItemAdded(index, stringPart, imagePart));
        return index;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void insert(int elementNum, String stringPart, Image imagePart) {
        ChoiceEntry entry = new ChoiceEntry(stringPart, imagePart);
        checkItem(entry);
        itemList.add(elementNum, entry);
        delegateHolder.callIfExists(d -> d.onItemAdded(elementNum, stringPart, imagePart));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(int elementNum) {
        ChoiceEntry entry = itemList.remove(elementNum);
        delegateHolder.callIfExists(d -> d.onItemRemoved(elementNum, entry.string, entry.image));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void set(int elementNum, String stringPart, Image imagePart) {
        ChoiceEntry entry = new ChoiceEntry(stringPart, imagePart);
        checkItem(entry);
        ChoiceEntry oldEntry = itemList.set(elementNum, entry);
        delegateHolder.callIfExists(d -> d.onItemChanged(elementNum, oldEntry.string, oldEntry.image, stringPart, imagePart));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSelected(int elementNum) {
        return itemList.get(elementNum).isSelected;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSelectedIndex() {
        for(int i = 0; i < itemList.size(); i++) {
            if(itemList.get(i).isSelected) {
                return i;
            }
        }
        return -1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSelectedFlags(boolean[] selectedArray_return) {
        int totalCount = 0;
        for(int i = 0; i < itemList.size(); i++) {
            boolean selected = isSelected(i);
            selectedArray_return[i] = selected;
            if(selected) {
                totalCount++;
            }
        }
        return totalCount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectedIndex(int elementNum, boolean selected) {
        ChoiceEntry entry = itemList.get(elementNum);
        if(choiceType == MULTIPLE) {
            entry.isSelected = selected;
        } else {
            if(selected) {
                clearSelectedFlags();
                entry.isSelected = true;
            } else {
                entry.isSelected = false;
            }
        }

        delegateHolder.callIfExists(d -> d.onItemSelectionChange(elementNum, entry.string, entry.image, entry.isSelected));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSelectedFlags(boolean[] selectedArray) {
        if(choiceType == MULTIPLE) {
            for(int i = 0; i < itemList.size(); i++) {
                setSelectedIndex(i, selectedArray[i]);
            }
        } else {
            for(int i = 0; i < itemList.size(); i++) {
                if(selectedArray[i]) {
                    setSelectedIndex(i, true);
                }
            }
        }
    }

    ////////// Implementation methods \\\\\\\\\\

    @Override
    public void attachDelegate(ItemDelegate delegate) {
        delegateHolder.setDelegate(null);
        super.attachDelegate(delegate);
    }

    public void attachDelegate(ChoiceGroupDelegate newDelegate) {
        super.attachDelegate(newDelegate);
        delegateHolder.setDelegate(newDelegate);
    }

    private void clearSelectedFlags() {
        for(int i = 0; i < itemList.size(); i++) {
            ChoiceEntry e = itemList.get(i);
            if(e.isSelected) {
                e.isSelected = false;

                ChoiceGroupDelegate delegate = delegateHolder.getDelegate();
                if(delegate != null) {
                    delegate.onItemSelectionChange(i, e.string, e.image, false);
                }
            }
        }
    }

    private void checkItem(ChoiceEntry entry) {
        if(entry.string == null) {
            throw new NullPointerException("stringPart is null");
        }

        if(entry.image != null && entry.image.isMutable()) {
            throw new IllegalArgumentException("Image is mutable");
        }
    }
}

