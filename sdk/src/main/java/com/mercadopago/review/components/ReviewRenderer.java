package com.mercadopago.review.components;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

import com.mercadopago.R;
import com.mercadopago.components.Component;
import com.mercadopago.components.Renderer;

/**
 * Created by nfortuna on 1/3/18.
 */

public class ReviewRenderer extends Renderer<ReviewContainer> {

    @Override
    public View render() {
        final View view = LayoutInflater.from(context)
                .inflate(R.layout.mpsdk_component_review_container, null);



        return view;
    }
}