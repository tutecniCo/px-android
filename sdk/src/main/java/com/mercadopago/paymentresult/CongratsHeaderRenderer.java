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
    private TextView counterView;
    private ViewGroup subtitleLayout;
    private View containerView;
    private Renderer subtitleRenderer;

    @Override
    protected void bindViews(Context context) {

        containerView = LayoutInflater.from(context).inflate(R.layout.mpsdk_congrats_header, null, false);
        congratsTitle = (TextView) containerView.findViewById(R.id.helloWorld);
        counterView = (TextView) containerView.findViewById(R.id.counter);
        subtitleLayout = (ViewGroup) containerView.findViewById(R.id.child);

        containerView.findViewById(R.id.button_increase).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                component.getDispatcher().dispatch(new IncreaseCountAction());
            }
        });

        containerView.findViewById(R.id.button_decrease).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                component.getDispatcher().dispatch(new DecreaseCountAction());
            }
        });
    }

    @Override
    public View render() {

        counterView.setText(String.valueOf(component.getProps().count));

        subtitleRenderer = RendererFactory.congratsSubtitleRenderer(context, component.getSubtitleComponent());
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

