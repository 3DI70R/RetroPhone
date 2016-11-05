package javax.microedition.lcdui;

import ru.threedisevenzeror.retrophone.DisplayDevice;
import ru.threedisevenzeror.retrophone.GraphicsDevice;
import ru.threedisevenzeror.retrophone.InputDevice;
import ru.threedisevenzeror.retrophone.RetroDevice;
import ru.threedisevenzeror.retrophone.utils.ComponentDelegate;
import ru.threedisevenzeror.retrophone.utils.DelegateHolder;

public abstract class Canvas extends Displayable {

    public static abstract class CanvasDelegate extends ComponentDelegate<Canvas> {

        public void paint(Graphics graphics) {
            checkForAttach();
            Canvas canvas = getAttachedObject();
            canvas.paint(graphics);
        }

        public void notifyShow() {
            checkForAttach();
            getAttachedObject().showNotify();
        }

        public void notifyHide() {
            checkForAttach();
            getAttachedObject().hideNotify();
        }

        public void notifyPointerPressed(int x, int y) {
            checkForAttach();
            getAttachedObject().pointerPressed(x, y);
        }

        public void notifyKeyPressed(int keyCode) {
            checkForAttach();
            getAttachedObject().keyPressed(keyCode);
        }

        public void notifyKeyRepeated(int keyCode) {
            checkForAttach();
            getAttachedObject().keyRepeated(keyCode);
        }

        public void notifyKeyReleased(int keyCode) {
            checkForAttach();
            getAttachedObject().keyReleased(keyCode);
        }

        public void notifyPointerDragged(int x, int y) {
            checkForAttach();
            getAttachedObject().pointerDragged(x, y);
        }

        public void notifyPointerReleased(int x, int y) {
            checkForAttach();
            getAttachedObject().pointerReleased(x, y);
        }

        public abstract void repaint(int x, int y, int width, int height);
        public abstract void serviceRepaints();
    }

    /**
     * Constant for the UP game action.
     */
    public static final int UP = 1;

    /**
     * Constant for the DOWN game action.
     */
    public static final int DOWN = 6;

    /**
     * Constant for the LEFT game action.
     */
    public static final int LEFT = 2;

    /**
     * Constant for the RIGHT game action.
     */
    public static final int RIGHT = 5;

    /**
     * Constant for the FIRE game action.
     */
    public static final int FIRE = 8;

    /**
     * Constant for the general purpose "A" game action.
     */
    public static final int GAME_A = 9;

    /**
     * Constant for the general purpose "B" game action.
     */
    public static final int GAME_B = 10;

    /**
     * Constant for the general purpose "C" game action.
     */
    public static final int GAME_C = 11;

    /**
     * Constant for the general purpose "D" game action.
     */
    public static final int GAME_D = 12;

    /**
     * keyCode for ITU-T key 0.
     */
    public static final int KEY_NUM0 = 48;

    /**
     * keyCode for ITU-T key 1.
     */
    public static final int KEY_NUM1 = 49;

    /**
     * keyCode for ITU-T key 2.
     */
    public static final int KEY_NUM2 = 50;

    /**
     * keyCode for ITU-T key 3.
     */
    public static final int KEY_NUM3 = 51;

    /**
     * keyCode for ITU-T key 4.
     */
    public static final int KEY_NUM4 = 52;

    /**
     * keyCode for ITU-T key 5.
     */
    public static final int KEY_NUM5 = 53;

    /**
     * keyCode for ITU-T key 6.
     */
    public static final int KEY_NUM6 = 54;

    /**
     * keyCode for ITU-T key 7.
     */
    public static final int KEY_NUM7 = 55;

    /**
     * keyCode for ITU-T key 8.
     */
    public static final int KEY_NUM8 = 56;

    /**
     * keyCode for ITU-T key 9.
     */
    public static final int KEY_NUM9 = 57;

    /**
     * keyCode for ITU-T key "star" (*).
     */
    public static final int KEY_STAR = 42;

    /**
     * keyCode for ITU-T key "pound" (#).
     */
    public static final int KEY_POUND = 35;

