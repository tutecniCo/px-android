package com.mercadopago.adapters;
import com.mercadopago.callbacks.Callback;

/**
 * Created by mreverter on 6/6/16.
 */
public interface MPCall<T> {
    void cancel();
    void enqueue(Callback<T> callback);
    com.mercadopago.adapters.MPCall<T> clone();
}

