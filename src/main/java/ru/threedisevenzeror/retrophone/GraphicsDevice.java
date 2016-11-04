package ru.threedisevenzeror.retrophone;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * Created by ThreeDISevenZeroR on 04.11.2016.
 */
public class GraphicsDevice {

    public Font.Impl getFontImpl(int face, int style, int size) {
        throw new IllegalStateException("Not implemented");
    }

    public Graphics.Impl getOffScreenGraphicsImpl(Image image) {
        throw new IllegalStateException("Not implemented");
    }

    public Image.Impl createMutableImage(int width, int height) {
        throw new IllegalStateException("Not implemented");
    }

    public Image.Impl createImmutableImage(byte[] imageData, int imageOffset, int imageLenght) {
        throw new IllegalStateException("Not implemented");
    }

    public Image.Impl createImmutableImage(String name) {
        throw new IllegalStateException("Not implemented");
    }

    public Image.Impl createImmutableImage(Image image) {
        throw new IllegalStateException("Not implemented");
    }
}
