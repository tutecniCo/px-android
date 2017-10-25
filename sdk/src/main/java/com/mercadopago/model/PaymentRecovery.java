package com.mercadopago.model;

/**
 * Created by mromar on 8/19/16.
 */
public class PaymentRecovery {
    private com.mercadopago.model.Token mToken;
    private String mStatus;
    private String mStatusDetail;
    private com.mercadopago.model.PaymentMethod mPaymentMethod;
    private com.mercadopago.model.PayerCost mPayerCost;
    private com.mercadopago.model.Issuer mIssuer;

    public PaymentRecovery(com.mercadopago.model.Token token, com.mercadopago.model.PaymentMethod paymentMethod, com.mercadopago.model.PayerCost payerCost, com.mercadopago.model.Issuer issuer, String paymentStatus, String paymentStatusDetail) {

        validate(token, paymentMethod, payerCost, issuer, paymentStatus, paymentStatusDetail);
        mToken = token;
        mPaymentMethod = paymentMethod;
        mPayerCost = payerCost;
        mIssuer = issuer;
        mStatusDetail = paymentStatusDetail;
    }

    private void validate(com.mercadopago.model.Token token, com.mercadopago.model.PaymentMethod paymentMethod, com.mercadopago.model.PayerCost payerCost, com.mercadopago.model.Issuer issuer, String paymentStatus, String paymentStatusDetail) {
        if (token == null) {
            throw new IllegalStateException("token is null");
        }

        if (paymentMethod == null) {
            throw new IllegalStateException("payment method is null");
        }

        if (payerCost == null) {
            throw new IllegalStateException("payer cost is null");
        }

        if (issuer == null) {
            throw new IllegalStateException("issuer is null");
        }

        if (!isRecoverablePaymentStatus(paymentStatus, paymentStatusDetail)) {
            throw new IllegalStateException("this payment is not recoverable");
        }
    }

    public Token getToken() {
        return mToken;
    }

    public PaymentMethod getPaymentMethod() {
        return mPaymentMethod;
    }

    public PayerCost getPayerCost() {
        return mPayerCost;
    }

    public com.mercadopago.model.Issuer getIssuer() {
        return mIssuer;
    }

    public boolean isTokenRecoverable() {
        return isStatusDetailRecoverable(mStatusDetail);
    }

    private boolean isRecoverablePaymentStatus(String paymentStatus, String paymentStatusDetail) {
        return com.mercadopago.model.Payment.StatusCodes.STATUS_REJECTED.equals(paymentStatus) && isPaymentStatusRecoverable(paymentStatusDetail);
    }

    private boolean isPaymentStatusRecoverable(String statusDetail) {
        return com.mercadopago.model.Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_BAD_FILLED_OTHER.equals(statusDetail) ||
                com.mercadopago.model.Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_BAD_FILLED_CARD_NUMBER.equals(statusDetail) ||
                com.mercadopago.model.Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_BAD_FILLED_SECURITY_CODE.equals(statusDetail) ||
                com.mercadopago.model.Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_BAD_FILLED_DATE.equals(statusDetail) ||
                com.mercadopago.model.Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_CALL_FOR_AUTHORIZE.equals(statusDetail) ||
                com.mercadopago.model.Payment.StatusCodes.STATUS_DETAIL_INVALID_ESC.equals(statusDetail);
    }

    private Boolean isStatusDetailRecoverable(String statusDetail) {
        return statusDetail != null &&
                (com.mercadopago.model.Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_CALL_FOR_AUTHORIZE.equals(statusDetail) ||
                com.mercadopago.model.Payment.StatusCodes.STATUS_DETAIL_INVALID_ESC.equals(statusDetail));
    }

    public boolean isStatusDetailCallForAuthorize() {
        return mStatusDetail != null && com.mercadopago.model.Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_CALL_FOR_AUTHORIZE.equals(mStatusDetail);
    }

    public boolean isStatusDetailInvalidESC() {
        return mStatusDetail != null && Payment.StatusCodes.STATUS_DETAIL_INVALID_ESC.equals(mStatusDetail);
    }

}
