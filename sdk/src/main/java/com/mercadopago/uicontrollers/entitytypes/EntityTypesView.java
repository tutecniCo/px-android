package com.mercadopago.uicontrollers.entitytypes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mercadopago.R;
import com.mercadopago.customviews.MPTextView;

/**
 * Created by marlanti on 3/3/17.
 */


public class EntityTypesView implements EntityTypesViewController {



    private Context mContext;
    private View mView;
    private ImageView mEntityTypeImageView;
    private MPTextView mEntityTypeTextView;
    private String mEntityType;

    public EntityTypesView(Context context) {
        this.mContext = context;
    }

    @Override
    public void initializeControls() {
        mEntityTypeImageView = (ImageView) mView.findViewById(R.id.mpsdkEntityTypeImageView);
        mEntityTypeTextView = (MPTextView) mView.findViewById(R.id.mpsdkEntityTypeTextView);
    }

    @Override
    public View inflateInParent(ViewGroup parent, boolean attachToRoot) {
        mView = LayoutInflater.from(mContext)
                .inflate(R.layout.mpsdk_view_entity_type, parent, attachToRoot);
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

            mEntityTypeImageView.setVisibility(View.GONE);
            mEntityTypeTextView.setVisibility(View.VISIBLE);
            mEntityTypeTextView.setText(entityType);

    }

}
