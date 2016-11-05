package javax.microedition.lcdui;

import ru.threedisevenzeror.retrophone.utils.ComponentDelegate;
import ru.threedisevenzeror.retrophone.utils.DelegateHolder;

import java.util.*;

/**
 * An object that has the capability of being placed on the display. A Displayable object may have commands and listeners
 * associated with it. The contents displayed and their interaction with the user are defined by subclasses.
 */
public abstract class Displayable {

    /**
     * Displayable's delegate
     * Provides basic functions and callbacks from displayable for specific implementation
     */
    public static abstract class DisplayableDelegate extends ComponentDelegate<Displayable> {

        /**
         * Callback that called just after new command is added to attached displayable
         * @param command added command
         */
        public abstract void onCommandAdded(Command command);

        /**
         * Callback that called just after command is removed from displayable
         * @param command removed command
         */
        public abstract void onCommandRemoved(Command command);

        /**
         * Method that called just after command is invoked
         * @param command invoked command
         */
        public abstract void onCommandAction(Command command);

        /**
         * Get all commands that contains within attached Displayable
         * @return unmodifiable list of added commands
         * @throws IllegalStateException if called before being attached to displayable
         */
        public java.util.List<Command> getAllCommands() {
            checkForAttach();
            return Collections.unmodifiableList(getAttachedObject().commandList);
        }

        /**
         * Invokes command on attached displayable
         * @throws IllegalStateException if called before being attached to displayable
         */
        public void invokeCommand(Command command) {
            checkForAttach();
            getAttachedObject().invokeCommand(command);
        }
    }

    private CommandListener commandListener;
    private final DelegateHolder<DisplayableDelegate, Displayable> delegateHolder;
    private java.util.List<Command> commandList;

    public Displayable() {
        commandList = new ArrayList<Command>();
        delegateHolder = new DelegateHolder<DisplayableDelegate, Displayable>(this);
    }

    /**
     * Checks if the Displayable is actually visible on the display.
     * In order for a Displayable to be visible, all of the following must be true:
     * the Display's MIDlet must be running in the foreground, the Displayable must be the Display's current screen,
     * and the Displayable must not be obscured by a system screen.
     *
     * @return true if the Displayable is currently visible
     */
    public boolean isShown() {
        return true; // TODO: Display checks
    }

    /**
     * Adds a command to the Displayable. The implementation may choose, for example,
     * to add the command to any of the available softbuttons or place it in a menu.
     * If the added command is already in the screen (tested by comparing the object references),
     * the method has no effect. If the Displayable is actually visible on the display, and this
     * call affects the set of visible commands, the implementation should update the display
     * as soon as it is feasible to do so.
     *
     * @param cmd the command to be added
     *
     * @throws NullPointerException if cmd is null
     */
    public void addCommand(Command cmd) {
        if(cmd == null) {
            throw new NullPointerException("Command cannot be null");
        }

        DisplayableDelegate delegate = delegateHolder.getDelegate();
        if(commandList.add(cmd) && delegate != null) {
            delegate.onCommandAdded(cmd);
        }
    }

    /**
     * Removes a command from the Displayable.
     *
     * If the command is not in the Displayable (tested by comparing the object references), the method has no effect.
     * If the Displayable is actually visible on the display, and this call affects the set of visible commands,
     * the implementation should update the display as soon as it is feasible to do so.
     *
     * @param command the command to be removed
     */
    public void removeCommand(Command command) {
        DisplayableDelegate delegate = delegateHolder.getDelegate();
        if(commandList.remove(command) && delegate != null) {
            delegate.onCommandRemoved(command);
        }
    }

    /**
     * Sets a listener for Commands to this Displayable, replacing any previous CommandListener.
     * A null reference is allowed and has the effect of removing any existing listener.
     * @param l the new listener, or null.
     */
    public void setCommandListener(CommandListener l) {
        commandListener = l;
    }

    /**
     * Attaches delegate to this displayable
     * If another delegate is attached to this displayable, it detaches from displayable
     * @param delegate new delegate
     */
    public void attachDelegate(DisplayableDelegate delegate) {
        delegateHolder.setDelegate(delegate);
    }

    protected void invokeCommand(Command command) {
        if(commandListener != null) {
            commandListener.commandAction(command, this);
        }
    }
}

