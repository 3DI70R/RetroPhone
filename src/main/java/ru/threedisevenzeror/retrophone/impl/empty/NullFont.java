package ru.threedisevenzeror.retrophone.impl.empty;

import javax.microedition.lcdui.Font;

/**
 * Created by ThreeDISevenZeroR on 04.11.2016.
 */
public class NullFont extends Font.Impl {

    public static final NullFont instance = new NullFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);

    public NullFont(int face, int styleMask, int size) {
        super(face, styleMask, size);
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getBaselinePosition() {
        return 0;
    }

    @Override
    public int charsWidth(char[] ch, int offset, int length) {
        return 0;
    }

    @Override
    public int substringWidth(String str, int offset, int len) {
        return 0;
    }
}
