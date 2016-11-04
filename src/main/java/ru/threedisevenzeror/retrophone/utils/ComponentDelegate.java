package ru.threedisevenzeror.retrophone.utils;

/**
 * Created by ThreeDISevenZeroR on 04.11.2016.
 */
public abstract class ComponentDelegate {

    DelegateHolder<?> holder;
    Object attachedObject;

    public boolean isAttached() {
        return attachedObject != null;
    }

    public void onAttach() {
        // noop
    }

    public void onDetach() {
        // noop
    }

    public Object getAttachedObject() {
        return attachedObject;
    }

    /**
     * Checks for attached object and throws exception if it doesn't attached
     */
    protected void checkForAttach() {
        if(!isAttached()) {
            throw new IllegalStateException("Cannot invoke delegate commands in detached state");
        }
    }
}
