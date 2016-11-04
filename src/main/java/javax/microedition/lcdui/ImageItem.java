package javax.microedition.lcdui;

import ru.threedisevenzeror.retrophone.utils.DelegateHolder;

public class ImageItem extends Item {

    public static abstract class ImageItemDelegate extends ItemDelegate {

        public void onLayoutChanged(int prevLayout, int newLayout) {
            // noop
        }

        public void onImageChanged(Image prevImage, Image newImage) {
            // noop
        }

        public void onAltTextChanged(String oldAltText, String newAltText) {
            // noop
        }

        @Override
        public ImageItem getAttachedObject() {
            return (ImageItem) super.getAttachedObject();
        }
    }

    /**
     * Use the default formatting of the "parentForm" of the image.
     */
    public static final int LAYOUT_DEFAULT = 0;

    /**
     * Image should be close to left-edge of the drawing area.
     */
    public static final int LAYOUT_LEFT = 1;

    /**
     * Image should be close to right-edge of the drawing area.
     */
    public static final int LAYOUT_RIGHT = 2;

    /**
     * Image should be horizontally centered.
     */
    public static final int LAYOUT_CENTER = 3;

    /**
     * A new line should be started before the image is drawn.
     */
    public static final int LAYOUT_NEWLINE_BEFORE = 256;

    /**
     * A new line should be started after the image is drawn.
     */
    public static final int LAYOUT_NEWLINE_AFTER = 512;

    private Image image;
    private int itemLayout;
    private String altText;
    private final DelegateHolder<ImageItemDelegate> delegateHolder;

    /**
     * Creates a new ImageItem with the given label, image, layout directive, and alternate text string.
     *
     * @param label the label string
     * @param img the image, must be immutable
     * @param layout a combination of layout directives
     * @param altText the text that may be used in place of the image
     *
     * @throws IllegalArgumentException if the image is mutable
     * @throws IllegalArgumentException if the layout value is not a legal combination of directives
     */
    public ImageItem(String label, Image img, int layout, String altText) {
        delegateHolder = new DelegateHolder<ImageItemDelegate>(this);
        setLabel(label);
        setImage(img);
        setLayout(layout);
        setAltText(altText);
    }

    /**
     * Gets the image contained within the ImageItem, or null if there is no contained image.
     * @return image used by the ImageItem
     */
    public Image getImage() {
        return image;
    }

    /**
     * Sets the image object contained within the ImageItem. The image must be immutable.
     * If img is null, the ImageItem is set to be empty.
     * @param img the new image
     *
     * @throws IllegalArgumentException if the image is mutable
     */
    public void setImage(Image img) {
        if(img != null && img.isMutable()) {
            throw new IllegalArgumentException("Image cannot be mutable");
        }

        Image oldImage = image;
        image = img;

        ImageItemDelegate delegate = delegateHolder.getDelegate();
        if(delegate != null) {
            delegate.onImageChanged(oldImage, image);
        }
    }

    /**
     * Gets the text string to be used if the image exceeds the device's capacity to display it.
     * @return the alternate text value, or null if none
     */
    public String getAltText() {
        return altText;
    }

    /**
     * Sets the alternate text of the ImageItem, or null if no alternate text is provided.
     * @param text the new alternate text
     */
    public void setAltText(String text) {
        String oldAltText = altText;
        altText = text;

        ImageItemDelegate delegate = delegateHolder.getDelegate();
        if(delegate != null) {
            delegate.onAltTextChanged(oldAltText, altText);
        }
    }

    /**
     * Gets the layout directives used for placing the image.
     * @return a combination of layout directive values
     */
    public int getLayout() {
        return itemLayout;
    }

    /**
     * Sets the layout directives.
     * @param layout a combination of layout directive values
     *
     * @throws IllegalArgumentException if the value of layout is not a valid combination of layout directives
     */
    public void setLayout(int layout) {
        // TODO: checks

        int oldLayout = itemLayout;
        itemLayout = layout;

        ImageItemDelegate delegate = delegateHolder.getDelegate();
        if(delegate != null) {
            delegate.onLayoutChanged(oldLayout, itemLayout);
        }
    }

    ////////// Implementation methods \\\\\\\\\\

    public void attachDelegate(ImageItemDelegate delegate) {
        super.attachDelegate(delegate);
        delegateHolder.setDelegate(delegate);
    }

    @Override
    public void attachDelegate(ItemDelegate delegate) {
        delegateHolder.setDelegate(null);
        super.attachDelegate(delegate);
    }
}

