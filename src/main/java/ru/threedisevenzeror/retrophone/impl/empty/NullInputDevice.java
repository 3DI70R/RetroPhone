package ru.threedisevenzeror.retrophone.impl.empty;

import ru.threedisevenzeror.retrophone.InputDevice;

/**
 * Created by ThreeDISevenZeroR on 04.11.2016.
 */
public class NullInputDevice extends InputDevice {

    public static final NullInputDevice instance = new NullInputDevice();

    private NullInputDevice() {

    }

    @Override
    public boolean hasPointerEvents() {
        return false;
    }

    @Override
    public boolean hasPointerMotionEvents() {
        return false;
    }

    @Override
    public boolean hasRepeatEvents() {
        return false;
    }

    @Override
    public int getKeyCode(int gameAction) {
        return 0;
    }

    @Override
    public String getKeyName(int keyCode) {
        return "";
    }

    @Override
    public int getGameAction(int keyCode) {
        return 0;
    }
}
