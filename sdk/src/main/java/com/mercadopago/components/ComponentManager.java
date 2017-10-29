package com.mercadopago.components;

import android.app.Activity;
import android.support.annotation.NonNull;


public class ComponentManager<T> implements ActionDispatcher, MutatorPropsListener<T> {

    private Activity activity;
    private Component root;
    private ActionsListener actionsListener;
    private Renderer renderer;

    public ComponentManager(@NonNull final Activity activity) {
        this.activity = activity;
    }

    public void setComponent(@NonNull final Component component) {
        root = component;
        renderer = RendererFactory.create(activity, component);
    }

    public void render() {
        if (renderer != null) {
            activity.setContentView(renderer.render(activity, root));
        }
    }

    public void render(final Component component) {
        if (component != null) {
            setComponent(component);
            render();
        }
    }

    public void setActionsListener(final ActionsListener actionsListener) {
        this.actionsListener = actionsListener;
    }

    @Override
    public void dispatch(final Action action) {
        if (actionsListener != null) {
            actionsListener.onAction(action);
        }
    }

    @Override
    public void onProps(T props) {
        root.applyProps(props);
        render();
    }
}