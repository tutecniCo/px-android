package com.mercadopago.components;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.mercadopago.paymentresult.CongratsHeaderComponent;
import com.mercadopago.paymentresult.CongratsHeaderRenderer;
import com.mercadopago.paymentresult.SubtitleComponent;
import com.mercadopago.paymentresult.SubtitleRenderer;

import java.util.HashMap;
import java.util.Map;

public class ComponentManager<T> implements ActionDispatcher, MutatorPropsListener<T> {

    private Activity activity;
    private Component root;
    private ActionsListener actionsListener;
    private Map<Class, Class> rendererRegistry = new HashMap<>();
    private Mutator mutator;
    private Renderer renderer;

    public ComponentManager(@NonNull final Activity activity) {
        this.activity = activity;
        rendererRegistry.put(CongratsHeaderComponent.class, CongratsHeaderRenderer.class);
        rendererRegistry.put(SubtitleComponent.class, SubtitleRenderer.class);
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

    public void render() {
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
