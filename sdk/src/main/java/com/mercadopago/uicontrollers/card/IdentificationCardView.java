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
    private static final int BIG_IDENTIFICATION_NUMBER_LENGHT = 15;
    private static final int IDENTIFICATION_NUMBER_MIN_SIZE = 14;
    private static final int LIMIT_IDENTIFICATION_NUMBER_MIN_SIZE = 12;

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
    private MPTextView mCardIdentificationBigNumberTextView;

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
            mCardTypeId = (MPTextView) mView.findViewById(R.id.mpsdk_type_id);

            if (isBigIdentificationNumber()){
                mCardIdentificationBigNumberTextView = (MPTextView) mView.findViewById(R.id.mpsdk_id_number_big);
                mCardIdentificationBigNumberTextView.setVisibility(View.VISIBLE);
            }else{
                mCardIdentificationNumberTextView = (MPAutoResizeTextView) mView.findViewById(R.id.mpsdk_id_number);
                mCardIdentificationNumberTextView.setVisibility(View.VISIBLE);
            }


            transformView();

        } else {
            mCardContainer = (FrameLayout) mView.findViewById(R.id.mpsdkIdentificationCardContainer);
            mCardBorder = (ImageView) mView.findViewById(R.id.mpsdkCardShadowBorder);
            mBaseIdNumberView = (MPTextView) mView.findViewById(R.id.mpsdkIdentificationCardholderContainer);
            mCardIdentificationNumberTextView = (MPAutoResizeTextView) mView.findViewById(R.id.mpsdkIdNumberView);
        }

    }

    private boolean isBigIdentificationNumber(){
        return mIdentification.getNumber().trim().length() > BIG_IDENTIFICATION_NUMBER_LENGHT;
    }


    private void transformView() {

        if (mSize == null || mIdentification == null) return;
        if (mSize.equals(CardRepresentationModes.MEDIUM_SIZE)) {
            mCardTypeId.setText(mIdentification.getType());
            mIdentificationNumber = mIdentification.getNumber();
            if(!isBigIdentificationNumber()){
                mCardIdentificationNumberTextView.setText(mIdentificationNumber);
                setMinTextSize();
                decorateIdentificationNumberTextView();
            }else{
                mCardIdentificationBigNumberTextView.setText(mIdentificationNumber);
                decorateIdentificationBigNumberTextView();
            }


        }
    }

    private void setMinTextSize() {
        if(mSize!=null && mSize.equals(CardRepresentationModes.MEDIUM_SIZE) && !isBigIdentificationNumber()){
            if (isLimitTextSize()){
                mCardIdentificationNumberTextView.setMinTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, LIMIT_IDENTIFICATION_NUMBER_MIN_SIZE, mContext.getResources().getDisplayMetrics()));
            }else
            {
                mCardIdentificationNumberTextView.setMinTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, IDENTIFICATION_NUMBER_MIN_SIZE, mContext.getResources().getDisplayMetrics()));
            }

        }

    }

    private boolean isLimitTextSize() {
        return getIdentificationNumberLength() == LIMIT_IDENTIFICATION_NUMBER_MIN_SIZE || getIdentificationNumberLength() == LIMIT_IDENTIFICATION_NUMBER_MIN_SIZE+1;
    }

    private int getIdentificationNumberLength(){
        return mIdentification.getNumber().trim().length();
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
    private void decorateIdentificationBigNumberTextView() {
        int color = NORMAL_TEXT_VIEW_COLOR;
        mCardIdentificationBigNumberTextView.setTextColor(ContextCompat.getColor(mContext, color));
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