    private GraphicsDevice graphicsDevice;
    private DisplayDevice displayDevice;
    private InputDevice inputDevice;
    private final DelegateHolder<CanvasDelegate, Canvas> delegateHolder;

    private int width;
    private int height;

    protected Canvas() {

        delegateHolder = new DelegateHolder<CanvasDelegate, Canvas>(this);
        graphicsDevice = RetroDevice.getInstance().getGraphics();
        displayDevice = RetroDevice.getInstance().getDisplay();
        inputDevice = RetroDevice.getInstance().getInput();

        width = displayDevice.getScreenWidth();
        height = displayDevice.getScreenHeight();
    }

    public GraphicsDevice getGraphicsDevice() {
        return graphicsDevice;
    }

    public InputDevice getInputDevice() {
        return inputDevice;
    }

    public DisplayDevice getDisplayDevice() {
        return displayDevice;
    }

    /**
     * Gets width of the displayable area in pixels.
     * The value is unchanged during the execution of the application and all Canvases will have the same value.
     * @return width of the displayable area
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets height of the displayable area in pixels.
     * The value is unchanged during the execution of the application and all Canvases will have the same value.
     * @return height of the displayable area
     */
    public int getHeight() {
        return height;
    }

    /**
     * Checks if the Graphics is double buffered by the implementation.
     * @return true if double buffered, false otherwise.
     */
    public boolean isDoubleBuffered() {
        return true;
    }

    /**
     * Checks if the platform supports pointer press and release events.
     * @return true if the device supports pointer events
     */
    public boolean hasPointerEvents() {
        return inputDevice.hasPointerEvents();
    }

    /**
     * Checks if the platform supports pointer motion events (pointer dragged).
     * Applications may use this method to determine if the platform is capable of supporting motion events.
     * @return true if the device supports pointer motion events
     */
    public boolean hasPointerMotionEvents() {
        return inputDevice.hasPointerMotionEvents();
    }

    /**
     * Checks if the platform can generate repeat events when key is kept down.
     * @return true if the device supports repeat events
     */
    public boolean hasRepeatEvents() {
        return inputDevice.hasRepeatEvents();
    }

    /**
     * Gets a key code that corresponds to the specified game action on the device.
     * The implementation is required to provide a mapping for every game action, so this method will
     * always return a valid key code for every game action. See above for further discussion of game actions.
     *
     * Note that a key code is associated with at most one game action, whereas a game action may
     * be associated with several key codes. Then, supposing that g is a valid game action and k
     * is a valid key code for a key associated with a game action, consider the following expressions:
     *
     * g == getGameAction(getKeyCode(g))     // (1)
     * k == getKeyCode(getGameAction(k))     // (2)
     *
     * Expression (1) is always true. However, expression (2) might be true but is not necessarily true.
     *
     * The mapping between key codes and game actions will not change during the execution of the application.
     * @param gameAction the game action
     * @return a key code corresponding to this game action
     *
     * @throws IllegalArgumentException if gameAction is not a valid game action
     */
    public int getKeyCode(int gameAction) {
        return inputDevice.getKeyCode(gameAction);
    }

    /**
     * Gets an informative key string for a key. The string returned will resemble the text physically
     * printed on the key. This string is suitable for displaying to the user. For example, on a device
     * with function keys F1 through F4, calling this method on the keyCode for the F1 key will
     * return the string "F1". A typical use for this string will be to compose help text
     * such as "Press F1 to proceed."
     *
     * This method will return a non-empty string for every valid key code.
     *
     * There is no direct mapping from game actions to key names.
     * To get the string name for a game action GAME_A, the application must call
     *
     * getKeyName(getKeyCode(GAME_A))
     * @param keyCode the key code being requested
     * @return a string name for the key
     *
     * @throws IllegalArgumentException if keyCode is not a valid key code
     */
    public String getKeyName(int keyCode) {
        return inputDevice.getKeyName(keyCode);
    }

    /**
     * Gets the game action associated with the given key code of the device.
     * Returns zero if no game action is associated with this key code. See above for further discussion of game actions.
     *
     * The mapping between key codes and game actions will not change during the execution of the application.
     * @param keyCode the key code
     * @return the game action corresponding to this key, or 0 if none
     *
     * @throws IllegalArgumentException if keyCode is not a valid key code
     */
    public int getGameAction(int keyCode) {
        return inputDevice.getGameAction(keyCode);
    }

