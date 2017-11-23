package com.mercadopago.paymentresult.components;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mercadopago.R;
import com.mercadopago.components.Renderer;
import com.mercadopago.components.RendererFactory;
import com.mercadopago.customviews.MPTextView;
import com.mercadopago.paymentresult.components.AccreditationComment;
import com.mercadopago.paymentresult.components.AccreditationTime;
import com.mercadopago.util.BitmapUtils;
import com.mercadopago.util.ScaleUtil;

import java.util.List;

/**
 * Created by vaserber on 11/13/17.
 */

public class AccreditationTimeRenderer extends Renderer<AccreditationTime> {


    @Override
    public View render() {
        final View accreditationTimeView = LayoutInflater.from(context).inflate(R.layout.mpsdk_accreditation_time, null, false);
        final MPTextView messageTextView = accreditationTimeView.findViewById(R.id.mpsdkAccreditationTimeMessage);
        final ViewGroup accreditationCommentsContainer = accreditationTimeView.findViewById(R.id.mpsdkAccreditationTimeComments);

        renderAccreditationMessage(messageTextView);

        if (component.hasAccreditationComments()) {
            renderAccreditationComments(accreditationCommentsContainer);
        }

        return accreditationTimeView;
    }

    private void renderAccreditationMessage(@NonNull final MPTextView messageTextView) {

        String accreditationMessage = component.props.accreditationMessage;
        if (accreditationMessage == null || accreditationMessage.isEmpty()) {
            messageTextView.setVisibility(View.GONE);
        } else {
            SpannableStringBuilder textspan = new SpannableStringBuilder("  " + accreditationMessage);

            final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.mpsdk_time);
            Bitmap resizedBitmap = BitmapUtils.scaleDown(bitmap, ScaleUtil.getPxFromDp(13, context), true);
            Drawable drawable = new BitmapDrawable(context.getResources(), resizedBitmap);
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_ATOP;
            drawable.setColorFilter(ContextCompat.getColor(context, R.color.mpsdk_warm_grey_with_alpha), mode);

            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());

            textspan.setSpan(new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            messageTextView.setText(textspan);
            messageTextView.setVisibility(View.VISIBLE);
        }
    }

    private void renderAccreditationComments(@NonNull final ViewGroup parent) {

        List<AccreditationComment> commentComponents = component.getAccreditationCommentComponents();

        for (AccreditationComment component: commentComponents) {
            final Renderer commentRenderer = RendererFactory.create(context, component);
            View accreditationComment = commentRenderer.render();
            parent.addView(accreditationComment);
        }
    }

}
