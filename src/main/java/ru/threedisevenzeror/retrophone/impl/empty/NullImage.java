package ru.threedisevenzeror.retrophone.impl.empty;

import javax.microedition.lcdui.Image;

/**
 * Created by ThreeDISevenZeroR on 04.11.2016.
 */
public class NullImage extends Image.Impl {

    private boolean isMutable;
    private int height;
    private int width;

    public NullImage(boolean isMutable, int width, int height) {
        this.isMutable = isMutable;
        this.width = width;
        this.height = height;
    }

    @Override
    public boolean isMutable() {
        return isMutable;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }
}
