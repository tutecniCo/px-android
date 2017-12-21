package com.mercadopago.paymentresult;

import android.support.annotation.NonNull;

import com.mercadopago.components.Mutator;
import com.mercadopago.components.MutatorPropsListener;
import com.mercadopago.model.Instruction;
import com.mercadopago.model.PaymentResult;
import com.mercadopago.paymentresult.components.Body;
import com.mercadopago.paymentresult.formatter.BodyAmountFormatter;
import com.mercadopago.paymentresult.formatter.HeaderTitleFormatter;
import com.mercadopago.paymentresult.props.HeaderProps;
import com.mercadopago.paymentresult.props.PaymentResultProps;
import com.mercadopago.preferences.PaymentResultScreenPreference;

/**
 * Created by vaserber on 10/20/17.
 */

public class PaymentResultPropsMutator implements Mutator, PaymentResultPropsView {

    private MutatorPropsListener propsListener;

    //Component props with default values
    private PaymentResultProps props = new PaymentResultProps.Builder().build();

    @Override
    public void setPropsListener(MutatorPropsListener listener) {
        this.propsListener = listener;
    }

    //headerAmountFormatter can be null
    @Override
    public void setPropPaymentResult(@NonNull final PaymentResult paymentResult,
                                     final HeaderTitleFormatter headerAmountFormatter,
                                     final BodyAmountFormatter bodyAmountFormatter,
                                     final boolean showLoading) {
        props = props.toBuilder()
                .setPaymentResult(paymentResult)
                .setHeaderMode(HeaderProps.HEADER_MODE_WRAP)
                .setHeaderAmountFormatter(headerAmountFormatter)
                .setBodyAmountFormatter(bodyAmountFormatter)
                .setLoading(showLoading)
                .build();
    }

    @Override
    public void setPropInstruction(@NonNull final Instruction instruction,
                                   @NonNull final HeaderTitleFormatter amountFormat,
                                   final boolean showLoading,
                                   @NonNull final String processingMode) {
        props = props.toBuilder()
                .setInstruction(instruction)
                .setHeaderAmountFormatter(amountFormat)
                .setLoading(showLoading)
                .setProcessingMode(processingMode)
                .build();
    }

    public void notifyPropsChanged() {
        if (propsListener != null) {
            propsListener.onProps(props);
        }
    }

    public void renderDefaultProps() {
        notifyPropsChanged();
    }

}
