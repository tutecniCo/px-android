package com.mercadopago.hooks;

import com.mercadopago.components.Component;

public class Hook {

    public final Component<HookComponent.Props> component;

    public Hook(HookComponent component) {
        this.component = component;
    }


}