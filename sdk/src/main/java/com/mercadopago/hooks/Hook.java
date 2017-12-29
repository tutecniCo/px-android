package com.mercadopago.hooks;

import com.mercadopago.components.Component;

public abstract class Hook {

    public abstract Component<HookComponent.Props> createComponent();

    public boolean isEnabled() {
        return true;
    }
}