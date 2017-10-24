package com.mercadopago.adapters;

import com.mercadopago.callbacks.Callback;

/**
 * Created by mromar on 10/20/17.
 */

public interface MPCall<T> {
    void cancel();

    void enqueue(Callback<T> callback);

    MPCall<T> clone();
}

