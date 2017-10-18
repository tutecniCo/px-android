package com.mercadopago.components;

import android.app.Activity;
import android.support.annotation.NonNull;


/**
 * Created by vaserber on 10/17/17.
 */

public class ComponentManager implements ActionDispatcher {

    private Activity activity;
    private Renderer root;
    private ActionsListener actionsListener;

    public ComponentManager(@NonNull final Activity activity) {
        this.activity = activity;
    }

    public void setComponent(@NonNull final Renderer component) {
        root = component;
        activity.setContentView(root.render());
    }

    public void setProps(@NonNull final Props props) {
        root.component.setProps(props);
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
}
