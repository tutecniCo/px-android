package com.mercadopago.plugins.components;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.mercadopago.components.RendererFactory;
import com.mercadopago.constants.PaymentTypes;
import com.mercadopago.core.CheckoutStore;
import com.mercadopago.model.Payment;
import com.mercadopago.model.PaymentData;
import com.mercadopago.model.PaymentMethod;
import com.mercadopago.model.PaymentResult;
import com.mercadopago.plugins.PaymentResultAction;
import com.mercadopago.plugins.PluginComponent;
import com.mercadopago.plugins.model.PaymentMethodInfo;

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

                final Payment payment = new Payment();
                payment.setId(Long.parseLong("123456"));
                payment.setMetadata(null);
                payment.setPaymentMethodId(paymentMethodInfo.id);
                payment.setPaymentTypeId(PaymentTypes.PLUGIN);

                payment.setStatus(Payment.StatusCodes.STATUS_APPROVED);
//                payment.setStatusDetail(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_OTHER_REASON);

                final PaymentData paymentData = new PaymentData();
                final PaymentMethod paymentMethod = new PaymentMethod();
                paymentMethod.setId(paymentMethodInfo.id);
                paymentMethod.setName(paymentMethodInfo.name);
                paymentMethod.setPaymentTypeId(PaymentTypes.PLUGIN);
                paymentData.setPaymentMethod(paymentMethod);

                onPaymentResult(createPaymentResult(payment, paymentData));
            }
        }, 2500);
    }

    public void onPaymentResult(@NonNull final PaymentResult result) {
        getDispatcher().dispatch(new PaymentResultAction(result));
    }

    private PaymentResult createPaymentResult(final Payment payment, final PaymentData paymentData) {
        return new PaymentResult.Builder()
            .setPaymentData(paymentData)
            .setPaymentId(payment.getId())
            .setPaymentStatus(payment.getStatus())
            .setPaymentStatusDetail(payment.getStatusDetail())
            .build();
    }
}