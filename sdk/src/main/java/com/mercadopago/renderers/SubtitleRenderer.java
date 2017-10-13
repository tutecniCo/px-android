package com.mercadopago.renderers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mercadopago.R;
import com.mercadopago.components.SubtitleComponent;

/**
 * Created by vaserber on 10/13/17.
 */

public class SubtitleRenderer extends Renderer<SubtitleComponent> {

    private View subtitleView;
    private TextView subtitleTextView;

    public SubtitleRenderer(Context context) {
        super(context);
    }

    public View render(SubtitleComponent subtitle) {
        subtitleTextView.setText(subtitle.getSubtitle());
        return subtitleView;
    }

    @Override
    protected void bindViews(Context context) {
        subtitleView = LayoutInflater.from(context).inflate(R.layout.mpsdk_subtitle_component, null, false);
        subtitleTextView = ((TextView) subtitleView.findViewById(R.id.subtitle));
    }
}

