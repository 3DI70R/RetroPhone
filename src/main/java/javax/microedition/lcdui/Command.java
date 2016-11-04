package javax.microedition.lcdui;

public class Command {

    /**
     * Specifies an application-defined command that pertains to the current screen. Examples could be "Load" and "Save".
     */
    public static final int SCREEN = 1;

    /**
     * A navigation command that returns the user to the logically previous screen.
     */
    public static final int BACK = 2;

    /**
     * A command that is a standard negative answer to a dialog implemented by current screen.
     */
    public static final int CANCEL = 3;

    /**
     * A command that is a standard positive answer to a dialog implemented by current screen.
     * Nothing is done automatically by the implementation; any action taken is
     * implemented by the commandAction provided by the application.
     *
     * With this command type the application hints to the implementation that the user will use this command
     * to ask the application to confirm the data that has been entered in the current screen and to proceed to the next logical screen.
     *
     * CANCEL is often used together with OK.
     */
    public static final int OK = 4;

    /**
     * This command specifies a request for on-line help.
     * No help information is shown automatically by the implementation.
     * The commandAction provided by the application is responsible for showing the help information.
     */
    public static final int HELP = 5;

    /**
     * A command that will stop some currently running process, operation, etc. Nothing is stopped automatically by the implementation.
     * The cessation must be performed by the commandAction provided by the application.
     *
     * With this command type the application hints to the implementation that the user will use this command
     * to stop any currently running process visible to the user on the current screen.
     * Examples of running processes might include downloading or sending of data.
     * Use of the STOP command does not necessarily imply a switch to another screen.
     */
    public static final int STOP = 6;

    /**
     * A command used for exiting from the application.
     * When the user invokes this command, the implementation does not exit automatically.
     * The application's commandAction will be called, and it should exit the
     * application if it is appropriate to do so.
     */
    public static final int EXIT = 7;

    /**
     * With this command type the application can hint to the implementation that the
     * command is specific to a particular item on the screen. For example,
     * an implementation of List can use this information for creating context sensitive menus.
     */
    public static final int ITEM = 8;

    private String label;
    private int commandType;
    private int priority;

    /**
     * Creates a new command object with the given label, type, and priority.
     *
     * @param label the label string
     * @param commandType the command's type, one of BACK, CANCEL, EXIT, HELP, ITEM, OK, SCREEN, or STOP
     * @param priority the command's priority value
     *
     * @throws IllegalArgumentException if the commandType is an invalid type
     * @throws NullPointerException if label is null
     */
    public Command(String label, int commandType, int priority)  {

        if(commandType < SCREEN || commandType > ITEM) {
            throw new IllegalArgumentException("Unknown command type " + commandType);
        }

        if(label == null) {
            throw new NullPointerException("Label cannot be null");
        }

        this.label = label;
        this.commandType = commandType;
        this.priority = priority;
    }

    /**
     * Gets the type of the command.
     * @return type of the Command
     */
    public int getCommandType() {
        return commandType;
    }

    /**
     * Gets the priority of the command.
     * @return priority of the Command
     */
    public int getPriority() {
        return priority;
    }

    /**
     * Gets the label of the command.
     * @return label of the Command
     */
    public String getLabel() {
        return label;
    }
}

