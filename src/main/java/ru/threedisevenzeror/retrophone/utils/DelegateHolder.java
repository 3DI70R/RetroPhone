package ru.threedisevenzeror.retrophone.utils;

/**
 * Created by ThreeDISevenZeroR on 04.11.2016.
 */
public class DelegateHolder<D extends ComponentDelegate> {

    public interface DelegateCaller<D> {
        void call(D d);
    }

    private D delegate;
    private Object attachObject;

    public DelegateHolder(Object attachObject) {
        this.attachObject = attachObject;
    }

    public D getDelegate() {
        return delegate;
    }

    public void callIfExists(DelegateCaller<D> call) {
        if(delegate != null) {
            call.call(delegate);
        }
    }

    public void setDelegate(D delegate) {

        if(this.delegate != delegate) {
            clearDelegate();

            this.delegate = delegate;
            if(this.delegate != null) {

                if(this.delegate.isAttached()) {
                    this.delegate.holder.clearDelegate();
                }

                this.delegate.attachedObject = attachObject;
                this.delegate.holder = this;
                this.delegate.onAttach();
            }
        }
    }

    protected void clearDelegate() {

        if(delegate != null) {
            delegate.onDetach();
            delegate.attachedObject = null;
            delegate.holder = null;
            delegate = null;
        }
    }
}
