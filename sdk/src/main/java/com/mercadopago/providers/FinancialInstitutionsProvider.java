package com.mercadopago.providers;

import com.mercadopago.model.FinancialInstitution;
import com.mercadopago.model.PaymentMethod;
import com.mercadopago.mvp.ResourcesProvider;

import java.util.List;

/**
 * Created by marlanti on 7/7/17.
 */

public interface FinancialInstitutionsProvider extends ResourcesProvider {

    String getStandardErrorMessageGotten();

    String getFinancialInstitutionsTitle();

    List<FinancialInstitution> getFinancialInstitutions(PaymentMethod paymentMethod);

    String getEmptyFinancialInstitutionsErrorMessage();
}
