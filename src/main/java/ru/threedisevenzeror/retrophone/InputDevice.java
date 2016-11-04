package ru.threedisevenzeror.retrophone;

/**
 * Created by ThreeDISevenZeroR on 04.11.2016.
 */
public class InputDevice {

    public boolean hasPointerEvents() {
        return true;
    }

    public boolean hasPointerMotionEvents() {
        return true;
    }

    public boolean hasRepeatEvents() {
        return true;
    }

    public int getKeyCode(int gameAction) {
        return 0;
    }

    public String getKeyName(int keyCode) {
        return "KEY_" + String.valueOf(keyCode);
    }

    public int getGameAction(int keyCode) {
        return 0;
    }
}
