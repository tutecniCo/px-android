package com.mercadopago.uicontrollers.discounts;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mercadopago.R;
import com.mercadopago.customviews.MPTextView;
import com.mercadopago.model.DiscountSearchItem;
import com.mercadopago.preferences.DecorationPreference;

/**
 * Created by mromar on 3/21/17.
 */

public class DiscountSearchOption implements DiscountSearchViewController {

    private static final int COMMENT_MAX_LENGTH = 75;
    private static final String TO_TINT_IMAGES_PREFIX = "grey_";

    protected DiscountSearchItem mItem;
    protected Context mContext;
    protected View mView;
    protected MPTextView mDescription;
    protected MPTextView mComment;
    protected ImageView mIcon;
    protected DecorationPreference mDecorationPreference;
    protected View.OnClickListener mListener;

    public DiscountSearchOption(Context context, DiscountSearchItem item, DecorationPreference decorationPreference) {
        mContext = context;
        mItem = item;
        mDecorationPreference = decorationPreference;
    }

    public View inflateInParent(ViewGroup parent, boolean attachToRoot) {
        mView = LayoutInflater.from(mContext)
                .inflate(R.layout.mpsdk_row_pm_search_item, parent, attachToRoot);
        if (mListener != null) {
            mView.setOnClickListener(mListener);
        }
        return mView;
    }

    @Override
    public View getView() {
        return mView;
    }

    public void initializeControls() {
        mDescription = (MPTextView) mView.findViewById(R.id.mpsdkDescription);
        mComment = (MPTextView) mView.findViewById(R.id.mpsdkComment);
        mIcon = (ImageView) mView.findViewById(R.id.mpsdkImage);

    }

    public void draw() {
        if (mItem.hasDescription()) {
            mDescription.setVisibility(View.VISIBLE);
            mDescription.setText(mItem.getDescription());
        } else {
            mDescription.setVisibility(View.GONE);
        }

        int resourceId = 0;

        Boolean needsTint = itemNeedsTint(mItem);
        String imageId;
//        if(needsTint) {
//            imageId = TO_TINT_IMAGES_PREFIX + mItem.getId();
//        } else {
//            imageId = mItem.getId();
//        }

//        if (mItem.isIconRecommended()) {
//            resourceId = MercadoPagoUtil.getPaymentMethodSearchItemIcon(mContext, imageId);
//        }

//        if (resourceId != 0) {
//            mIcon.setImageResource(resourceId);
//        } else {
//            mIcon.setVisibility(View.GONE);
//        }

        if(needsTint) {
            mIcon.setColorFilter(mDecorationPreference.getBaseColor(), PorterDuff.Mode.MULTIPLY);
        }
    }

    private boolean itemNeedsTint(DiscountSearchItem discountSearchItem) {

        return mDecorationPreference != null && mDecorationPreference.hasColors()
                && (discountSearchItem.isGroup()
                || discountSearchItem.isDiscountType());
    }

    @Override
    public void setOnClickListener(View.OnClickListener listener) {
        mListener = listener;
        if (mView != null) {
            mView.setOnClickListener(listener);
        }
    }
}

