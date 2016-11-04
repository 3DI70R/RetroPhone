package ru.threedisevenzeror.retrophone;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

/**
 * Created by ThreeDISevenZeroR on 04.11.2016.
 */
public abstract class DisplayDevice {

    public abstract boolean isColor();

    public abstract int getColorCount();

    public abstract int getScreenWidth();

    public abstract int getScreenHeight();

    public abstract Display.Impl getDisplayImpl(MIDlet midlet);
}
