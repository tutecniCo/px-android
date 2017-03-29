package com.mercadopago.uicontrollers.card;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.mercadopago.R;
import com.mercadopago.customviews.MPAutoResizeTextView;
import com.mercadopago.customviews.MPTextView;
import com.mercadopago.model.Identification;
import com.mercadopago.model.IdentificationType;
import com.mercadopago.util.MPCardMaskUtil;
import com.mercadopago.util.ScaleUtil;

/**
 * Created by vaserber on 10/20/16.
 */

public class IdentificationCardView {

    public static final int NORMAL_TEXT_VIEW_COLOR = R.color.mpsdk_base_text;
    private Identification mIdentification;

    private Context mContext;
    private View mView;

    //View controls
    private FrameLayout mCardContainer;
    private ImageView mCardBorder;
    private MPAutoResizeTextView mCardIdentificationNumberTextView;
    private MPTextView mBaseIdNumberView;

    //Identification Info
    private String mIdentificationNumber;
    private IdentificationType mIdentificationType;
    private String mSize;
    private MPTextView mCardTypeId;

    public IdentificationCardView(Context context) {
        this.mContext = context;
    }

    public IdentificationCardView(Context context, String size, Identification identification) {
        this.mContext = context;
        this.mSize = size;
        this.mIdentification = identification;
    }


    public View inflateInParent(ViewGroup parent, boolean attachToRoot) {

        if (mSize != null && mSize.equals(CardRepresentationModes.MEDIUM_SIZE)) {
            mView = LayoutInflater.from(mContext)
                    .inflate(R.layout.mpsdk_card_identification_medium_size, parent, attachToRoot);
        } else {
            mView = LayoutInflater.from(mContext)
                    .inflate(R.layout.mpsdk_card_identification, parent, attachToRoot);
        }


        return mView;
    }

    public void initializeControls() {

        if (mSize != null && mSize.equals(CardRepresentationModes.MEDIUM_SIZE)) {

            mCardContainer = (FrameLayout) mView.findViewById(R.id.mpsdkIdentificationCardContainer);
            mCardBorder = (ImageView) mView.findViewById(R.id.mpsdkCardShadowBorder);
            mCardIdentificationNumberTextView = (MPAutoResizeTextView) mView.findViewById(R.id.mpsdk_id_number);
            mCardTypeId = (MPTextView) mView.findViewById(R.id.mpsdk_type_id);

            transformView();

        } else {
            mCardContainer = (FrameLayout) mView.findViewById(R.id.mpsdkIdentificationCardContainer);
            mCardBorder = (ImageView) mView.findViewById(R.id.mpsdkCardShadowBorder);
            mBaseIdNumberView = (MPTextView) mView.findViewById(R.id.mpsdkIdentificationCardholderContainer);
            mCardIdentificationNumberTextView = (MPAutoResizeTextView) mView.findViewById(R.id.mpsdkIdNumberView);
        }

    }


    private void transformView() {

        if (mSize == null || mIdentification == null) return;
        if (mSize.equals(CardRepresentationModes.MEDIUM_SIZE)) {
            mCardTypeId.setText(mIdentification.getType());
            mIdentificationNumber = mIdentification.getNumber();
            mCardIdentificationNumberTextView.setText(mIdentificationNumber);
            mCardIdentificationNumberTextView.setMinTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, mContext.getResources().getDisplayMetrics()));
            decorateIdentificationNumberTextView();
            //FIXME: Si vienen más de 10 dígitos cambio el diseño. Caso Otro con 20.
        }
    }


    public void setIdentificationNumber(String number) {
        this.mIdentificationNumber = number;
    }

    public void setIdentificationType(IdentificationType identificationType) {
        this.mIdentificationType = identificationType;
    }

    public void draw() {
        if (mSize == null || !mSize.equals(CardRepresentationModes.MEDIUM_SIZE)) {
            if (mIdentificationNumber == null || mIdentificationNumber.length() == 0) {
                mCardIdentificationNumberTextView.setVisibility(View.INVISIBLE);
                mBaseIdNumberView.setVisibility(View.VISIBLE);
            } else {
                mBaseIdNumberView.setVisibility(View.INVISIBLE);
                mCardIdentificationNumberTextView.setVisibility(View.VISIBLE);
                decorateIdentificationNumberTextView();
            }
        }

    }

    private void decorateIdentificationNumberTextView() {
        int color = NORMAL_TEXT_VIEW_COLOR;
        String number = MPCardMaskUtil.buildIdentificationNumberWithMask(mIdentificationNumber, mIdentificationType);
        mCardIdentificationNumberTextView.setTextColor(ContextCompat.getColor(mContext, color));
        mCardIdentificationNumberTextView.setText(number);
    }

    public void show() {
        mCardContainer.setVisibility(View.VISIBLE);
    }

    public void hide() {
        mCardContainer.setVisibility(View.GONE);
    }

    public void decorateCardBorder(int borderColor) {
        GradientDrawable cardShadowRounded = (GradientDrawable) ContextCompat.getDrawable(mContext, R.drawable.mpsdk_card_shadow_rounded);
        cardShadowRounded.setStroke(ScaleUtil.getPxFromDp(6, mContext), borderColor);
        mCardBorder.setImageDrawable(cardShadowRounded);
    }

    public View getView() {
        return mView;
    }
}
