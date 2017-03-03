package com.mercadopago.views;

import com.mercadopago.model.ApiException;
import com.mercadopago.model.Issuer;

import java.util.List;

/**
 * Created by marlanti on 3/3/17.
 */

public interface EntityTypeActivityView {
    void onValidStart();
    void onInvalidStart(String message);
    void initializeEntityTypes(List<String> entityTypesList);
    void showApiExceptionError(ApiException exception);
    void startErrorView(String message, String errorDetail);
    void showHeader();
    void showLoadingView();
    void stopLoadingView();
    void finishWithResult(String entityType);

}
