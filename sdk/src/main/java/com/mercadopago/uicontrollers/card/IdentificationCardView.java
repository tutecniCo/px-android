package com.mercadopago.uicontrollers.card;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.mercadopago.R;
import com.mercadopago.customviews.MPEditText;
import com.mercadopago.customviews.MPTextView;
import com.mercadopago.model.Identification;
import com.mercadopago.model.IdentificationType;
import com.mercadopago.util.LayoutUtil;
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
    private MPTextView mCardIdentificationNumberTextView;
    private MPTextView mBaseIdNumberView;

    //Identification Info
    private String mIdentificationNumber;
    private IdentificationType mIdentificationType;
    private String mSize;
    private MPTextView mCardBaseTextAlpha;
    private ImageView mIcon;

    public IdentificationCardView(Context context) {
        this.mContext = context;
    }

    public IdentificationCardView(Context context, String size, Identification identification) {
        this.mContext = context;
        this.mSize = size;
        this.mIdentification = identification;
    }
    

    public View inflateInParent(ViewGroup parent, boolean attachToRoot) {
        mView = LayoutInflater.from(mContext)
                .inflate(R.layout.mpsdk_card_identification, parent, attachToRoot);
        return mView;
    }

    public void initializeControls() {
        mCardContainer = (FrameLayout) mView.findViewById(R.id.mpsdkIdentificationCardContainer);
        mCardBorder = (ImageView) mView.findViewById(R.id.mpsdkCardShadowBorder);
        mBaseIdNumberView = (MPTextView) mView.findViewById(R.id.mpsdkIdentificationCardholderContainer);
        mCardIdentificationNumberTextView = (MPTextView) mView.findViewById(R.id.mpsdkIdNumberView);
        mCardBaseTextAlpha = (MPTextView) mView.findViewById(R.id.mpsdk_base_text_alpha);
        mIcon = (ImageView) mView.findViewById(R.id.mpsdk_id_picture);

        transform();

    }
    

    private void transform() {
        if (mSize == null || mIdentification == null) return;
        if (mSize.equals(CardRepresentationModes.MEDIUM_SIZE)) {
            transformView();
            resize();
        }
    }

    private void transformView() {

        mCardBaseTextAlpha.setText(mIdentification.getType());
        mIdentificationNumber = mIdentification.getNumber();
    }

    private void resize() {
        if (mSize == null) return;
        if (mSize.equals(CardRepresentationModes.MEDIUM_SIZE)) {
            resizeCard(mCardContainer, R.dimen.mpsdk_id_card_size_medium_height, R.dimen.mpsdk_id_card_size_medium_width,
                    CardRepresentationModes.CARD_NUMBER_SIZE_MEDIUM, CardRepresentationModes.CARD_HOLDER_NAME_SIZE_MEDIUM);


        }
    }

    private void resizeCard(ViewGroup cardViewContainer, int cardHeight, int cardWidth, int cardNumberFontSize,
                            int cardHolderNameFontSize) {

        LayoutUtil.resizeViewGroupLayoutParams(cardViewContainer, cardHeight, cardWidth, mContext);
        mCardIdentificationNumberTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, cardNumberFontSize);
        /*mCardBorder.getLayoutParams().height =  R.dimen.mpsdk_card_border_size_medium_height;
        mCardBorder.getLayoutParams().width =  R.dimen.mpsdk_card_border_size_medium_width;*/

        mCardBorder.getLayoutParams().height =  400;
        mCardBorder.getLayoutParams().width =  670;
        mIcon.getLayoutParams().height = 150;
        mIcon.getLayoutParams().width = 150;
        mIcon.requestLayout();
        mCardBorder.requestLayout();

        mBaseIdNumberView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, cardHolderNameFontSize);

    }

    public void setIdentificationNumber(String number) {
        this.mIdentificationNumber = number;
    }

    public void setIdentificationType(IdentificationType identificationType) {
        this.mIdentificationType = identificationType;
    }

    public void draw() {
        if (mIdentificationNumber == null || mIdentificationNumber.length() == 0) {
            mCardIdentificationNumberTextView.setVisibility(View.INVISIBLE);
            mBaseIdNumberView.setVisibility(View.VISIBLE);
        } else {
            mBaseIdNumberView.setVisibility(View.INVISIBLE);
            mCardIdentificationNumberTextView.setVisibility(View.VISIBLE);
            int color = NORMAL_TEXT_VIEW_COLOR;
            String number = MPCardMaskUtil.buildIdentificationNumberWithMask(mIdentificationNumber, mIdentificationType);
            mCardIdentificationNumberTextView.setTextColor(ContextCompat.getColor(mContext, color));
            mCardIdentificationNumberTextView.setText(number);
        }
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
