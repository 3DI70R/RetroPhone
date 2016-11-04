package ru.threedisevenzeror.retrophone;

/**
 * Created by ThreeDISevenZeroR on 04.11.2016.
 */
public abstract class InputDevice {

    public abstract boolean hasPointerEvents();

    public abstract boolean hasPointerMotionEvents();

    public abstract boolean hasRepeatEvents();

    public abstract int getKeyCode(int gameAction);

    public abstract String getKeyName(int keyCode);

    public abstract int getGameAction(int keyCode);
}
