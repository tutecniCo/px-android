package com.mercadopago.plugins.components;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.mercadopago.components.RendererFactory;
import com.mercadopago.core.CheckoutStore;
import com.mercadopago.model.Payment;
import com.mercadopago.plugins.PluginComponent;
import com.mercadopago.plugins.PluginPaymentResultAction;
import com.mercadopago.plugins.model.PaymentMethodInfo;
import com.mercadopago.plugins.model.PluginPaymentResult;

/**
 * Created by nfortuna on 12/13/17.
 */

public class BitcoinPayment extends PluginComponent {

    final Handler handler = new Handler();

    static {
        RendererFactory.register(BitcoinPayment.class, BitcoinPaymentRenderer.class);
    }

    public BitcoinPayment(@NonNull final Props props) {
        super(props);
        executePayment();
    }

    public void executePayment() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                final PaymentMethodInfo paymentMethodInfo =
                        CheckoutStore.getInstance().getSelectedPaymentMethod();

                final PluginPaymentResult result = new PluginPaymentResult(
                        1241231234l,
                        Payment.StatusCodes.STATUS_APPROVED,
                        Payment.StatusCodes.STATUS_APPROVED,
                        paymentMethodInfo);

                getDispatcher().dispatch(new PluginPaymentResultAction(result));

            }
        }, 2500);
    }
}