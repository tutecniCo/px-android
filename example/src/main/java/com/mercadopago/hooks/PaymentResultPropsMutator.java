package com.mercadopago.hooks;

import android.support.annotation.NonNull;

import com.mercadopago.components.Mutator;
import com.mercadopago.components.MutatorPropsListener;
import com.mercadopago.paymentresult.PaymentResultProps;

/**
 * Created by nfortuna on 10/19/17.
 */

public class PaymentResultPropsMutator implements Mutator {


    private MutatorPropsListener propsListener;

    //Componente props with default values
    private PaymentResultProps props = new PaymentResultProps(0, null, null);

    @Override
    public void setPropsListener(@NonNull final MutatorPropsListener listener) {
        this.propsListener = listener;
    }

//    @Override
//    public void decreaseCounter() {
//        props = props.toBuilder().setCount(props.count - 1).build();
//        notifyPropsChanged();
//    }

    private void notifyPropsChanged() {
        if (propsListener != null) {
            propsListener.onProps(props);
        }
    }
}
