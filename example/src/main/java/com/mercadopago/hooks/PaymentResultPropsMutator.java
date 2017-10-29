package com.mercadopago.hooks;

import com.mercadopago.components.Mutator;
import com.mercadopago.paymentresult.PaymentResultProps;

/**
 * Created by nfortuna on 10/19/17.
 */

public class PaymentResultPropsMutator extends Mutator<PaymentResultProps> {

    @Override
    public PaymentResultProps getDefaultProps() {
        return new PaymentResultProps(0, null, null);
    }
}
