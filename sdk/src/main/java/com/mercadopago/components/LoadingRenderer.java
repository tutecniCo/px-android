package com.mercadopago.components;

import android.view.LayoutInflater;
import android.view.View;

import com.mercadopago.R;

/**
 * Created by nfortuna on 11/1/17.
 */

public class LoadingRenderer extends Renderer<LoadingComponent> {
    @Override
    public View render() {
        return LayoutInflater.from(context)
                .inflate(R.layout.mpsdk_component_loading, null);
    }
}
