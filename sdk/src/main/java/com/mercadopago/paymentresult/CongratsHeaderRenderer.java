package com.mercadopago.paymentresult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mercadopago.R;
import com.mercadopago.components.Renderer;
import com.mercadopago.components.RendererFactory;

/**
 * Created by vaserber on 10/13/17.
 */

public class CongratsHeaderRenderer extends Renderer<CongratsHeaderComponent> {

    private TextView congratsTitle;
    private ViewGroup subtitleLayout;
    private View containerView;
    private final Renderer subtitleRenderer;

    public CongratsHeaderRenderer(CongratsHeaderComponent component, Context context) {
        super(component, context);
        subtitleRenderer = RendererFactory.congratsSubtitleRenderer(context, component.getSubtitleComponent());
    }

    @Override
    protected void bindViews(Context context) {
        containerView = LayoutInflater.from(context).inflate(R.layout.mpsdk_congrats_header, null, false);
        congratsTitle = (TextView) containerView.findViewById(R.id.helloWorld);
        subtitleLayout = (ViewGroup) containerView.findViewById(R.id.child);
    }

    @Override
    public View render() {
        congratsTitle.setText(component.getTitle());
        if (component.hasSubtitle()) {
            View subtitle = renderSubtitle(component);
            this.subtitleLayout.addView(subtitle);
        }

        return containerView;
    }

    private View renderSubtitle(CongratsHeaderComponent congratsHeaderComponent) {
        return subtitleRenderer.render();
    }
}

