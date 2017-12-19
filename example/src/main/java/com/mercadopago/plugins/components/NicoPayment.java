package com.mercadopago.plugins.components;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.mercadopago.components.ExternalComponent;
import com.mercadopago.components.RendererFactory;
import com.mercadopago.constants.PaymentTypes;
import com.mercadopago.model.Payment;
import com.mercadopago.model.PaymentData;
import com.mercadopago.model.PaymentMethod;
import com.mercadopago.model.PaymentResult;
import com.mercadopago.plugins.PaymentResultAction;

import java.math.BigDecimal;

/**
 * Created by nfortuna on 12/13/17.
 */

public class NicoPayment extends ExternalComponent {

    final Handler handler = new Handler();

    static {
        RendererFactory.register(NicoPayment.class, NicoPaymentRenderer.class);
    }

    public NicoPayment() {
        executePayment();
    }

    public void executePayment() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                final Payment payment = new Payment();
                payment.setBinaryMode(true);
//                payment.setCallForAuthorizeId("123");
                payment.setCaptured(false);
                payment.setCollectorId("1234567");
                payment.setCouponAmount(new BigDecimal("19"));
                payment.setCurrencyId("ARS");
                payment.setDescription("some desc");
                payment.setDifferentialPricingId(Long.parseLong("789"));
                payment.setExternalReference("some ext ref");
                payment.setId(Long.parseLong("123456"));
                payment.setInstallments(3);
                payment.setIssuerId(3);
                payment.setLiveMode(true);
                payment.setMetadata(null);
//                payment.setNotificationUrl("http://some_url.com");
//                payment.setPaymentMethodId("visa");
                payment.setPaymentTypeId("nicopay");
                payment.setRefunds(null);
                payment.setStatementDescriptor("statement");
                payment.setStatus("approved");
//                payment.setStatusDetail("accredited");
                payment.setTransactionAmount(new BigDecimal(10.50));

                final PaymentData paymentData = new PaymentData();
                paymentData.setTransactionAmount(new BigDecimal(10.50));


                final PaymentMethod paymentMethod = new PaymentMethod();
                paymentMethod.setId("nicopay");
                paymentMethod.setPaymentTypeId(PaymentTypes.PLUGIN);
                paymentData.setPaymentMethod(paymentMethod);

                onPaymentResult(createPaymentResult(payment, paymentData));

            }
        }, 3000);
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
            .setPayerEmail("payer@gmail.com")
            .setStatementDescription(payment.getStatementDescriptor())
            .build();
    }
}