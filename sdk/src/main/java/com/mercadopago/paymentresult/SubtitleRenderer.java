package com.mercadopago.paymentresult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mercadopago.R;
import com.mercadopago.components.Renderer;
import com.mercadopago.paymentresult.actions.LogAction;

/**
 * Created by vaserber on 10/13/17.
 */

public class SubtitleRenderer extends Renderer<SubtitleComponent> {

    private View subtitleView;
    private TextView subtitleTextView;

    public View render() {
        subtitleTextView.setText(component.getSubtitle());
        return subtitleView;
    }

    @Override
    protected void bindViews(Context context) {
        subtitleView = LayoutInflater.from(context).inflate(R.layout.mpsdk_subtitle_component, null, false);
        subtitleTextView = ((TextView) subtitleView.findViewById(R.id.subtitle));

        subtitleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                component.getDispatcher().dispatch(new LogAction("Holaaaaaaa!!!!!!!"));
            }
        });
    }
}

