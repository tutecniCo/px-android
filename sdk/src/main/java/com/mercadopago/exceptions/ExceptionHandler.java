package com.mercadopago.exceptions;

import android.content.Context;

import com.mercadopago.R;

/**
 * Created by mromar on 3/2/16.
 */
public class ExceptionHandler {

    public static String getErrorMessage(Context context, com.mercadopago.exceptions.CheckoutPreferenceException exception) {
        String errorMessage = "";
        switch (exception.getErrorCode()) {
            case com.mercadopago.exceptions.CheckoutPreferenceException.INVALID_ITEM:
                errorMessage = context.getString(R.string.mpsdk_error_message_invalid_item);
                break;

            case com.mercadopago.exceptions.CheckoutPreferenceException.EXPIRED_PREFERENCE:
                errorMessage = context.getString(R.string.mpsdk_error_message_expired_preference);
                break;

            case com.mercadopago.exceptions.CheckoutPreferenceException.INACTIVE_PREFERENCE:
                errorMessage = context.getString(R.string.mpsdk_error_message_inactive_preference);
                break;

            case com.mercadopago.exceptions.CheckoutPreferenceException.INVALID_INSTALLMENTS:
                errorMessage = context.getString(R.string.mpsdk_error_message_invalid_installments);
                break;

            case com.mercadopago.exceptions.CheckoutPreferenceException.EXCLUDED_ALL_PAYMENT_TYPES:
                errorMessage = context.getString(R.string.mpsdk_error_message_excluded_all_payment_type);
                break;
            case com.mercadopago.exceptions.CheckoutPreferenceException.NO_EMAIL_FOUND:
                errorMessage = context.getString(R.string.mpsdk_error_message_email_required);
                break;
        }
        return errorMessage;
    }

    public static String getErrorMessage(Context context, CardTokenException exception) {
        String errorMessage = "";
        switch (exception.getErrorCode()) {
            case CardTokenException.INVALID_EMPTY_CARD:
                errorMessage = context.getString(R.string.mpsdk_invalid_empty_card);
                break;

            case CardTokenException.INVALID_CARD_BIN:
                errorMessage = context.getString(R.string.mpsdk_invalid_card_bin);
                break;

            case CardTokenException.INVALID_CARD_LENGTH:
                errorMessage = context.getString(R.string.mpsdk_invalid_card_length, exception.getExtraParams());
                break;

            case CardTokenException.INVALID_CARD_LUHN:
                errorMessage = context.getString(R.string.mpsdk_invalid_card_luhn);
                break;

            case CardTokenException.INVALID_CVV_LENGTH:
                errorMessage = context.getString(R.string.mpsdk_invalid_cvv_length, exception.getExtraParams());
                break;

            case CardTokenException.INVALID_FIELD:
                errorMessage = context.getString(R.string.mpsdk_invalid_field);
                break;

            case CardTokenException.INVALID_CARD_NUMBER_INCOMPLETE:
                errorMessage = context.getString(R.string.mpsdk_invalid_card_number_incomplete);
                break;

            case CardTokenException.INVALID_PAYMENT_METHOD:
                errorMessage = context.getString(R.string.mpsdk_invalid_payment_method);
                break;
        }
        return errorMessage;
    }
}
