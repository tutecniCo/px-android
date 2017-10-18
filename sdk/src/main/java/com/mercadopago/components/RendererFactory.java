package com.mercadopago.components;

import android.content.Context;
import android.view.View;

import com.mercadopago.paymentresult.CongratsHeaderComponent;
import com.mercadopago.paymentresult.CongratsHeaderRenderer;
import com.mercadopago.paymentresult.SubtitleComponent;
import com.mercadopago.paymentresult.SubtitleRenderer;

/**
 * Created by vaserber on 10/18/17.
 */

public class RendererFactory {

    public static Renderer<CongratsHeaderComponent>  congratsHeaderRenderer(final Context context, ActionDispatcher dispatcher) {
        CongratsHeaderComponent congratsHeaderComponent = new CongratsHeaderComponent("Hola Mundo!", "Soy un subtitulo!", dispatcher);
        CongratsHeaderRenderer congratsHeaderRenderer = new CongratsHeaderRenderer(congratsHeaderComponent, context);
        return congratsHeaderRenderer;
    }

    public static Renderer<SubtitleComponent>  congratsSubtitleRenderer(final Context context,
                                                                        final SubtitleComponent component) {
        SubtitleRenderer subtitleRenderer = new SubtitleRenderer(component, context);
        return subtitleRenderer;
    }
}
