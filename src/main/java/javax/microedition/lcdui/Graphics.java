package javax.microedition.lcdui;

public class Graphics {

    public abstract static class Impl {

        private int color;
        private int xTranslation;
        private int yTranslation;
        private int strokeStyle;
        private Font font;

        public abstract int getClipHeight();

        public abstract int getClipWidth();

        public abstract int getClipX();

        public abstract int getClipY();

        public abstract int getWidth();

        public abstract int getHeight();

        public int getColor() {
            return color;
        }

        public void setColor(int RGB) {
            this.color = RGB;
        }

        public void setColor(int red, int green, int blue) {
            if(red < 0 || red > 255 || green < 0 || green > 255 || blue < 0 || blue > 255) {
                throw new IllegalArgumentException("One or more color components is outside of range 0-255");
            }
            setColor((0xFF << 24) | (red << 16) | (green << 8) | blue);
        }

        public int getGrayScale() {
            float r = getRedComponent() / 255.0f;
            float g = getGreenComponent() / 255.0f;
            float b = getBlueComponent() / 255.0f;
            return (int) ((0.30f * r + 0.59f * g +  0.11f * b) * 255);
        }

        public void setGrayScale(int value) {
            setColor(value, value, value);
        }

        public int getBlueComponent() {
            return (getColor()) & 0x000000FF;
        }

        public int getGreenComponent() {
            return (getColor() >> 8) & 0x000000FF;
        }

        public int getRedComponent() {
            return (getColor() >> 16) & 0x000000FF;
        }

        public int getStrokeStyle() {
            return strokeStyle;
        }

        public int getTranslateX() {
            return xTranslation;
        }

        public int getTranslateY() {
            return yTranslation;
        }

        public Font getFont() {
            return font;
        }

        public abstract void clipRect(int x, int y, int width, int height);

        public abstract void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle);

        public void drawChar(char character, int x, int y, int anchor) {
            drawString(String.valueOf(character), x, y, anchor);
        }

        public abstract void drawChars(char[] data, int offset, int length, int x, int y, int anchor);

        public abstract void drawImage(Image img, int x, int y, int anchor);

        public abstract void drawLine(int x1, int y1, int x2, int y2);

        public abstract void drawRect(int x, int y, int width, int height);

