package javax.microedition.lcdui;

import ru.threedisevenzeror.retrophone.RetroDevice;

/**
 * The Font class represents fonts and font metrics.
 * Fonts cannot be created by applications. Instead, applications query for fonts based on font
 * attributes and the system will attempt to provide a font that matches the requested attributes as closely as possible.<br/>
 * <br/>
 * A Font's attributes are style, size, and face. Values for attributes must be specified in terms
 * of symbolic constants. Values for the style attribute may be combined using the logical
 * OR operator, whereas values for the other attributes may not be combined. For example, the value<br/>
 * <br/>
 * <code>STYLE_BOLD | STYLE_ITALIC</code><br/>
 * <br/>
 * may be used to specify a bold-italic font; however<br/>
 * <br/>
 * <code>SIZE_LARGE | SIZE_SMALL</code><br/>
 * <br/>
 * is illegal.<br/>
 * <br/>
 * The values of these constants are arranged so that zero is valid for each attribute and
 * can be used to specify a reasonable default font for the system. For clarity of programming,
 * the following symbolic constants are provided and are defined to have values of zero:<br/>
 * <br/>
 * <code>STYLE_PLAIN
 * SIZE_MEDIUM
 * FACE_SYSTEM</code><br/>
 * <br/>
 * Values for other attributes are arranged to have disjoint bit patterns
 * in order to raise errors if they are inadvertently misused
 * (for example, using FACE_PROPORTIONAL where a style is required).
 * However, the values for the different attributes are not intended to be combined with each other.
 */
public final class Font {

    public abstract static class Impl {

        protected int styleMask;
        protected int size;
        protected int face;

        public abstract int getHeight();

        public abstract int getBaselinePosition();

        public abstract int charsWidth(char[] ch, int offset, int length);

        public abstract int substringWidth(String str, int offset, int len);

        public int getStyle() {
            return styleMask;
        }

        public int getSize() {
            return size;
        }

        public int getFace() {
            return face;
        }

        public boolean isPlain() {
            return styleMask == Font.STYLE_PLAIN;
        }

        public boolean isBold() {
            return (styleMask & Font.STYLE_BOLD) != 0;
        }

        public boolean isItalic() {
            return (styleMask & Font.STYLE_ITALIC) != 0;
        }

        public boolean isUnderlined() {
            return (styleMask & Font.STYLE_UNDERLINED) != 0;
        }

        public int charWidth(char ch) {
            return charsWidth(new char[]{ch}, 0, 1);
        }

        public int stringWidth(String str) {
            return substringWidth(str, 0, str.length());
        }
    }

    /**
     * The plain style constant. This may be combined with the other style constants for mixed styles.
     */
    public static final int STYLE_PLAIN = 0;

    /**
     * The bold style constant. This may be combined with the other style constants for mixed styles.
     */
    public static final int STYLE_BOLD = 1;

    /**
     * The italicized style constant. This may be combined with the other style constants for mixed styles.
     */
    public static final int STYLE_ITALIC = 2;

    /**
     * The underlined style constant. This may be combined with the other style constants for mixed styles.
     */
    public static final int STYLE_UNDERLINED = 4;

    /**
     * The "small" system-dependent font size.
     */
    public static final int SIZE_SMALL = 8;

    /**
     * The "medium" system-dependent font size.
     */
    public static final int SIZE_MEDIUM = 0;

    /**
     * The "large" system-dependent font size.
     */
    public static final int SIZE_LARGE = 16;

    /**
     * The "system" font face.
     */
    public static final int FACE_SYSTEM = 0;

    /**
     * The "monospace" font face.
     */
    public static final int FACE_MONOSPACE = 32;

    /**
     * The "proportional" font face.
     */
    public static final int FACE_PROPORTIONAL = 64;

    private static Font defaultFont;
    private Impl delegate;

    private Font(int face, int style, int size) {
        this.delegate = RetroDevice.getInstance()
                .getGraphics()
                .getFontImpl(face, style, size);
    }

    /**
     * Returns actual implementation of this class
     */
    public Impl getImplementation() {
        return delegate;
    }

    /**
     * Gets the style of the font. The value is an OR'ed combination of
     * STYLE_BOLD, STYLE_ITALIC, and STYLE_UNDERLINED; or the value is zero (STYLE_PLAIN).
     * @return style of the current font
     */
    public int getStyle() {
        return delegate.getStyle();
    }

    /**
     * Gets the size of the font.
     * @return one of SIZE_SMALL, SIZE_MEDIUM, SIZE_LARGE
     */
    public int getSize() {
        return delegate.getSize();
    }

