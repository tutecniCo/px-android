package com.mercadopago.components;

import android.support.annotation.NonNull;

/**
 * Created by nfortuna on 10/19/17.
 */

public abstract class Mutator<T> {

    private T props;
    private MutatorPropsListener propsListener;

    public void setPropsListener(@NonNull final MutatorPropsListener listener) {
        this.propsListener = listener;
    }

    protected void notifyPropsChanged() {
        if (propsListener != null) {
            propsListener.onProps(props);
        }
    }

    public abstract T getDefaultProps();
}