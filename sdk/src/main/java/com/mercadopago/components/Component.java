package com.mercadopago.components;

/**
 * Created by vaserber on 10/17/17.
 */

public abstract class Component<T> {

    private final ActionDispatcher dispatcher;

    public Component(ActionDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public ActionDispatcher getDispatcher() {
        return dispatcher;
    }

    public abstract void setProps(T props);
}
