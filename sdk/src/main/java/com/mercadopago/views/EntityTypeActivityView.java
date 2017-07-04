package com.mercadopago.views;

import com.mercadopago.callbacks.OnSelectedCallback;
import com.mercadopago.model.ApiException;
import com.mercadopago.model.EntityType;
import com.mercadopago.mvp.MvpView;

import java.util.List;

/**
 * Created by marlanti on 3/3/17.
 */

public interface EntityTypeActivityView extends MvpView {
    void initialize();
    void showEntityTypes(List<EntityType> entityTypes, OnSelectedCallback<Integer> dpadSelectionCallback);
    void showError(String message, String errorDetail);
    void showHeader(String title);
    void showLoadingView();
    void stopLoadingView();
    void showTimer();
    void finishWithResult(EntityType entityType);
    void trackScreen();
}
