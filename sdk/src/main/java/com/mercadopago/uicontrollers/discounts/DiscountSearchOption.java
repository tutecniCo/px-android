package com.mercadopago.uicontrollers.discounts;

import android.content.Context;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mercadopago.R;
import com.mercadopago.customviews.MPTextView;
import com.mercadopago.model.DiscountSearchItem;
import com.mercadopago.preferences.DecorationPreference;
import com.mercadopago.util.CurrenciesUtil;
import com.mercadopago.util.MercadoPagoUtil;

import java.math.BigDecimal;

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
        int resourceId = 0;

        if (mItem.hasDescription()) {
            mDescription.setVisibility(View.VISIBLE);
            mDescription.setText(mItem.getDescription());
        } else {
            mDescription.setVisibility(View.GONE);
        }

        if (mItem.hasCouponAmount()) {
            StringBuilder discountText = new StringBuilder();
            Spanned spannedDiscountText;

            discountText.append("Descuento ");
            discountText.append(CurrenciesUtil.formatNumber(mItem.getCouponAmount(), mItem.getCurrency()));

            spannedDiscountText = CurrenciesUtil.formatCurrencyInText(mItem.getCouponAmount(),
                    mItem.getCurrency(), discountText.toString(), false, true);

            mComment.setText(spannedDiscountText);
            mComment.setTextColor(mContext.getResources().getColor(R.color.mpsdk_color_payer_costs_no_rate));
        }

        resourceId = MercadoPagoUtil.getSearchItemIcon(mContext, mItem.getId().toString());

        if (resourceId != 0) {
            mIcon.setImageResource(resourceId);
        } else {
            mIcon.setVisibility(View.GONE);
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

    private Spanned getFormattedAmount(BigDecimal amount, String currencyId) {
        String originalNumber = CurrenciesUtil.formatNumber(amount, currencyId);
        Spanned amountText = CurrenciesUtil.formatCurrencyInText(amount, currencyId, originalNumber, false, true);
        return amountText;
    }
}

