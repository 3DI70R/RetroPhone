package ru.threedisevenzeror.retrophone;

/**
 * Created by ThreeDISevenZeroR on 04.11.2016.
 */
public class RetroDevice {

    public static RetroDevice getInstance() {
        throw new IllegalStateException("Not implemented");
    }


    public GraphicsDevice getGraphics() {
        throw new IllegalStateException("Not implemented");
    }

    public InputDevice getInput() {
        throw new IllegalStateException("Not implemented");
    }

    public DisplayDevice getDisplay() {
        throw new IllegalStateException("Not implemented");
    }
}
