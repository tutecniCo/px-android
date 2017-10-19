package com.mercadopago.paymentresult;

import android.support.annotation.NonNull;

import com.mercadopago.components.Mutator;
import com.mercadopago.components.MutatorPropsListener;
import com.mercadopago.model.PaymentResult;
import com.mercadopago.views.PaymentResultPropsView;

/**
 * Created by nfortuna on 10/19/17.
 */

public class PaymentResultPropsMutator implements Mutator, PaymentResultPropsView {


    private MutatorPropsListener propsListener;

    //Componente props with default values
    private PaymentResultProps props = new PaymentResultProps(null, null);

    @Override
    public void setPaymentResult(@NonNull final PaymentResult paymentResult) {

        props.toBuilder().setTitle(paymentResult.getPaymentStatus())
                .setSubtitle(paymentResult.getPaymentData()
                        .getPaymentMethod().getName()).build();

    }

    @Override
    public void showError(@NonNull final String errorMessage) {

    }

    @Override
    public void showError(@NonNull final String errorMessage, @NonNull final String errorDetail) {

    }

    @Override
    public void setPropsListener(@NonNull final MutatorPropsListener listener) {
        this.propsListener = listener;
    }
}
