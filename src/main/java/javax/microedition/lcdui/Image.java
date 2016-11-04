package javax.microedition.lcdui;

import ru.threedisevenzeror.retrophone.RetroDevice;

import java.io.IOException;

/**
 * The Image class is used to hold graphical image data.
 * Image objects exist independently of the display device.
 * They exist only in off-screen memory and will not be painted on the display unless an explicit
 * command is issued by the application (such as within the paint() method of a Canvas)
 * or when an Image object is placed within a Form screen or an Alert screen and that screen is made current.<br/>
 * <br/>
 * Images are either mutable or immutable depending upon how they are created.
 * Immutable images are generally created by loading image data from resource bundles,
 * from files, or from the network. They may not be modified once created.
 * Mutable images are created in off-screen memory. The application may paint into them after having created
 * a Graphics object expressly for this purpose. Images to be placed within Alert, Choice,
 * Form, or ImageItem objects are required to be immutable because the implementation may
 * use them to update the display at any time, without notifying the application.<br/>
 * <br/>
 * An immutable image may be created from a mutable image through the use
 * of the createImage method. It is possible to create a mutable copy of an
 * immutable image using a technique similar to the following:<br/>
 * <br/>
 * <code>Image source; // the image to be copied<br/>
 * source = Image.createImage(...);<br/>
 * Image copy = Image.createImage(source.getWidth(), source.getHeight());<br/>
 * Graphics g = copy.getGraphics();<br/>
 * g.drawImage(source, 0, 0, TOP|LEFT);</code><br/>
 * <br/>
 * It is also possible to use this technique to create a copy of a subrectangle of an image,
 * by altering the width and height parameters of the createImage() call that
 * creates the destination image and by altering the x and y parameters of the drawImage() call.
 */
public class Image {

    public abstract static class Impl {

        public abstract boolean isMutable();

        public abstract int getHeight();

        public abstract int getWidth();
    }

    private Impl impl;

    Image(Impl impl) {
        this.impl = impl;
    }

    /**
     * Returns actual implementation of this class
     */
    public Impl getImplementation() {
        return impl;
    }

    /**
     * Check if this image is mutable. Mutable images can be modified by rendering to them through a
     * Graphics object obtained from the getGraphics() method of this object.
     *
     * @return true if the image is mutable, false otherwise
     */
    public boolean isMutable() {
        return impl.isMutable();
    }

    /**
     * Gets the height of the image in pixels.
     * @return height of the image
     */
    public int getHeight() {
        return impl.getHeight();
    }

    /**
     * Gets the width of the image in pixels.
     * @return width of the image
     */
    public int getWidth() {
        return impl.getWidth();
    }

    /**
     * Creates a new Graphics object that renders to this image.
     * This image must be mutable; it is illegal to call this method on an immutable image.
     * The mutability of an image may be tested with the isMutable() method.
     *
     * The newly created Graphics object has the following properties:
     *
     * the destination is this Image object;
     * the clip region encompasses the entire Image;
     * the current color is black;
     * the font is the same as the font returned by Font.getDefaultFont();
     * the stroke style is SOLID; and
     * the origin of the coordinate system is located at the upper-left corner of the Image.
     * The lifetime of Graphics objects created using this method is indefinite. They may be used at any time, by any thread.
     * @return a Graphics object with this image as its destination
     *
     * @throws IllegalStateException if the image is immutable
     */
    public Graphics getGraphics() {
        if (isMutable()) {
            return new Graphics(RetroDevice.getInstance()
                    .getGraphics()
                    .getOffScreenGraphicsImpl(this));
        } else {
            throw new IllegalStateException("Cannot get graphics, Image is immutable");
        }
    }

    /**
     * Creates an immutable image which is decoded from the data stored in the
     * specified byte array at the specified offset and length.
     * The data must be in a self-identifying image file format supported by the implementation, such as PNG.
     *
     * The imageoffset and imagelength parameters specify a range of data within the imageData byte array.
     * The imageOffset parameter specifies the offset into the array of the first data byte to be used.
     * It must therefore lie within the range [0..(imageData.length-1)].
     * The imageLength parameter specifies the number of data bytes to be used.
     * It must be a positive integer and it must not cause the range to extend beyond the end of the array.
     * That is, it must be true that imageOffset + imageLength <= imageData.length.
     *
     * This method is intended for use when loading an image from a variety of sources,
     * such as from persistent storage or from the network.
     *
     * @param imageData the array of image data in a supported image format
     * @param imageOffset the offset of the start of the data in the array
     * @param imageLength the length of the data in the array
     * @return the created image
     *
     * @throws ArrayIndexOutOfBoundsException if imageOffset and imageLength specify an invalid range
     * @throws NullPointerException if imageData is null
     * @throws IllegalArgumentException if imageData is incorrectly formatted or otherwise cannot be decoded
     */
    public static Image createImage(byte[] imageData, int imageOffset, int imageLength) {

        if(imageOffset < 0 || imageLength < 0 || imageOffset + imageLength > imageData.length) {
            throw new IllegalStateException("Invalid offset or length: o:" + imageOffset + ", l:" + imageLength);
        }

        return new Image(RetroDevice.getInstance()
                .getGraphics()
                .createImmutableImage(imageData, imageOffset, imageLength));
    }

    /**
     * Creates a new, mutable image for off-screen drawing. Every pixel within the newly created image is white.
     * The width and height of the image must both be greater than zero.
     *
     * @param width the width of the new image, in pixels
     * @param height the height of the new image, in pixels
     * @return the created image
     *
     * @throws IllegalArgumentException if either width or height is zero or less
     */
    public static Image createImage(int width, int height) {

        if(width <= 0 || height <= 0) {
            throw new IllegalStateException("Cannot create image with resolution " + width + "x" + height);
        }

        return new Image(RetroDevice.getInstance()
                .getGraphics()
                .createMutableImage(width, height));
    }

    /**
     * Creates an immutable image from decoded image data obtained from the named resource.
     * The name parameter is a resource name as defined by Class.getResourceAsStream(name).
     *
     * @param name the name of the resource containing the image data in one of the supported image formats
     * @return the created image
     *
     * @throws NullPointerException if name is null
     * @throws IOException if the resource does not exist, the data cannot be loaded, or the image data cannot be decoded
     */
    public static Image createImage(String name) throws IOException {
        return new Image(RetroDevice.getInstance()
                .getGraphics()
                .createImmutableImage(name));
    }

    /**
     * Creates an immutable image from a source image.
     * If the source image is mutable, an immutable copy is created and returned.
     * If the source image is immutable, the implementation may simply return it without creating a new image.
     *
     * This method is useful for placing images drawn off-screen into Alert, Choice, Form, and StringItem objects.
     * The application can create an off-screen image using the createImage(w, h) method, draw into it using a
     * Graphics object obtained with the getGraphics() method, and then create an immutable copy of it with this method.
     * The immutable copy may then be placed into the Alert, Choice, Form, and StringItem objects.
     *
     * @param image the source image to be copied
     * @return the new, immutable image
     */
    public static Image createImage(Image image) {
        if(image.isMutable()) {
            return new Image(RetroDevice.getInstance()
                    .getGraphics()
                    .createImmutableImage(image));
        } else {
            return image;
        }
    }
}

