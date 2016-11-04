package ru.threedisevenzeror.retrophone.impl;

import ru.threedisevenzeror.retrophone.InputDevice;

import javax.microedition.lcdui.Canvas;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ThreeDISevenZeroR on 04.11.2016.
 */
public class GenericInputDevice extends InputDevice {

    public static class Builder {

        private boolean hasPointerEvents;
        private boolean hasPointerMotionEvents;
        private boolean hasRepeatEvents;
        private Map<Integer, String> keyNames;
        private Map<Integer, Integer> keyCodeToAction;
        private Map<Integer, Integer> actionToKeyCode;

        public Builder() {
            keyNames = new HashMap<Integer, String>();
            keyCodeToAction = new HashMap<Integer, Integer>();
            actionToKeyCode = new HashMap<Integer, Integer>();

            hasPointerEvents(false);
            hasPointerMotionEvents(false);
            hasRepeatEvents(false);

            setKeyCodeName(Canvas.KEY_NUM0, "Key 0");
            setKeyCodeName(Canvas.KEY_NUM1, "Key 1");
            setKeyCodeName(Canvas.KEY_NUM2, "Key 2");
            setKeyCodeName(Canvas.KEY_NUM3, "Key 3");
            setKeyCodeName(Canvas.KEY_NUM4, "Key 4");
            setKeyCodeName(Canvas.KEY_NUM5, "Key 5");
            setKeyCodeName(Canvas.KEY_NUM6, "Key 6");
            setKeyCodeName(Canvas.KEY_NUM7, "Key 7");
            setKeyCodeName(Canvas.KEY_NUM8, "Key 8");
            setKeyCodeName(Canvas.KEY_NUM9, "Key 9");
            setKeyCodeName(Canvas.KEY_POUND, "Pound");
            setKeyCodeName(Canvas.KEY_STAR, "Star");

            setKeyCodeAction(Canvas.KEY_NUM1, Canvas.GAME_A);
            setKeyCodeAction(Canvas.KEY_NUM2, Canvas.UP);
            setKeyCodeAction(Canvas.KEY_NUM3, Canvas.GAME_B);
            setKeyCodeAction(Canvas.KEY_NUM4, Canvas.LEFT);
            setKeyCodeAction(Canvas.KEY_NUM5, Canvas.FIRE);
            setKeyCodeAction(Canvas.KEY_NUM6, Canvas.RIGHT);
            setKeyCodeAction(Canvas.KEY_NUM7, Canvas.GAME_C);
            setKeyCodeAction(Canvas.KEY_NUM8, Canvas.DOWN);
            setKeyCodeAction(Canvas.KEY_NUM9, Canvas.GAME_D);
        }

        public Builder hasPointerEvents(boolean hasPointerEvents) {
            this.hasPointerEvents = hasPointerEvents;
            return this;
        }

        public Builder hasPointerMotionEvents(boolean hasPointerMotionEvents) {
            this.hasPointerMotionEvents = hasPointerMotionEvents;
            return this;
        }

        public Builder hasRepeatEvents(boolean hasRepeatEvents) {
            this.hasRepeatEvents = hasRepeatEvents;
            return this;
        }

        public Builder setKeyCodeNamesMap(Map<Integer, String> keyCodeNames) {

            keyNames = new HashMap<Integer, String>();

            if(keyCodeNames != null) {
                for(Map.Entry<Integer, String> e : keyCodeNames.entrySet()) {
                    setKeyCodeName(e.getKey(), e.getValue());
                }
            }

            return this;
        }

        public Builder setKeyCodeActionMap(Map<Integer, Integer> keyCodeActions) {

            keyCodeToAction = new HashMap<Integer, Integer>();
            actionToKeyCode = new HashMap<Integer, Integer>();

            if(keyCodeActions != null) {
                for(Map.Entry<Integer, Integer> e : keyCodeActions.entrySet()) {
                    setKeyCodeAction(e.getKey(), e.getValue());
                }
            }

            return this;
        }

        public Builder setKeyCodeName(int keyCode, String name) {
            keyNames.put(keyCode, name);
            return this;
        }

        public Builder setKeyCodeAction(int keyCode, int action) {
            keyCodeToAction.put(keyCode, action);
            actionToKeyCode.put(action, keyCode);
            return this;
        }

        public GenericInputDevice create() {
            GenericInputDevice device = new GenericInputDevice();

            device.hasPointerEvents = hasPointerEvents;
            device.hasPointerMotionEvents = hasPointerMotionEvents;
            device.hasRepeatEvents = hasRepeatEvents;
            device.keyNames = new HashMap<Integer, String>(keyNames);
            device.keyCodeToAction = new HashMap<Integer, Integer>(keyCodeToAction);
            device.actionToKeyCode = new HashMap<Integer, Integer>(actionToKeyCode);

            return device;
        }
    }

    private boolean hasPointerEvents;
    private boolean hasPointerMotionEvents;
    private boolean hasRepeatEvents;
    private Map<Integer, String> keyNames;
    private Map<Integer, Integer> keyCodeToAction;
    private Map<Integer, Integer> actionToKeyCode;

    private GenericInputDevice() {
    }

    @Override
    public boolean hasPointerEvents() {
        return hasPointerEvents;
    }

    @Override
    public boolean hasPointerMotionEvents() {
        return hasPointerMotionEvents;
    }

    @Override
    public boolean hasRepeatEvents() {
        return hasRepeatEvents;
    }

    @Override
    public int getKeyCode(int gameAction) {
        Integer keyCode = actionToKeyCode.get(gameAction);
        if(keyCode != null) {
            return keyCode;
        } else {
            return 0;
        }
    }

    @Override
    public String getKeyName(int keyCode) {
        return keyNames.get(keyCode);
    }

    @Override
    public int getGameAction(int keyCode) {
        Integer action = keyCodeToAction.get(keyCode);
        if(action != null) {
            return action;
        } else {
            return 0;
        }
    }
}
