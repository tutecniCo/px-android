package com.mercadopago.components;

import android.support.annotation.NonNull;

public abstract class Component<T> {

    private T props;
    private ActionDispatcher dispatcher;

    public Component() {

    }

    public Component(@NonNull final T props) {
        this.props = props;
        this.dispatcher = null;
    }

    public Component(@NonNull final T props, @NonNull final ActionDispatcher dispatcher) {
        this.props = props;
        this.dispatcher = dispatcher;
        applyProps(props);
    }

    public ActionDispatcher getDispatcher() {
        return dispatcher;
    }

    public abstract void applyProps(@NonNull final T props);

    public void setProps(T props) {
        this.props = props;
    }

    public void setDispatcher(ActionDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public T getProps() {
        return props;
    }
}
