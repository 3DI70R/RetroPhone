package ru.threedisevenzeror.retrophone.impl.empty;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;

/**
 * Created by ThreeDISevenZeroR on 04.11.2016.
 */
public class NullDisplay extends Display.Impl {

    public static final NullDisplay instance = new NullDisplay();

    private NullDisplay() {

    }

    @Override
    public void setCurrent(Displayable nextDisplayable) {

    }

    @Override
    public void setCurrent(Alert alert, Displayable nextDisplayable) {

    }

    @Override
    public void callSerially(Runnable runnable) {
        runnable.run();
    }
}
