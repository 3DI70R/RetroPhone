package ru.threedisevenzeror.retrophone;

import ru.threedisevenzeror.retrophone.impl.empty.NullDisplayDevice;
import ru.threedisevenzeror.retrophone.impl.empty.NullGraphicsDevice;
import ru.threedisevenzeror.retrophone.impl.empty.NullInputDevice;

/**
 * Created by ThreeDISevenZeroR on 04.11.2016.
 */
public class RetroDevice {

    public static class Builder {

        private GraphicsDevice graphicsDevice;
        private InputDevice inputDevice;
        private DisplayDevice displayDevice;

        public Builder graphicsDevice(GraphicsDevice graphicsDevice) {
            this.graphicsDevice = graphicsDevice;
            return this;
        }

        public Builder inputDevice(InputDevice inputDevice) {
            this.inputDevice = inputDevice;
            return this;
        }

        public Builder displayDevice(DisplayDevice displayDevice) {
            this.displayDevice = displayDevice;
            return this;
        }

        public void createDeviceInstance() {
            RetroDevice device = new RetroDevice();

            device.graphicsDevice = graphicsDevice != null ? graphicsDevice : NullGraphicsDevice.instance;
            device.displayDevice = displayDevice != null ? displayDevice : NullDisplayDevice.instance;
            device.inputDevice = inputDevice != null ? inputDevice : NullInputDevice.instance;

            instance.set(device);
        }
    }

    private static InheritableThreadLocal<RetroDevice> instance = new InheritableThreadLocal<RetroDevice>();

    private GraphicsDevice graphicsDevice;
    private InputDevice inputDevice;
    private DisplayDevice displayDevice;

    public static RetroDevice getInstance() {
        return instance.get();
    }

    public GraphicsDevice getGraphics() {
        return graphicsDevice;
    }

    public InputDevice getInput() {
        return inputDevice;
    }

    public DisplayDevice getDisplay() {
        return displayDevice;
    }
}
