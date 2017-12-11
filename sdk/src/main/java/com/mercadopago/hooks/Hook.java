package com.mercadopago.hooks;

import com.mercadopago.components.Component;

public abstract class Hook {

    abstract Component<HookComponent.Props> createComponent();

    public boolean isEnabled() {
        return true;
    }
}