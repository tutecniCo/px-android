package com.mercadopago.components;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vaserber on 10/20/17.
 */

public class ComponentManager<T> implements ActionDispatcher, MutatorPropsListener<T> {

    private Activity activity;
    private Component root;
    private ActionsListener actionsListener;
    private Map<Class, Class> rendererRegistry = new HashMap<>();
    private Mutator mutator;
    private Renderer renderer;

    public ComponentManager(@NonNull final Activity activity) {
        this.activity = activity;

        //Register renderers here. For example:

        //rendererRegistry.put(ExampleComponent.class, ExampleRenderer.class);
    }

    public void setComponent(@NonNull final Component component) {
        root = component;
        try {
            renderer = (Renderer) rendererRegistry.get(root.getClass()).newInstance();
            renderer.setComponent(component);
            renderer.setContext(activity);
            renderer.init();
        } catch (Exception e) {
            Log.e("error", e.getMessage(), e);
        }
    }

    private void render() {
        if (renderer != null) {
            activity.setContentView(renderer.render());
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

    public void setMutator(Mutator mutator) {
        this.mutator = mutator;
        this.mutator.setPropsListener(this);
    }

    @Override
    public void onProps(T props) {
        root.applyProps(props);
        render();
    }
}
