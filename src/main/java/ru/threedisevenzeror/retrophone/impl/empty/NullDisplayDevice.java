package ru.threedisevenzeror.retrophone.impl.empty;

import ru.threedisevenzeror.retrophone.DisplayDevice;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;

/**
 * Created by ThreeDISevenZeroR on 04.11.2016.
 */
public class NullDisplayDevice extends DisplayDevice {

    public static final NullDisplayDevice instance = new NullDisplayDevice();

    private NullDisplayDevice() {

    }

    @Override
    public boolean isColor() {
        return true;
    }

    @Override
    public int getColorCount() {
        return 0xffffff;
    }

    @Override
    public int getScreenWidth() {
        return 96;
    }

    @Override
    public int getScreenHeight() {
        return 54;
    }

    @Override
    public Display.Impl getDisplayImpl(MIDlet midlet) {
        return NullDisplay.instance;
    }
}
