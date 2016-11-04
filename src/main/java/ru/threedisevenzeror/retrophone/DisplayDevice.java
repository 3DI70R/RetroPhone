package ru.threedisevenzeror.retrophone;

import javax.microedition.lcdui.Display;

/**
 * Created by ThreeDISevenZeroR on 04.11.2016.
 */
public class DisplayDevice {

    public boolean isColor() {
        return true;
    }

    public int getColorCount() {
        return 0xffffff;
    }

    public int getScreenWidth() {
        return 176;
    }

    public int getScreenHeight() {
        return 208;
    }

    public Display.DisplayImpl getDisplayImpl() {
        throw new IllegalStateException("Not implemented");
    }
}
