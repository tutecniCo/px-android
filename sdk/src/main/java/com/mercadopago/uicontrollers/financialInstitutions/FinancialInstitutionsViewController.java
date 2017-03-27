package com.mercadopago.uicontrollers.financialInstitutions;

import android.view.View;
import android.view.ViewGroup;

import com.mercadopago.model.FinancialInstitution;

/**
 * Created by marlanti on 3/13/17.
 */

public interface FinancialInstitutionsViewController {
    void initializeControls();
    View inflateInParent(ViewGroup parent, boolean attachToRoot);
    View getView();
    void setOnClickListener(View.OnClickListener listener);
    void drawFinancialInstitution(FinancialInstitution financialInstitution);
}
