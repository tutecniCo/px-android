package com.mercadopago.renderers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mercadopago.R;
import com.mercadopago.components.CongratsHeaderComponent;

/**
 * Created by vaserber on 10/13/17.
 */

public class CongratsHeaderRenderer extends Renderer<CongratsHeaderComponent> {

    private TextView congratsTitle;
    private ViewGroup subtitleLayout;
    private View containerView;
    private final SubtitleRenderer subtitleRenderer;

    public CongratsHeaderRenderer(Context context) {
        super(context);
        subtitleRenderer = new SubtitleRenderer(context);
    }

    @Override
    protected void bindViews(Context context) {
        containerView = LayoutInflater.from(context).inflate(R.layout.mpsdk_congrats_header, null, false);
        congratsTitle = (TextView) containerView.findViewById(R.id.helloWorld);
        subtitleLayout = (ViewGroup) containerView.findViewById(R.id.child);
    }

    @Override
    public View render(CongratsHeaderComponent congratsHeaderComponent) {
        congratsTitle.setText(congratsHeaderComponent.getTitle());
        if (congratsHeaderComponent.hasSubtitle()) {
            View subtitle = renderSubtitle(congratsHeaderComponent);
            this.subtitleLayout.addView(subtitle);
        }

        return containerView;
    }

    private View renderSubtitle(CongratsHeaderComponent congratsHeaderComponent) {
        return subtitleRenderer.render(congratsHeaderComponent.getSubtitleComponent());
    }
}

