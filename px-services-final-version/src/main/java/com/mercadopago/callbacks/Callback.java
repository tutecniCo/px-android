package com.mercadopago.callbacks;

import com.mercadopago.model.ApiException;

/**
 * Created by mromar on 10/20/17.
 */

public abstract class Callback<T> {
    /**
     * Called for [200, 300) responses.
     */
    public abstract void success(T t);

    /**
     * Called for all errors.
     */
    public abstract void failure(ApiException apiException);

    public int attempts = 0;
}