    /**
     * Called when a key is pressed.<br/>
     * <br/>
     * The getGameAction() method can be called to determine what game action, if any, is mapped to the key.
     * Class Canvas has an empty implementation of this method, and the subclass has to redefine it if it
     * wants to listen this method.
     * @param keyCode The key code of the key that was pressed.
     */
    protected void keyPressed(int keyCode) {
        // empty implementation
    }

    /**
     * Called when a key is repeated (held down). The getGameAction() method can be called to determine what game action,
     * if any, is mapped to the key. Class Canvas has an empty implementation of this method,
     * and the subclass has to redefine it if it wants to listen this method.
     *
     * @param keyCode The key code of the key that was repeated
     */
    protected void keyRepeated(int keyCode) {
        // empty implementation
    }

    /**
     * Called when a key is released. The getGameAction() method can be called to determine what
     * game action, if any, is mapped to the key. Class Canvas has an empty implementation of this
     * method, and the subclass has to redefine it if it wants to listen this method.
     *
     * @param keyCode The key code of the key that was released
     */
    protected void keyReleased(int keyCode) {
        // empty implementation
    }

    /**
     * Called when the pointer is pressed. The hasPointerEvents() method may be called to determine if the device
     * supports pointer events. Class Canvas has an empty implementation of this method, and the subclass has to
     * redefine it if it wants to listen this method.
     * @param x The horizontal location where the pointer was pressed (relative to the Canvas)
     * @param y The vertical location where the pointer was pressed (relative to the Canvas)
     */
    protected void pointerPressed(int x, int y) {
        // empty implementation
    }

    /**
     * Called when the pointer is released. The hasPointerEvents() method may be called to determine if the device
     * supports pointer events. Class Canvas has an empty implementation of this method, and the subclass has
     * to redefine it if it wants to listen this method.
     *
     * @param x The horizontal location where the pointer was released (relative to the Canvas)
     * @param y The vertical location where the pointer was released (relative to the Canvas)
     */
    protected void pointerReleased(int x, int y) {
        // empty implementation
    }

    /**
     * Called when the pointer is dragged. The hasPointerMotionEvents() method may be called to determine
     * if the device supports pointer events. Class Canvas has an empty implementation of this method,
     * and the subclass has to redefine it if it wants to listen this method.
     * @param x The horizontal location where the pointer was dragged (relative to the Canvas)
     * @param y The vertical location where the pointer was dragged (relative to the Canvas)
     */
    protected void pointerDragged(int x, int y) {
        // empty implementation
    }

    /**
     * Requests a repaint for the specified region of the Screen.
     * Calling this method may result in subsequent call to paint(), where the passed Graphics object's clip region
     * will include at least the specified region.
     *
     * If the canvas is not visible, or if width and height are zero or less, or if the rectangle does not
     * specify a visible region of the display, this call has no effect.
     *
     * The call to paint() occurs independently of the call to repaint(). That is, repaint() will not block
     * waiting for paint() to finish. The paint() method will either be called after the caller of repaint()
     * returns to the implementation (if the caller is a callback) or on another thread entirely.
     *
     * To synchronize with its paint() routine, applications can use either Display.callSerially() or serviceRepaints(),
     * or they can code explicit synchronization into their paint() routine.
     *
     * The origin of the coordinate system is above and to the left of the pixel in the upper left corner
     * of the displayable area of the Canvas. The X-coordinate is positive right and the
     * Y-coordinate is positive downwards.
     *
     * @param x the x coordinate of the rectangle to be repainted
     * @param y the y coordinate of the rectangle to be repainted
     * @param width the width of the rectangle to be repainted
     * @param height the height of the rectangle to be repainted
     */
    public final void repaint(int x, int y, int width, int height) {
        CanvasDelegate delegate = delegateHolder.getDelegate();
        if(delegate != null) {
            delegate.repaint(x, y, width, height);
        }
    }

