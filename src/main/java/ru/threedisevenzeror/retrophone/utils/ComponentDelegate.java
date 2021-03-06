package ru.threedisevenzeror.retrophone.utils;

/**
 * Created by ThreeDISevenZeroR on 04.11.2016.
 */
public abstract class ComponentDelegate<T> {

    DelegateHolder<?, T> holder;
    T attachedObject;

    public boolean isAttached() {
        return attachedObject != null;
    }

    public void onAttach() {
        // noop
    }

    public void onDetach() {
        // noop
    }

    public T getAttachedObject() {
        return attachedObject;
    }

    public void detach() {
        if(holder != null) {
            holder.clearDelegate();
        }
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
