package com.mercadopago.components;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vaserber on 10/20/17.
 */

public class Component<T> {

    public T props;
    public final List<Component> children = new ArrayList<>();

    private ActionDispatcher dispatcher;

    public Component(@NonNull final T props) {
        this(props, null);
    }

    public Component(@NonNull final T props, @NonNull final ActionDispatcher dispatcher) {
        this.props = props;
        this.dispatcher = dispatcher;
        setProps(props);
    }


    public void addChildren(final Component component) {
        this.children.add(component);
    }

    public List<Component> getChildren() {
        return children;
    }

    public ActionDispatcher getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(ActionDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void setProps(@NonNull final T props) {
        this.props = props;
    }

    public void dispatchActionContinue() {
        getDispatcher().dispatch(new ContinueAction());
    }

    public void dispatchActionBack() {
        getDispatcher().dispatch(new BackAction());
    }
}