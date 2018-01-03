package com.mercadopago.plugins.model;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import com.mercadopago.R;

import javax.crypto.spec.DESKeySpec;

/**
 * Created by nfortuna on 12/11/17.
 */
public class PaymentMethodInfo {

    public final String id;
    public final String name;
    public final String description;
    public final @DrawableRes int icon;

    public PaymentMethodInfo(@NonNull final String id) {
        this(id, null, R.drawable.mpsdk_none);
    }

    public PaymentMethodInfo(@NonNull final String id,
                             @NonNull final String name,
                             @DrawableRes final int icon) {
        this(id, name, icon, null);
    }

    public PaymentMethodInfo(@NonNull final String id,
                             @NonNull final String name,
                             @DrawableRes final int icon,
                             @NonNull final String description) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.description = description;
    }
}