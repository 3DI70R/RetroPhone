package ru.threedisevenzeror.retrophone.impl.empty;

import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * Created by ThreeDISevenZeroR on 04.11.2016.
 */
public class NullGraphics extends Graphics.Impl {

    public static final NullGraphics instance = new NullGraphics();

    private NullGraphics() {

    }

    @Override
    public int getClipHeight() {
        return 0;
    }

    @Override
    public int getClipWidth() {
        return 0;
    }

    @Override
    public int getClipX() {
        return 0;
    }

    @Override
    public int getClipY() {
        return 0;
    }

    @Override
    public void clipRect(int x, int y, int width, int height) {

    }

    @Override
    public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {

    }

    @Override
    public void drawChars(char[] data, int offset, int length, int x, int y, int anchor) {

    }

    @Override
    public void drawImage(Image img, int x, int y, int anchor) {

    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2) {

    }

    @Override
    public void drawRect(int x, int y, int width, int height) {

    }

    @Override
    public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {

    }

    @Override
    public void drawSubstring(String str, int offset, int len, int x, int y, int anchor) {

    }

    @Override
    public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {

    }

    @Override
    public void fillRect(int x, int y, int width, int height) {

    }

    @Override
    public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {

    }

    @Override
    public void setClip(int x, int y, int width, int height) {

    }
}