    /**
     * Requests a repaint for the entire Canvas. The effect is identical to repaint(0, 0, getWidth(), getHeight());
     */
    public final void repaint() {
        repaint(0, 0, getWidth(), getHeight());
    }

    /**
     * Forces any pending repaint requests to be serviced immediately.
     * This method blocks until the pending requests have been serviced. If there are no pending repaints,
     * or if this canvas is not visible on the display, this call does nothing and returns immediately.
     *
     * WARNING: This method blocks until the call to the application's paint() method returns.
     * The application has no control over which thread calls paint(); it may vary from implementation to
     * implementation. If the caller of serviceRepaints() holds a lock that the paint() method acquires,
     * this may result in deadlock. Therefore, callers of serviceRepaints() must not hold any locks
     * that might be acquired within the paint() method. The Display.callSerially() method provides
     * a facility where an application can be called back after painting has completed, avoiding
     * the danger of deadlock.
     */
    public final void serviceRepaints() {
        CanvasDelegate delegate = delegateHolder.getDelegate();
        if(delegate != null) {
            delegate.serviceRepaints();
        }
    }

    /**
     * The implementation calls showNotify() immediately prior to this Canvas being made visible on
     * the display. Canvas subclasses may override this method to perform tasks before being shown,
     * such as setting up animations, starting timers, etc. The default implementation of this method
     * in class Canvas is empty.
     */
    protected void showNotify() {
        // empty implementation
    }

    /**
     * The implementation calls hideNotify() shortly after the Canvas has been removed from the display.
     * Canvas subclasses may override this method in order to pause animations, revoke timers, etc.
     * The default implementation of this method in class Canvas is empty.
     */
    protected void hideNotify() {
        // empty implementation
    }

    /**
     * Renders the Canvas. The application must implement this method in order to paint any graphics.
     * The Graphics object's clip region defines the area of the screen that is considered to be invalid.
     * A correctly-written paint() routine must paint every pixel within this region.
     * Applications must not assume that they know the underlying source of the paint() call and use
     * this assumption to paint only a subset of the pixels within the clip region.
     * The reason is that this particular paint() call may have resulted from multiple repaint() requests,
     * some of which may have been generated from outside the application. An application that paints
     * only what it thinks is necessary to be painted may display incorrectly if the screen contents had
     * been invalidated by, for example, an incoming telephone call.<br/>
     * <br/>
     * Operations on this graphics object after the paint() call returns are undefined.
     * Thus, the application must not cache this Graphics object for later use or use by
     * another thread. It must only be used within the scope of this method.<br/>
     * <br/>
     * The implementation may postpone visible effects of graphics operations until the end of the paint method.<br/>
     * <br/>
     * The contents of the Canvas are never saved if it is hidden and then is made visible again.
     * Thus, shortly after showNotify() is called, paint() will always be called with a Graphics object
     * whose clip region specifies the entire displayable area of the Canvas. Applications must not
     * rely on any contents being preserved from a previous occasion when the Canvas was current.
     * This call to paint() will not necessarily occur before any other key, pointer, or commandAction()
     * methods are called on the Canvas. Applications whose repaint recomputation is expensive may
     * create an offscreen Image, paint into it, and then draw this image on the Canvas when paint() is called.<br/>
     * <br/>
     * The application code must never call paint(); it is called only by the implementation.<br/>
     * <br/>
     * The Graphics object passed to the paint() method has the following properties:<br/>
     * <br/>
     * - the destination is the actual display, or if double buffering is in effect, a back buffer for the display;<br/>
     * - the clip region includes at least one pixel within this Canvas;<br/>
     * - the current color is black;<br/>
     * - the font is the same as the font returned by Font.getDefaultFont();<br/>
     * - the stroke style is SOLID;<br/>
     * - the origin of the coordinate system is located at the upper-left corner of the Canvas; and<br/>
     * - the Canvas is visible, that is, a call to isShown() will return true.<br/>
     *
     * @param g the Graphics object to be used for rendering the Canvas
     */
    protected abstract void paint(Graphics g);

    public void attachDelegate(CanvasDelegate delegate) {
        delegateHolder.setDelegate(delegate);
    }
}

