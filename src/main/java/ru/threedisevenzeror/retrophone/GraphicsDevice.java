package ru.threedisevenzeror.retrophone;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * Created by ThreeDISevenZeroR on 04.11.2016.
 */
public abstract class GraphicsDevice {

    public abstract Font.Impl getFontImpl(int face, int style, int size);

    public abstract Graphics.Impl getOffScreenGraphicsImpl(Image image);

    public abstract Image.Impl createMutableImage(int width, int height);

    public abstract Image.Impl createImmutableImage(byte[] imageData, int imageOffset, int imageLenght);

    public abstract Image.Impl createImmutableImage(String name);

    public abstract Image.Impl createImmutableImage(Image image);
}