    /**
     * Gets the face of the font.
     * @return one of FACE_SYSTEM, FACE_PROPORTIONAL, FACE_MONOSPACE
     */
    public int getFace() {
        return delegate.getFace();
    }

    /**
     * Returns true if the font is plain.
     * @return true if font is plain
     */
    public boolean isPlain() {
        return delegate.isPlain();
    }

    /**
     * Returns true if the font is bold.
     * @return true if font is bold
     */
    public boolean isBold() {
        return delegate.isBold();
    }

    /**
     * Returns true if the font is italic.
     * @return true if font is italic
     */
    public boolean isItalic() {
        return delegate.isItalic();
    }

    /**
     * Returns true if the font is underlined.
     * @return true if font is underlined
     */
    public boolean isUnderlined() {
        return delegate.isUnderlined();
    }

    /**
     * Gets the standard height of a line of text in this font.
     * This value includes sufficient spacing to ensure that lines of text painted this distance from anchor point
     * to anchor point are spaced as intended by the font designer and the device.
     * This extra space (leading) occurs below the text.
     * @return standard height of a line of text in this font (a non-negative value)
     */
    public int getHeight() {
        return delegate.getHeight();
    }

    /**
     * Gets the distance in pixels from the top of the text to the text's baseline.
     * @return the distance in pixels from the top of the text to the text's baseline
     */
    public int getBaselinePosition() {
        return delegate.getBaselinePosition();
    }

    /**
     * Gets the advance width of the specified character in this Font.
     * The advance width is the amount by which the current point is moved from one character to the next in a
     * line of text, and thus includes proper inter-character spacing. This spacing occurs to the right of the character.
     * @param ch the character to be measured
     * @return the total advance width (a non-negative value)
     */
    public int charWidth(char ch) {
        return delegate.charWidth(ch);
    }

    /**
     * Returns the advance width of the characters in ch, starting at the specified offset and for the specified number
     * of characters (length). The advance width is the amount by which the current point is
     * moved from one character to the next in a line of text.
     * The offset and length parameters must specify a valid range of characters within the character array ch.
     * The offset parameter must be within the range [0..(ch.length)]. The length parameter
     * must be a non-negative integer such that (offset + length) <= ch.length.
     * @param ch The array of characters
     * @param offset The index of the first character to measure
     * @param length The number of characters to measure
     * @return the width of the character range
     *
     * @throws ArrayIndexOutOfBoundsException if offset and length specify an invalid range
     * @throws NullPointerException - if ch is null
     */
    public int charsWidth(char[] ch, int offset, int length) {
        return delegate.charsWidth(ch, offset, length);
    }

    /**
     * Gets the total advance width for showing the specified String in this Font.
     * The advance width is the amount by which the current point is moved from one character to the next in a line of text.
     * @param str the String to be measured.
     * @return the total advance width
     *
     * @throws NullPointerException if str is null
     */
    public int stringWidth(String str) {
        return delegate.stringWidth(str);
    }

    /**
     * Gets the total advance width for showing the specified substring in this Font.
     * The advance width is the amount by which the current point is moved from one character to the next in a line of text.
     * The offset and length parameters must specify a valid range of characters within str.
     * The offset parameter must be within the range [0..(str.length())]. The length parameter must be a non-negative integer such that (offset + length) <= str.length().
     * @param str the String to be measured.
     * @param offset zero-based index of first character in the substring
     * @param len length of the substring.
     * @return the total advance width
     *
     * @throws StringIndexOutOfBoundsException if offset and length specify an invalid range
     * @throws NullPointerException if str is null
     */
    public int substringWidth(String str, int offset, int len) {
        return delegate.substringWidth(str, offset, len);
    }

    /**
     * Gets the default font of the system.
     */
    public static Font getDefaultFont() {
        if(defaultFont == null) {
            defaultFont = getFont(STYLE_PLAIN, SIZE_MEDIUM, FACE_SYSTEM);
        }

        return defaultFont;
    }

    /**
     * Obtains an object representing a font having the specified face, style, and size.
     * If a matching font does not exist, the system will attempt to provide the closest match.
     * This method always returns a valid font object, even if it is not a close match to the request.
     *
     * @param face one of FACE_SYSTEM, FACE_MONOSPACE, or FACE_PROPORTIONAL
     * @param style STYLE_PLAIN, or a combination of STYLE_BOLD, STYLE_ITALIC, and STYLE_UNDERLINED
     * @param size one of SIZE_SMALL, SIZE_MEDIUM, or SIZE_LARGE
     * @return instance the nearest font found
     *
     * @throws IllegalArgumentException if face, style, or size are not legal values
     */
    public static Font getFont(int face, int style, int size) {
        return new Font(face, style, size);
    }
}