        public abstract void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight);

        public void drawString(String str, int x, int y, int anchor) {
            drawSubstring(str, 0, str.length(), x, y, anchor);
        }

        public abstract void drawSubstring(String str, int offset, int len, int x, int y, int anchor);

        public abstract void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle);

        public abstract void fillRect(int x, int y, int width, int height);

        public abstract void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight);

        public abstract void setClip(int x, int y, int width, int height);

        public void setFont(Font font) {
            if(font == null) {
                this.font = Font.getDefaultFont();
            } else {
                this.font = font;
            }
        }

        public void setStrokeStyle(int style) {
            if(style != Graphics.SOLID && style != Graphics.DOTTED) {
                throw new IllegalArgumentException("Unknown stroke style " + style);
            } else {
                this.strokeStyle = style;
            }
        }

        public void translate(int x, int y) {
            xTranslation += x;
            yTranslation += y;
        }
    }

    /**
     *  Constant for the SOLID stroke style.
     */
    public static final int SOLID = 0;

    /**
     * Constant for the DOTTED stroke style.
     */
    public static final int DOTTED = 1;

    /**
     * Constant for centering text and images horizontally around the anchor point
     */
    public static final int HCENTER = 1;

    /**
     * Constant for positioning the anchor point of text and images above the text or image.
     */
    public static final int TOP = 16;

    /**
     * Constant for centering images vertically around the anchor point.
     */
    public static final int VCENTER = 2;

    /**
     * Constant for positioning the anchor point of text and images below the text or image.
     */
    public static final int BOTTOM = 32;

    /**
     * Constant for positioning the anchor point of text and images to the left of the text or image.
     */
    public static final int LEFT = 4;

    /**
     * Constant for positioning the anchor point at the baseline of text.
     */
    public static final int BASELINE = 64;

    /**
     * Constant for positioning the anchor point of text and images to the right of the text or image.
     */
    public static final int RIGHT = 8;

    private Impl impl;

    Graphics(Impl impl) {
        this.impl = impl;

        setClip(0, 0, impl.getWidth(), impl.getHeight());
        setColor(0, 0, 0);
        setFont(Font.getDefaultFont());
        setStrokeStyle(Graphics.SOLID);
        translate(0, 0);
    }

    /**
     * Returns actual implementation of this class
     */
    public Impl getImplementation() {
        return impl;
    }

    /**
     * Gets the height of the current clipping area.
     *
     * @return height of the current clipping area.
     */
    public int getClipHeight() {
        return impl.getClipHeight();
    }

    /**
     * Gets the width of the current clipping area.
     *
     * @return width of the current clipping area.
     */
    public int getClipWidth() {
        return impl.getClipWidth();
    }

    /**
     * Gets the X offset of the current clipping area, relative to the coordinate system origin of this graphics context.
     * Separating the getClip operation into two methods returning integers is more performance and memory efficient
     * than one getClip() call returning an object.
     *
     * @return X offset of the current clipping area
     */
    public int getClipX() {
        return impl.getClipX();
    }

    /**
     * Gets the Y offset of the current clipping area, relative to the coordinate system origin of this graphics context.
     * Separating the getClip operation into two methods returning integers is more performance and memory efficient
     * than one getClip() call returning an object.
     *
     * @return Y offset of the current clipping area
     */
    public int getClipY() {
        return impl.getClipY();
    }

    /**
     * Gets the current color.
     * @return an integer in parentForm 0x00RRGGBB
     */
    public int getColor() {
        return impl.getColor();
    }

    /**
     * Sets the current color to the specified RGB values.
     * All subsequent rendering operations will use this specified color.
     * The RGB value passed in is interpreted with the least significant eight bits giving the blue component,
     * the next eight more significant bits giving the green component, and the next eight more significant
     * bits giving the red component. That is to say, the color component is specified in the
     * parentForm of 0x00RRGGBB. The high order byte of this value is ignored.
     *
     * @param RGB the color being set
     */
    public void setColor(int RGB) {
        impl.setColor(RGB);
    }

    /**
     * Sets the current color to the specified RGB values.
     * All subsequent rendering operations will use this specified color.
     *
     * @param red The red component of the color being set in range 0-255.
     * @param green The green component of the color being set in range 0-255.
     * @param blue The blue component of the color being set in range 0-255.
     *
     * @throws IllegalArgumentException if any of the color components are outside of range 0-255.
     */
    public void setColor(int red, int green, int blue) {
        impl.setColor(red, green, blue);
    }

    /**
     * Gets the current grayscale value of the color being used for rendering operations.
     * If the color was set by setGrayScale(), that value is simply returned.
     * If the color was set by one of the methods that allows setting of the red, green, and blue components,
     * the value returned is computed from the RGB color components (possibly in a device-specific fashion)
     * that best approximates the brightness of that color.
     *
     * @return integer value in range 0-255
     */
    public int getGrayScale() {
        return impl.getGrayScale();
    }

    /**
     * Sets the current grayscale to be used for all subsequent rendering operations.
     * For monochrome displays, the behavior is clear. For color displays, this sets the color
     * for all subsequent drawing operations to be a gray color equivalent to the value passed in.
     * The value must be in the range 0-255.
     * @param value the desired grayscale value
     */
    public void setGrayScale(int value) {
        impl.setGrayScale(value);
    }

    /**
     * Gets the blue component of the current color.
     * @return integer value in range 0-255
     */
    public int getBlueComponent() {
        return impl.getBlueComponent();
    }

    /**
     * Gets the green component of the current color.
     * @return integer value in range 0-255
     */
    public int getGreenComponent() {
        return impl.getGreenComponent();
    }

    /**
     * Gets the red component of the current color.
     * @return integer value in range 0-255
     */
    public int getRedComponent() {
        return impl.getRedComponent();
    }

    /**
     * Gets the stroke style used for drawing operations.
     * @return stroke style, SOLID or DOTTED
     */
    public int getStrokeStyle() {
        return impl.getStrokeStyle();
    }

    /**
     * Gets the X coordinate of the translated origin of this graphics context.
     * @return X of current origin
     */
    public int getTranslateX() {
        return impl.getTranslateX();
    }

    /**
     * Gets the Y coordinate of the translated origin of this graphics context.
     * @return Y of current origin
     */
    public int getTranslateY() {
        return impl.getTranslateY();
    }

    /**
     * Gets the current font.
     * @return current font
     */
    public Font getFont() {
        return impl.getFont();
    }

    /**
     * Intersects the current clip with the specified rectangle.
     * The resulting clipping area is the intersection of the current clipping area
     * and the specified rectangle. This method can only be used to make the current clip smaller.
     * To set the current clip larger, use the setClip method.
     * Rendering operations have no effect outside of the clipping area.
     *
     * @param x the x coordinate of the rectangle to intersect the clip with
     * @param y the y coordinate of the rectangle to intersect the clip with
     * @param width the width of the rectangle to intersect the clip with
     * @param height the height of the rectangle to intersect the clip with
     */
    public void clipRect(int x, int y, int width, int height) {
        impl.clipRect(x, y, width, height);
    }

    /**
     * Draws the outline of a circular or elliptical arc covering the specified rectangle, using the current color and stroke style.
     * The resulting arc begins at startAngle and extends for arcAngle degrees, using the current color.
     * Angles are interpreted such that 0 degrees is at the 3 o'clock position.
     * A positive value indicates a counter-clockwise rotation while a negative value indicates a clockwise rotation.
     *
     * The center of the arc is the center of the rectangle whose origin is (x, y) and whose size is
     * specified by the width and height arguments.
     *
     * The resulting arc covers an area width + 1 pixels wide by height + 1 pixels tall.
     * If either width or height is less than zero, nothing is drawn.
     *
     * The angles are specified relative to the non-square extents of the bounding rectangle such that 45 degrees
     * always falls on the line from the center of the ellipse to the upper right corner of the bounding rectangle.
     * As a result, if the bounding rectangle is noticeably longer in one axis than the other,
     * the angles to the start and end of the arc segment will be skewed farther along the longer axis of the bounds.

     * @param x the x coordinate of the upper-left corner of the arc to be drawn.
     * @param y the y coordinate of the upper-left corner of the arc to be drawn.
     * @param width the width of the arc to be drawn
     * @param height the height of the arc to be drawn
     * @param startAngle the beginning angle
     * @param arcAngle the angular extent of the arc, relative to the start angle.
     */
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        impl.drawArc(x, y, width, height, startAngle, arcAngle);
    }

    /**
     * Draws the specified character using the current font and color.
     *
     * @param character the character to be drawn
     * @param x the x coordinate of the anchor point
     * @param y the y coordinate of the anchor point
     * @param anchor the anchor point for positioning the text
     *
     * @throws IllegalArgumentException if anchor is not a legal value
     */
    public void drawChar(char character, int x, int y, int anchor) {
        impl.drawChar(character, x, y, anchor);
    }

    /**
     * Draws the specified characters using the current font and color.
     *
     * @param data the array of characters to be drawn
     * @param offset the start offset in the data
     * @param length the number of characters to be drawn
     * @param x the x coordinate of the anchor point
     * @param y the y coordinate of the anchor point
     * @param anchor the anchor point for positioning the text; see anchor points
     *
     * @throws ArrayIndexOutOfBoundsException if offset and length do not specify a valid range within the data array
     * @throws IllegalArgumentException if anchor is not a legal value
     * @throws NullPointerException if data is null
     */
    public void drawChars(char[] data, int offset, int length, int x, int y, int anchor) {
        impl.drawChars(data, offset, length, x, y, anchor);
    }

    /**
     * Draws the specified image by using the anchor point.
     * The image can be drawn in different positions relative to the anchor point
     * by passing the appropriate position constants.
     *
     * @param img the specified image to be drawn
     * @param x the x coordinate of the anchor point
     * @param y the y coordinate of the anchor point
     * @param anchor the anchor point for positioning the image
     *
     * @throws IllegalArgumentException if anchor is not a legal value
     * @throws NullPointerException if img is null
     */
    public void drawImage(Image img, int x, int y, int anchor) {
        impl.drawImage(img, x, y, anchor);
    }

    /**
     * Draws a line between the coordinates (x1,y1) and (x2,y2) using the current color and stroke style.
     *
     * @param x1 the x coordinate of the start of the line
     * @param y1 the y coordinate of the start of the line
     * @param x2 the x coordinate of the end of the line
     * @param y2 the y coordinate of the end of the line
     */
    public void drawLine(int x1, int y1, int x2, int y2) {
        impl.drawLine(x1, y1, x2, y2);
    }

    /**
     * Draws the outline of the specified rectangle using the current color and stroke style.
     * The resulting rectangle will cover an area (width + 1)
     * pixels wide by (height + 1) pixels tall. If either width or height is less than zero, nothing is drawn.
     * @param x the x coordinate of the rectangle to be drawn
     * @param y the y coordinate of the rectangle to be drawn
     * @param width the width of the rectangle to be drawn
     * @param height the height of the rectangle to be drawn
     */
    public void drawRect(int x, int y, int width, int height) {
        impl.drawRect(x, y, width, height);
    }

    /**
     * Draws the outline of the specified rounded corner rectangle using the current color and stroke style.
     * The resulting rectangle will cover an area (width + 1) pixels wide by (height + 1) pixels tall.
     * If either width or height is less than zero, nothing is drawn.
     *
     * @param x the x coordinate of the rectangle to be drawn
     * @param y the y coordinate of the rectangle to be drawn
     * @param width the width of the rectangle to be drawn
     * @param height the height of the rectangle to be drawn
     * @param arcWidth the horizontal diameter of the arc at the four corners
     * @param arcHeight the vertical diameter of the arc at the four corners
     */
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        impl.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    /**
     * Draws the specified String using the current font and color. The x,y position is the position of the anchor point.
     * @param str the String to be drawn
     * @param x the x coordinate of the anchor point
     * @param y the y coordinate of the anchor point
     * @param anchor the anchor point for positioning the text
     *
     * @throws NullPointerException if str is null
     * @throws IllegalArgumentException if anchor is not a legal value
     */
    public void drawString(String str, int x, int y, int anchor) {
        impl.drawString(str, x, y, anchor);
    }

    /**
     * Draws the specified String using the current font and color. The x,y position is the position of the anchor point.
     *
     * @param str the String to be drawn
     * @param offset zero-based index of first character in the substring
     * @param len length of the substring
     * @param x the x coordinate of the anchor point
     * @param y the y coordinate of the anchor point
     * @param anchor the anchor point for positioning the text
     *
     * @throws StringIndexOutOfBoundsException if offset and length do not specify a valid range within the String str
     * @throws IllegalArgumentException if anchor is not a legal value
     * @throws NullPointerException if str is null
     */
    public void drawSubstring(String str, int offset, int len, int x, int y, int anchor) {
        impl.drawSubstring(str, offset, len, x, y, anchor);
    }

    /**
     * Fills a circular or elliptical arc covering the specified rectangle.
     * The resulting arc begins at startAngle and extends for arcAngle degrees.
     * Angles are interpreted such that 0 degrees is at the 3 o'clock position.
     * A positive value indicates a counter-clockwise rotation while a negative value indicates a clockwise rotation.
     *
     * The center of the arc is the center of the rectangle whose origin is (x, y)
     * and whose size is specified by the width and height arguments.
     *
     * If either width or height is zero or less, nothing is drawn.
     *
     * The filled region consists of the "pie wedge" region bounded by the arc segment as if drawn by drawArc(),
     * the radius extending from the center to this arc at startAngle degrees,
     * and radius extending from the center to this arc at startAngle + arcAngle degrees.
     *
     * The angles are specified relative to the non-square extents of the bounding rectangle such that
     * 45 degrees always falls on the line from the center of the ellipse to the upper right corner of the bounding rectangle.
     * As a result, if the bounding rectangle is noticeably longer in one axis than the other, the angles
     * to the start and end of the arc segment will be skewed farther along the longer axis of the bounds.
     *
     * @param x the x coordinate of the upper-left corner of the arc to be filled.
     * @param y the y coordinate of the upper-left corner of the arc to be filled.
     * @param width the width of the arc to be filled
     * @param height the height of the arc to be filled
     * @param startAngle the beginning angle.
     * @param arcAngle the angular extent of the arc, relative to the start angle.
     */
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
        impl.fillArc(x, y, width, height, startAngle, arcAngle);
    }

    /**
     * Fills the specified rectangle with the current color. If either width or height is zero or less, nothing is drawn.
     *
     * @param x the x coordinate of the rectangle to be filled
     * @param y the y coordinate of the rectangle to be filled
     * @param width the width of the rectangle to be filled
     * @param height the height of the rectangle to be filled
     */
    public void fillRect(int x, int y, int width, int height) {
        impl.fillRect(x, y, width, height);
    }

    /**
     * Fills the specified rounded corner rectangle with the current color.
     * If either width or height is zero or less, nothing is drawn.
     *
     * @param x the x coordinate of the rectangle to be filled
     * @param y the y coordinate of the rectangle to be filled
     * @param width the width of the rectangle to be filled
     * @param height the height of the rectangle to be filled
     * @param arcWidth the horizontal diameter of the arc at the four corners
     * @param arcHeight the vertical diameter of the arc at the four corners
     */
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
        impl.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
    }

    /**
     * Sets the current clip to the rectangle specified by the given coordinates.
     * Rendering operations have no effect outside of the clipping area.
     *
     * @param x the x coordinate of the new clip rectangle
     * @param y the y coordinate of the new clip rectangle
     * @param width the width of the new clip rectangle
     * @param height the height of the new clip rectangle
     */
    public void setClip(int x, int y, int width, int height)  {
        impl.setClip(x, y, width, height);
    }

    /**
     * Sets the font for all subsequent text rendering operations.
     * If font is null, it is equivalent to setFont(Font.getDefaultFont()).
     *
     * @param font the specified font
     */
    public void setFont(Font font) {
        impl.setFont(font);
    }

    /**
     * Sets the stroke style used for drawing lines, arcs, rectangles, and rounded rectangles.
     * This does not affect fill, text, and image operations.
     *
     * @param style can be SOLID or DOTTED
     */
    public void setStrokeStyle(int style) {
        impl.setStrokeStyle(style);
    }

    /**
     * Translates the origin of the graphics context to the point (x, y) in the current coordinate system.
     * All coordinates used in subsequent rendering operations on this graphics context will be relative to this new origin.
     *
     * The effect of calls to translate() are cumulative. For example,
     * calling translate(1, 2) and then translate(3, 4) results in a translation of (4, 6).
     *
     * The application can set an absolute origin (ax, ay) using the following technique:
     *
     * g.translate(ax - g.getTranslateX(), ay - g.getTranslateY())
     *
     * @param x the x coordinate of the new translation origin
     * @param y the y coordinate of the new translation origin
     */
    public void translate(int x, int y) {
        impl.translate(x, y);
    }
}

