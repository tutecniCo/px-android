package com.mercadopago.views;

import com.mercadopago.model.ApiException;
import com.mercadopago.model.Identification;
import com.mercadopago.model.IdentificationType;

import java.util.List;

/**
 * Created by marlanti on 3/16/17.
 */

public interface IdentificationActivityView {

    void initializeIdentificationTypes(List<IdentificationType> identificationTypes);
    void setIdentificationTypeListeners();
    void setIdentificationNumberListeners();
    void hideIdentificationInput();
    void showIdentificationInput();
    void setIdentificationNumberRestrictions(String type);
    void setErrorIdentificationNumber();
    void clearErrorIdentificationNumber();
    void setIdentificationNumber(String identificationNumber);
    void setErrorView(String message);
    void clearErrorView();
    void startErrorView(String message, String errorDetail);
    void showApiExceptionError(ApiException exception);
    void onInvalidStart(String message);
    void onValidStart();
    void showInputContainer();
    void initializeTitle();
    void setSoftInputMode();
    void finishWithResult(IdentificationType identificationType, Identification identification);
    void setNextButtonListeners();
}
