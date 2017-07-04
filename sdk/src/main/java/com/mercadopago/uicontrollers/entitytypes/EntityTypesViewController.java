package com.mercadopago.uicontrollers.entitytypes;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by marlanti on 3/3/17.
 */

public interface EntityTypesViewController {

    void initializeControls();
    View inflateInParent(ViewGroup parent, boolean attachToRoot);
    View getView();
    void setOnClickListener(View.OnClickListener listener);
    void drawEntityType(String entityType);
}
