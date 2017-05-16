package com.mercadopago.listeners;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * Created by mromar on 5/12/17.
 */

public class PayerNameTextWatcher implements TextWatcher{

    private PayerNameEditTextCallback mEditTextCallback;

    public PayerNameTextWatcher(PayerNameEditTextCallback editTextCallback) {
        this.mEditTextCallback = editTextCallback;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        mEditTextCallback.checkOpenKeyboard();
        mEditTextCallback.savePayerName(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {
        mEditTextCallback.changeErrorView();
        mEditTextCallback.toggleLineColorOnError(false);
    }
}
