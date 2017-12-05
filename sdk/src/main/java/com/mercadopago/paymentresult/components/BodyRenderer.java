package com.mercadopago.paymentresult.components;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mercadopago.R;
import com.mercadopago.components.Renderer;
import com.mercadopago.components.RendererFactory;

/**
 * Created by vaserber on 10/23/17.
 */

public class BodyRenderer extends Renderer<Body> {
    @Override
    public View render() {
        final View bodyView = LayoutInflater.from(context).inflate(R.layout.mpsdk_payment_result_body, null, false);
        final ViewGroup bodyViewGroup = bodyView.findViewById(R.id.mpsdkPaymentResultContainerBody);

        if (component.hasInstructions()) {
            final Renderer instructionsRenderer = RendererFactory.create(context, component.getInstructionsComponent());
            final View instructions = instructionsRenderer.render();
            bodyViewGroup.addView(instructions);
        } else if (component.hasBodyError()) {
            final Renderer bodyErrorRenderer = RendererFactory.create(context, component.getBodyErrorComponent());
            final View bodyError = bodyErrorRenderer.render();
            bodyViewGroup.addView(bodyError);
        } else {
            if (component.hasReceipt()) {
                final Renderer paymentIdRenderer = RendererFactory.create(context, component.getReceiptComponent());
                final View paymentId = paymentIdRenderer.render();
                bodyViewGroup.addView(paymentId);
            }
            if (component.hasPaymentMethodDescription()) {
                final Renderer paymentMethodRenderer = RendererFactory.create(context, component.getPaymentMethodComponent());
                final View paymentMethod = paymentMethodRenderer.render();
                bodyViewGroup.addView(paymentMethod);
            }
        }

        stretchHeight(bodyViewGroup);
        return bodyView;
    }
}
