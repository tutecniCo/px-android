package com.mercadopago.components;

/**
 * Created by vaserber on 10/18/17.
 */

public interface ComponentProps<T extends Props> {

    void setProps(T props);
}
