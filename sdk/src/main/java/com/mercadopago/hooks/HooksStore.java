package com.mercadopago.hooks;

import android.support.annotation.NonNull;

/**
 * Created by nfortuna on 10/24/17.
 */

public class HooksStore<T> {

    private static HooksStore instance;
    private CheckoutHooks checkoutHooks;
    private Hook hook;

    private T data;

    private HooksStore() {}

    public static HooksStore getInstance() {
        if (instance == null) {
            instance = new HooksStore();
        }
        return instance;
    }

    public CheckoutHooks getCheckoutHooks() {
        return checkoutHooks;
    }

    public void setCheckoutHooks(@NonNull final CheckoutHooks checkoutHooks) {
        this.checkoutHooks = checkoutHooks;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
