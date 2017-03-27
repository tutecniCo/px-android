package com.mercadopago.uicontrollers.entitytypes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mercadopago.R;
import com.mercadopago.customviews.MPTextView;
import com.mercadopago.model.Issuer;

/**
 * Created by marlanti on 3/3/17.
 */


public class EntityTypesView implements EntityTypesViewController {

    public static final String CARD_IMAGE_PREFIX = "mpsdk_issuer_";

    private Issuer mIssuer;

    private Context mContext;
    private View mView;
    private ImageView mIssuerImageView;
    private MPTextView mIssuerTextView;
    private String mEntityType;

    public EntityTypesView(Context context) {
        this.mContext = context;
    }

    @Override
    public void initializeControls() {
        mIssuerImageView = (ImageView) mView.findViewById(R.id.mpsdkIssuerImageView);
        mIssuerTextView = (MPTextView) mView.findViewById(R.id.mpsdkIssuerTextView);
    }

    @Override
    public View inflateInParent(ViewGroup parent, boolean attachToRoot) {
        mView = LayoutInflater.from(mContext)
                .inflate(R.layout.mpsdk_view_issuer, parent, attachToRoot);
        return mView;
    }

    @Override
    public View getView() {
        return mView;
    }

    @Override
    public void setOnClickListener(View.OnClickListener listener) {
        mView.setOnClickListener(listener);
    }

    @Override
    public void drawEntityType(String entityType) {
        this.mEntityType = entityType;

            mIssuerImageView.setVisibility(View.GONE);
            mIssuerTextView.setVisibility(View.VISIBLE);
            mIssuerTextView.setText(entityType);

    }

}
