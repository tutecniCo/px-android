package com.mercadopago.components;

import android.support.annotation.NonNull;

/**
 * Created by vaserber on 10/17/17.
 */

public abstract class Component<T> {

    private final ActionDispatcher dispatcher;

    public Component(@NonNull final ActionDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public Component(@NonNull final T props, @NonNull final ActionDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public ActionDispatcher getDispatcher() {
        return dispatcher;
    }

    public abstract void setProps(@NonNull final T props);
}
