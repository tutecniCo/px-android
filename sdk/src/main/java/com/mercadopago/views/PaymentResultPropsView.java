package com.mercadopago.views;

import com.mercadopago.model.PaymentResult;
import com.mercadopago.model.Site;
import com.mercadopago.mvp.MvpView;

import java.math.BigDecimal;

public interface PaymentResultPropsView extends MvpView {

    void setPropPaymentResult(PaymentResult paymentResult);

    void increaseCounter();

    void decreaseCounter();

    void showError(String errorMessage);

    void showError(String errorMessage, String errorDetail);
}
