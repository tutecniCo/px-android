package com.mercadopago.views;

import com.mercadopago.callbacks.OnSelectedCallback;
import com.mercadopago.model.Discount;
import com.mercadopago.model.DiscountSearchItem;
import com.mercadopago.model.PaymentMethodSearchItem;
import com.mercadopago.mvp.MvpView;

import java.util.List;

/**
 * Created by mromar on 11/29/16.
 */

public interface DiscountsActivityView extends MvpView {

    void drawSummary();

    void requestDiscountCode();

    void finishWithResult();

    void finishWithCancelResult();

    void showCodeInputError(String message);

    void clearErrorView();

    void showProgress();

    void hideProgress();

    void showEmptyDiscountCodeError();

    void hideKeyboard();

    void setSoftInputModeSummary();

    void hideDiscountSummary();

    void showSearchItems(List<DiscountSearchItem> discountSearchItems, OnSelectedCallback<DiscountSearchItem> discountSearchItemSelectionCallback);

    void hideDiscountSearchSelection();
}
