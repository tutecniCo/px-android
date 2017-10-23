package com.mercadopago.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.mercadopago.components.Action;
import com.mercadopago.components.ActionsListener;
import com.mercadopago.constants.PaymentTypes;
import com.mercadopago.model.Payment;
import com.mercadopago.model.PaymentResult;
import com.mercadopago.model.Site;
import com.mercadopago.mvp.MvpPresenter;
import com.mercadopago.paymentresult.PaymentResultNavigator;
import com.mercadopago.paymentresult.actions.DecreaseCountAction;
import com.mercadopago.paymentresult.actions.IncreaseCountAction;
import com.mercadopago.paymentresult.actions.LogAction;
import com.mercadopago.providers.PaymentResultProvider;
import com.mercadopago.util.MercadoPagoUtil;
import com.mercadopago.views.PaymentResultPropsView;

import java.math.BigDecimal;

import static com.mercadopago.util.TextUtils.isEmpty;

public class PaymentResultPresenter extends MvpPresenter<PaymentResultPropsView, PaymentResultProvider> implements ActionsListener {
    private Boolean discountEnabled;
    private PaymentResult paymentResult;
    private Site site;
    private BigDecimal amount;
    private PaymentResultNavigator navigator;

    public PaymentResultPresenter(@NonNull final PaymentResultNavigator navigator) {
        this.navigator = navigator;
    }

    public void initialize() {
        try {
            validateParameters();
            onValidStart();
        } catch (IllegalStateException exception) {
            onInvalidStart(exception.getMessage());
        }
    }

    private void validateParameters() {
        if (paymentResult == null) {
            throw new IllegalStateException("payment result is null");
        } else if (paymentResult.getPaymentData() == null) {
            throw new IllegalStateException("payment data is null");
        }
        if (!isStatusValid()) {
            throw new IllegalStateException("payment not does not have status");
        }
    }

    private void onValidStart() {
        if (paymentResult.getPaymentStatusDetail() != null && paymentResult.getPaymentStatusDetail().equals(Payment.StatusCodes.STATUS_DETAIL_PENDING_WAITING_PAYMENT)) {
            navigator.showInstructions(site, amount, paymentResult);
        } else if (paymentResult.getPaymentStatus().equals(Payment.StatusCodes.STATUS_IN_PROCESS) ||
                paymentResult.getPaymentStatus().equals(Payment.StatusCodes.STATUS_PENDING)) {
            navigator.showPending(paymentResult);
        } else if (isCardOrAccountMoney()) {
            startPaymentsOnResult();
        } else if (paymentResult.getPaymentStatus().equals(Payment.StatusCodes.STATUS_REJECTED)) {
            navigator.showRejection(paymentResult);
        }
    }

    protected void onInvalidStart(final String errorDetail) {
        getView().showError(getResourcesProvider().getStandardErrorMessage(), errorDetail);
    }

    private boolean isCardOrAccountMoney() {
        return MercadoPagoUtil.isCard(paymentResult.getPaymentData().getPaymentMethod().getPaymentTypeId()) ||
                paymentResult.getPaymentData().getPaymentMethod().getPaymentTypeId().equals(PaymentTypes.ACCOUNT_MONEY);
    }

    private void startPaymentsOnResult() {
        if (paymentResult.getPaymentStatus().equals(Payment.StatusCodes.STATUS_APPROVED)) {
            navigator.showCongrats(site, amount, paymentResult, discountEnabled);
        } else if (paymentResult.getPaymentStatus().equals(Payment.StatusCodes.STATUS_REJECTED)) {
            if (isStatusDetailValid() && paymentResult.getPaymentStatusDetail().equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_CALL_FOR_AUTHORIZE)) {
                navigator.showCallForAuthorize(site, paymentResult);
            } else {
                navigator.showRejection(paymentResult);
            }
        } else {
            getView().showError(getResourcesProvider().getStandardErrorMessage());
        }
    }

    private Boolean isStatusValid() {
        return !isEmpty(paymentResult.getPaymentStatus());
    }

    private Boolean isStatusDetailValid() {
        return !isEmpty(paymentResult.getPaymentStatusDetail());
    }

    public void setDiscountEnabled(final Boolean discountEnabled) {
        this.discountEnabled = discountEnabled;
    }

    public void setPaymentResult(final PaymentResult paymentResult) {
        this.paymentResult = paymentResult;
        getView().setPropPaymentResult(paymentResult);
    }

    public void setSite(final Site site) {
        this.site = site;
    }

    public void setAmount(final BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public void onAction(final Action action) {
        if (action instanceof LogAction) {
            Log.d("log", ((LogAction) action).text);
        } else if (action instanceof IncreaseCountAction) {
            getView().increaseCounter();
        } else if (action instanceof DecreaseCountAction) {
            getView().decreaseCounter();
        }
    }
}
