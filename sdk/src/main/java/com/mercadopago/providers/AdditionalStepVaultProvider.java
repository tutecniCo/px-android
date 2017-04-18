package com.mercadopago.providers;

import android.content.Context;

import com.mercadopago.mvp.ResourcesProvider;

/**
 * Created by marlanti on 3/23/17.
 */

public interface AdditionalStepVaultProvider extends ResourcesProvider {
    Context getContext();
}
