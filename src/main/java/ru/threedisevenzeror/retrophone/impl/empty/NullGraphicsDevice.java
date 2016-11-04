package ru.threedisevenzeror.retrophone.impl.empty;

import ru.threedisevenzeror.retrophone.GraphicsDevice;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * Created by ThreeDISevenZeroR on 04.11.2016.
 */
public class NullGraphicsDevice extends GraphicsDevice {

    public static final NullGraphicsDevice instance = new NullGraphicsDevice();

    private NullGraphicsDevice() {

    }

    @Override
    public Font.Impl getFontImpl(int face, int style, int size) {
        return NullFont.instance;
    }

    @Override
    public Graphics.Impl getOffScreenGraphicsImpl(Image image) {
        return NullGraphics.instance;
    }

    @Override
    public Image.Impl createMutableImage(int width, int height) {
        return new NullImage(true, width, height);
    }

    @Override
    public Image.Impl createImmutableImage(byte[] imageData, int imageOffset, int imageLenght) {
        return new NullImage(false, 1, 1);
    }

    @Override
    public Image.Impl createImmutableImage(String name) {
        return new NullImage(false, 1, 1);
    }

    @Override
    public Image.Impl createImmutableImage(Image image) {
        return image.getImplementation();
    }
}
