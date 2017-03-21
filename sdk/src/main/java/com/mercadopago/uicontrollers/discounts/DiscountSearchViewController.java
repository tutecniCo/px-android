package com.mercadopago.uicontrollers.discounts;

import android.view.View;

import com.mercadopago.uicontrollers.CustomViewController;

/**
 * Created by mromar on 3/21/17.
 */

public interface DiscountSearchViewController extends CustomViewController {

    void draw();
    void setOnClickListener(View.OnClickListener listener);
}
