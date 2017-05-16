package com.mercadopago.listeners;

/**
 * Created by mromar on 5/12/17.
 */

public interface PayerNameEditTextCallback {

    void checkOpenKeyboard();
    void savePayerName(CharSequence s);
    void changeErrorView();
    void toggleLineColorOnError(boolean toggle);
}
