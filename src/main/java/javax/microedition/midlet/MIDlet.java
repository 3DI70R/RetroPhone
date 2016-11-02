package javax.microedition.midlet;

/**
 * A MIDLet is a MID Profile application.<br/>
 * <br/>
 * The application must extend this class to allow the application management software to control the javax.microedition.midlet.MIDlet
 * and to be able to retrieve properties from the application descriptor and notify and request state changes.
 * The methods of this class allow the application management software to create, start, pause, and destroy a javax.microedition.midlet.MIDlet.
 * A javax.microedition.midlet.MIDlet is a set of classes designed to be run and controlled by the application management software via this interface.
 * The states allow the application management software to manage the activities of multiple MIDlets within a runtime environment.
 * It can select which MIDlets are active at a given time by starting and pausing them individually.
 * The application management software maintains the state of the javax.microedition.midlet.MIDlet and invokes methods on the javax.microedition.midlet.MIDlet to change states.
 * The javax.microedition.midlet.MIDlet implements these methods to update its internal activities and resource usage as
 * directed by the application management software. The javax.microedition.midlet.MIDlet can initiate some state changes itself and notifies
 * the application management software of those state changes by invoking the appropriate methods.<br/>
 * <br/>
 * Note: The methods on this interface signal state changes.
 * The state change is not considered complete until the state change method has returned.
 * It is intended that these methods return quickly.
 */
public abstract class MIDlet {

    /**
     * Protected constructor for subclasses.
     */
    protected MIDlet() {
    }

    /**
     * Signals the javax.microedition.midlet.MIDlet that it has entered the Active state.
     * In the Active state the javax.microedition.midlet.MIDlet may hold resources. The method will only be called when the javax.microedition.midlet.MIDlet is in the Paused state.
     * Two kinds of failures can prevent the service from starting, transient and non-transient.
     * For transient failures the javax.microedition.midlet.MIDletStateChangeException exception should be thrown.
     * For non-transient failures the notifyDestroyed method should be called.<br/>
     * <br/>
     * If a Runtime exception occurs during startApp the javax.microedition.midlet.MIDlet will be destroyed immediately.
     * Its destroyApp will be called allowing the javax.microedition.midlet.MIDlet to cleanup.<br/>
     *
     * @throws MIDletStateChangeException is thrown if the javax.microedition.midlet.MIDlet cannot start now but might be able to start at a later time.
     */
    public abstract void startApp() throws MIDletStateChangeException;

    /**
     * Signals the javax.microedition.midlet.MIDlet to stop and enter the Paused state.
     * In the Paused state the javax.microedition.midlet.MIDlet must release shared resources and become quiescent.
     * This method will only be called called when the javax.microedition.midlet.MIDlet is in the Active state.<br/>
     * <br/>
     * If a Runtime exception occurs during pauseApp the javax.microedition.midlet.MIDlet will be destroyed immediately.
     * Its destroyApp will be called allowing the javax.microedition.midlet.MIDlet to cleanup.
     */
    public abstract void pauseApp();

    /**
     * Signals the javax.microedition.midlet.MIDlet to terminate and enter the Destroyed state.
     * In the destroyed state the javax.microedition.midlet.MIDlet must release all resources and save any persistent state.
     * This method may be called from the Paused or Active states.
     * MIDlets should perform any operations required before being terminated, such as releasing resources or saving preferences or state.<br/>
     * <br/>
     * NOTE: The javax.microedition.midlet.MIDlet can request that it not enter the Destroyed state by throwing an javax.microedition.midlet.MIDletStateChangeException.
     * This is only a valid response if the unconditional flag is set to false.
     * If it is true the javax.microedition.midlet.MIDlet is assumed to be in the Destroyed state regardless of how this method terminates.
     * If it is not an unconditional request, the javax.microedition.midlet.MIDlet can signify that it wishes to stay in
     * its current state by throwing the javax.microedition.midlet.MIDletStateChangeException.
     * This request may be honored and the destroy() method called again at a later time.<br/>
     * <br/>
     * If a Runtime exception occurs during destroyApp then they are ignored and the javax.microedition.midlet.MIDlet is put into the Destroyed state.
     * @param unconditional If true when this method is called, the javax.microedition.midlet.MIDlet must cleanup and release all resources.
     *                      If false the javax.microedition.midlet.MIDlet may throw javax.microedition.midlet.MIDletStateChangeException to indicate
     *                      it does not want to be destroyed at this time.
     * @throws MIDletStateChangeException is thrown if the javax.microedition.midlet.MIDlet wishes to continue to execute (Not enter the Destroyed state).
     * This exception is ignored if unconditional is equal to true.
     */
    public abstract void destroyApp(boolean unconditional) throws MIDletStateChangeException;

    /**
     * Used by an javax.microedition.midlet.MIDlet to notify the application management software that it has entered into the Destroyed state.
     * The application management software will not call the javax.microedition.midlet.MIDlet's destroyApp method,
     * and all resources held by the javax.microedition.midlet.MIDlet will be considered eligible for reclamation.
     * The javax.microedition.midlet.MIDlet must have performed the same operations (clean up, releasing of resources etc.)
     * it would have if the javax.microedition.midlet.MIDlet.destroyApp() had been called.
     */
    public final void notifyDestroyed() {
        // TODO: setState
    }

    /**
     * Notifies the application management software that the javax.microedition.midlet.MIDlet does not want to be active and has entered the Paused state.
     * Invoking this method will have no effect if the javax.microedition.midlet.MIDlet is destroyed, or if it has not yet been started.
     * It may be invoked by the javax.microedition.midlet.MIDlet when it is in the Active state.<br/>
     * <br/>
     * If a javax.microedition.midlet.MIDlet calls notifyPaused(), in the future its startApp() method may be called make
     * it active again, or its destroyApp() method may be called to request it to destroy itself.<br/>
     */
    public final void notifyPaused() {
        // TODO: set state if active
    }

    /**
     * Provides a javax.microedition.midlet.MIDlet with a mechanism to indicate that it is interested in entering the Active state.
     * Calls to this method can be used by the application management software to
     * determine which applications to move to the Active state.
     * When the application management software decides to activate this application it will call the startApp method.<br/>
     * <br/>
     * The application is generally in the Paused state when this is called.
     * Even in the paused state the application may handle asynchronous events such as timers or callbacks.
     */
    public final void resumeRequest() {
        // TODO: Log
    }

    /**
     * Provides a javax.microedition.midlet.MIDlet with a mechanism to retrieve named properties from the application management software.
     * The properties are retrieved from the combination of the application descriptor file and the manifest.
     * If an attributes in the descriptor has the same name as an attribute in the manifest
     * the value from the descriptor is used and the value from the manifest is ignored.
     * @param key the name of the property
     * @return A string with the value of the property. null is returned if no value is available for the key.
     *
     * @throws NullPointerException is thrown if key is null.
     */
    public final String getAppProperty(String key) {
        return null;
    }
}

