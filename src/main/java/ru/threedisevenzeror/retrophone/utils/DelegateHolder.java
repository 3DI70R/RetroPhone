package ru.threedisevenzeror.retrophone.utils;

/**
 * Created by ThreeDISevenZeroR on 04.11.2016.
 */
public class DelegateHolder<D extends ComponentDelegate> {

    public interface DelegateCaller<D> {
        void call(D d);
    }

    private DelegateHolder<? super D> parentHolder;
    private D delegate;
    private Object attachObject;

    public DelegateHolder(Object attachObject) {
        this(null, attachObject);
    }

    public DelegateHolder(DelegateHolder<? super D> parentHolder, Object attachObject) {
        this.parentHolder = parentHolder;
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

        parentHolder.setDelegate(delegate);

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

        if(parentHolder != null) {
            parentHolder.clearDelegate();
        }

        if(delegate != null) {
            delegate.onDetach();
            delegate.attachedObject = null;
            delegate.holder = null;
            delegate = null;
        }
    }
}
