package com.mercadopago.uicontrollers.financialInstitutions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mercadopago.R;
import com.mercadopago.customviews.MPTextView;
import com.mercadopago.model.FinancialInstitution;

/**
 * Created by marlanti on 3/13/17.
 */

public class FinancialInstitutionsView implements FinancialInstitutionsViewController {

    public static final String CARD_IMAGE_PREFIX = "mpsdk_financial_institution_";

    private Context mContext;
    private View mView;
    private ImageView mFinancialInstitutionImageView;
    private MPTextView mFinancialInstitutionTextView;
    private FinancialInstitution mFinancialInstitution;

    public FinancialInstitutionsView(Context context) {
        this.mContext = context;
    }

    @Override
    public void initializeControls() {
        mFinancialInstitutionImageView = (ImageView) mView.findViewById(R.id.mpsdkFinancialInstitutionImageView);
        mFinancialInstitutionTextView = (MPTextView) mView.findViewById(R.id.mpsdkFinancialInstitutionTextView);
    }

    @Override
    public View inflateInParent(ViewGroup parent, boolean attachToRoot) {
        mView = LayoutInflater.from(mContext)
                .inflate(R.layout.mpsdk_view_financial_institution, parent, attachToRoot);
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
    public void drawFinancialInstitution(FinancialInstitution financialInstitution) {
        this.mFinancialInstitution = financialInstitution;

        int image = getCardImage(financialInstitution);
        if (image == 0) {
            mFinancialInstitutionImageView.setVisibility(View.GONE);
            mFinancialInstitutionTextView.setVisibility(View.VISIBLE);
            mFinancialInstitutionTextView.setText(financialInstitution.getDescription());
        } else {
            mFinancialInstitutionImageView.setVisibility(View.VISIBLE);
            mFinancialInstitutionTextView.setVisibility(View.GONE);
            mFinancialInstitutionImageView.setImageResource(getCardImage(financialInstitution));
        }
    }

    private int getCardImage(FinancialInstitution financialInstitution) {
        String imageName = CARD_IMAGE_PREFIX + String.valueOf(financialInstitution.getId());
        return mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName());
    }

}

