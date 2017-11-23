package com.mercadopago.paymentresult.components;

import android.support.annotation.NonNull;

import com.mercadopago.R;
import com.mercadopago.components.ActionDispatcher;
import com.mercadopago.components.Component;
import com.mercadopago.components.LoadingComponent;
import com.mercadopago.constants.PaymentMethods;
import com.mercadopago.constants.PaymentTypes;
import com.mercadopago.model.Payment;
import com.mercadopago.model.PaymentResult;
import com.mercadopago.paymentresult.PaymentResultProvider;
import com.mercadopago.paymentresult.model.Badge;
import com.mercadopago.paymentresult.props.HeaderProps;
import com.mercadopago.paymentresult.props.PaymentResultBodyProps;
import com.mercadopago.paymentresult.props.PaymentResultProps;

/**
 * Created by vaserber on 10/20/17.
 */

public class PaymentResultContainer extends Component<PaymentResultProps> {

    private static final int DEFAULT_BACKGROUND_COLOR = R.color.mpsdk_blue_MP;
    private static final int GREEN_BACKGROUND_COLOR = R.color.mpsdk_green_MP;
    private static final int RED_BACKGROUND_COLOR = R.color.mpsdk_red_MP;
    private static final int ORANGE_BACKGROUND_COLOR = R.color.mpsdk_orange_MP;

    private static final int DEFAULT_ICON_IMAGE = R.drawable.mpsdk_icon_default;
    private static final int ITEM_ICON_IMAGE = R.drawable.mpsdk_icon_product;
    private static final int CARD_ICON_IMAGE = R.drawable.mpsdk_icon_card;
    private static final int BOLETO_ICON_IMAGE = R.drawable.mpsdk_icon_boleto;

    //armar componente Badge que va como hijo
    private static final int DEFAULT_BADGE_IMAGE = 0;
    private static final int CHECK_BADGE_IMAGE = R.drawable.mpsdk_badge_check;
    private static final int PENDING_BADGE_IMAGE = R.drawable.mpsdk_badge_pending;
    private static final int ERROR_BADGE_IMAGE = R.drawable.mpsdk_badge_error;
    private static final int WARNING_BADGE_IMAGE = R.drawable.mpsdk_badge_warning;

    public PaymentResultProvider resourcesProvider;

    public PaymentResultContainer(@NonNull final ActionDispatcher dispatcher,
                                  @NonNull final PaymentResultProvider provider) {
        super(new PaymentResultProps.Builder().build(), dispatcher);
        this.resourcesProvider = provider;
    }

    public boolean isLoading() {
        return props.loading;
    }

    public LoadingComponent getLoadingComponent() {
        return new LoadingComponent(getDispatcher());
    }

    public HeaderComponent getHeaderComponent() {

        HeaderProps headerProps = new HeaderProps.Builder()
                .setHeight(props.headerMode)
                .setBackground(getBackground(props.paymentResult))
                .setIconImage(getIconImage(props))
                .setBadgeImage(getBadgeImage(props))
                .setTitle(getTitle(props))
                .setLabel(getLabel(props))
                .setAmountFormat(props.amountFormat)
                .build();

        return new HeaderComponent(headerProps, getDispatcher());
    }

    public PaymentResultBodyComponent getBodyComponent() {
        PaymentResultBodyComponent body = null;
        if (props.paymentResult != null) {
            final PaymentResultBodyProps bodyProps = new PaymentResultBodyProps(props.paymentResult.getPaymentStatus());
            body = new PaymentResultBodyComponent(bodyProps, getDispatcher());
        }
        return body;
    }

    public PaymentResultFooterComponent getFooterComponent() {
        PaymentResultFooterComponent footer = null;
        if (props.paymentResult != null) {
            footer = new PaymentResultFooterComponent(props.paymentResult.getPaymentStatus(), getDispatcher());
        }
        return footer;
    }

    private int getBackground(@NonNull final PaymentResult paymentResult) {

        if (paymentResult == null) {
            return DEFAULT_BACKGROUND_COLOR;
        } else if (isGreenBackground(paymentResult)) {
            return GREEN_BACKGROUND_COLOR;
        } else if (isRedBackground(paymentResult)) {
            return RED_BACKGROUND_COLOR;
        } else if (isOrangeBackground(paymentResult)) {
            return ORANGE_BACKGROUND_COLOR;
        } else {
            return DEFAULT_BACKGROUND_COLOR;
        }
    }

    private boolean isGreenBackground(@NonNull final PaymentResult paymentResult) {
        return (paymentResult.getPaymentStatus().equals(Payment.StatusCodes.STATUS_APPROVED) ||
                paymentResult.getPaymentStatus().equals(Payment.StatusCodes.STATUS_IN_PROCESS) ||
                paymentResult.getPaymentStatus().equals(Payment.StatusCodes.STATUS_PENDING));
    }

    private boolean isRedBackground(@NonNull final PaymentResult paymentResult) {
        String status = paymentResult.getPaymentStatus();
        String statusDetail = paymentResult.getPaymentStatusDetail();
        return status.equals(Payment.StatusCodes.STATUS_REJECTED) &&
                (statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_OTHER_REASON) ||
                        statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_REJECTED_REJECTED_BY_BANK) ||
                        statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_REJECTED_REJECTED_INSUFFICIENT_DATA) ||
                        statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_DUPLICATED_PAYMENT) ||
                        statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_MAX_ATTEMPTS) ||
                        statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_REJECTED_HIGH_RISK));

    }

    private boolean isOrangeBackground(@NonNull final PaymentResult paymentResult) {
        String status = paymentResult.getPaymentStatus();
        String statusDetail = paymentResult.getPaymentStatusDetail();
        return status.equals(Payment.StatusCodes.STATUS_REJECTED) &&
                (statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_INVALID_ESC) ||
                        statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_CALL_FOR_AUTHORIZE) ||
                        statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_BAD_FILLED_CARD_NUMBER) ||
                        statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_BAD_FILLED_DATE) ||
                        statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_BAD_FILLED_SECURITY_CODE) ||
                        statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_BAD_FILLED_OTHER) ||
                        statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_CARD_DISABLED) ||
                        statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_INSUFFICIENT_AMOUNT));

    }

    private int getIconImage(@NonNull final PaymentResultProps props) {
        if (props.hasCustomizedIcon()) {
            return props.getPreferenceIcon();
        } else if (props.paymentResult == null) {
            return DEFAULT_ICON_IMAGE;
        } else if (isItemIconImage(props.paymentResult)) {
            return ITEM_ICON_IMAGE;
        } else if (isCardIconImage(props.paymentResult)) {
            return CARD_ICON_IMAGE;
        } else if (isBoletoIconImage(props.paymentResult)) {
            return BOLETO_ICON_IMAGE;
        } else {
            return DEFAULT_ICON_IMAGE;
        }
    }

    private boolean isItemIconImage(@NonNull final PaymentResult paymentResult) {
        String status = paymentResult.getPaymentStatus();
        String statusDetail = paymentResult.getPaymentStatusDetail();
        return status.equals(Payment.StatusCodes.STATUS_APPROVED) ||
                status.equals(Payment.StatusCodes.STATUS_IN_PROCESS) ||
                (status.equals(Payment.StatusCodes.STATUS_PENDING) &&
                        statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_PENDING_WAITING_PAYMENT));
    }

    private boolean isCardIconImage(@NonNull final PaymentResult paymentResult) {
        if (isPaymentMethodIconImage(paymentResult)) {
            String paymentTypeId = paymentResult.getPaymentData().getPaymentMethod().getPaymentTypeId();
            return paymentTypeId.equals(PaymentTypes.PREPAID_CARD) || paymentTypeId.equals(PaymentTypes.DEBIT_CARD) ||
                    paymentTypeId.equals(PaymentTypes.CREDIT_CARD);
        }
        return false;
    }

    private boolean isBoletoIconImage(@NonNull final PaymentResult paymentResult) {
        if (isPaymentMethodIconImage(paymentResult)) {
            String paymentMethodId = paymentResult.getPaymentData().getPaymentMethod().getId();
            return paymentMethodId.equals(PaymentMethods.BRASIL.BOLBRADESCO);
        }
        return false;
    }

    private boolean isPaymentMethodIconImage(@NonNull final PaymentResult paymentResult) {
        String status = paymentResult.getPaymentStatus();
        String statusDetail = paymentResult.getPaymentStatusDetail();
        return ((status.equals(Payment.StatusCodes.STATUS_PENDING) && !statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_PENDING_WAITING_PAYMENT)) ||
                status.equals(Payment.StatusCodes.STATUS_IN_PROCESS) ||
                status.equals(Payment.StatusCodes.STATUS_REJECTED));
    }

    private int getBadgeImage(@NonNull final  PaymentResultProps props) {
        if (props.hasCustomizedBadge()) {
            String badge = props.getPreferenceBadge();
            if (badge.equals(Badge.CHECK_BADGE_IMAGE)) {
                return CHECK_BADGE_IMAGE;
            } else if (badge.equals(Badge.PENDING_BADGE_IMAGE)) {
                return PENDING_BADGE_IMAGE;
            } else {
                return DEFAULT_BADGE_IMAGE;
            }
        } else if (props.paymentResult == null) {
            return DEFAULT_BADGE_IMAGE;
        } else if (isCheckBagdeImage(props.paymentResult)) {
            return CHECK_BADGE_IMAGE;
        } else if (isPendingBadgeImage(props.paymentResult)) {
            return PENDING_BADGE_IMAGE;
        } else if (isWarningBadgeImage(props.paymentResult)) {
            return WARNING_BADGE_IMAGE;
        } else if (isErrorBadgeImage(props.paymentResult)) {
            return ERROR_BADGE_IMAGE;
        } else {
            return DEFAULT_BADGE_IMAGE;
        }
    }

    private boolean isCheckBagdeImage(@NonNull final PaymentResult paymentResult) {
        return paymentResult.getPaymentStatus().equals(Payment.StatusCodes.STATUS_APPROVED);
    }

    private boolean isPendingBadgeImage(@NonNull final PaymentResult paymentResult) {
        return paymentResult.getPaymentStatus().equals(Payment.StatusCodes.STATUS_PENDING) ||
                paymentResult.getPaymentStatus().equals(Payment.StatusCodes.STATUS_IN_PROCESS);
    }

    private boolean isWarningBadgeImage(@NonNull final PaymentResult paymentResult) {
        String status = paymentResult.getPaymentStatus();
        String statusDetail = paymentResult.getPaymentStatusDetail();
        return status.equals(Payment.StatusCodes.STATUS_REJECTED) && (statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_INVALID_ESC) ||
                statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_CALL_FOR_AUTHORIZE) ||
                statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_BAD_FILLED_CARD_NUMBER) ||
                statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_BAD_FILLED_OTHER) ||
                statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_BAD_FILLED_SECURITY_CODE) ||
                statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_BAD_FILLED_DATE) ||
                statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_CARD_DISABLED) ||
                statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_INSUFFICIENT_AMOUNT));
    }

    private boolean isErrorBadgeImage(@NonNull final PaymentResult paymentResult) {
        String status = paymentResult.getPaymentStatus();
        String statusDetail = paymentResult.getPaymentStatusDetail();
        return status.equals(Payment.StatusCodes.STATUS_REJECTED) && (statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_OTHER_REASON) ||
                statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_REJECTED_REJECTED_BY_BANK) ||
                statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_REJECTED_REJECTED_INSUFFICIENT_DATA) ||
                statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_DUPLICATED_PAYMENT) ||
                statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_MAX_ATTEMPTS) ||
                statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_REJECTED_HIGH_RISK));
    }

    private String getTitle(@NonNull final  PaymentResultProps props) {

        if (props.hasCustomizedTitle()) {
            return props.getPreferenceTitle();
        } else if (props.hasInstructions()) {
            return props.getInstructionsTitle();
        } else if (props.paymentResult == null) {
            return resourcesProvider.getEmptyText();
        } else if (isPaymentMethodOff(props.paymentResult)) {
            return resourcesProvider.getEmptyText();
        } else {
            String paymentMethodName = props.paymentResult.getPaymentData().getPaymentMethod().getName();
            String status = props.paymentResult.getPaymentStatus();
            String statusDetail = props.paymentResult.getPaymentStatusDetail();

            if (status.equals(Payment.StatusCodes.STATUS_APPROVED)) {
                return resourcesProvider.getApprovedTitle();
            } else if (status.equals(Payment.StatusCodes.STATUS_IN_PROCESS) || status.equals(Payment.StatusCodes.STATUS_PENDING)) {
                return resourcesProvider.getPendingTitle();
            } else if (status.equals(Payment.StatusCodes.STATUS_REJECTED)) {
                if (statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_OTHER_REASON)) {
                    return resourcesProvider.getRejectedOtherReasonTitle(paymentMethodName);
                } else if (statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_INSUFFICIENT_AMOUNT)) {
                    return resourcesProvider.getRejectedInsufficientAmountTitle(paymentMethodName);
                } else if (statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_DUPLICATED_PAYMENT)) {
                    return resourcesProvider.getRejectedDuplicatedPaymentTitle(paymentMethodName);
                } else if (statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_CARD_DISABLED)) {
                    return resourcesProvider.getRejectedCardDisabledTitle(paymentMethodName);
                } else if (statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_REJECTED_HIGH_RISK)) {
                    return resourcesProvider.getRejectedHighRiskTitle();
                } else if (statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_MAX_ATTEMPTS)) {
                    return resourcesProvider.getRejectedMaxAttemptsTitle();
                } else if (statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_BAD_FILLED_OTHER) ||
                        statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_BAD_FILLED_CARD_NUMBER) ||
                        statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_BAD_FILLED_SECURITY_CODE) ||
                        statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_BAD_FILLED_DATE)) {
                    return resourcesProvider.getRejectedBadFilledCardTitle(paymentMethodName);
                } else if (statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_REJECTED_REJECTED_BY_BANK)
                        || statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_REJECTED_REJECTED_INSUFFICIENT_DATA)) {
                    return resourcesProvider.getRejectedInsufficientDataTitle();
                } else if (statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_CC_REJECTED_CALL_FOR_AUTHORIZE)) {
                    return resourcesProvider.getRejectedCallForAuthorizeTitle();
                } else {
                    return resourcesProvider.getRejectedBadFilledOther();
                }
            }
        }
        return resourcesProvider.getEmptyText();
    }

    private String getLabel(@NonNull final  PaymentResultProps props) {
        if (props.hasCustomizedLabel()) {
            return props.getPreferenceLabel();
        } else if (props.paymentResult == null) {
            return resourcesProvider.getEmptyText();
        } else {
            if (isLabelEmpty(props.paymentResult)) {
                return resourcesProvider.getEmptyText();
            } else if (isLabelPending(props.paymentResult)) {
                return resourcesProvider.getPendingLabel();
            } else if (isLabelError(props.paymentResult)) {
                return resourcesProvider.getRejectionLabel();
            }
        }
        return resourcesProvider.getEmptyText();
    }

    private boolean isLabelEmpty(@NonNull final PaymentResult paymentResult) {
        String status = paymentResult.getPaymentStatus();
        String statusDetail = paymentResult.getPaymentStatusDetail();
        return status.equals(Payment.StatusCodes.STATUS_APPROVED) || status.equals(Payment.StatusCodes.STATUS_IN_PROCESS) ||
                (status.equals(Payment.StatusCodes.STATUS_PENDING) && !statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_PENDING_WAITING_PAYMENT));
    }

    private boolean isLabelPending(@NonNull final PaymentResult paymentResult) {
        String status = paymentResult.getPaymentStatus();
        String statusDetail = paymentResult.getPaymentStatusDetail();
        return status.equals(Payment.StatusCodes.STATUS_PENDING) && statusDetail.equals(Payment.StatusCodes.STATUS_DETAIL_PENDING_WAITING_PAYMENT);
    }

    private boolean isLabelError(@NonNull final PaymentResult paymentResult) {
        return paymentResult.getPaymentStatus().equals(Payment.StatusCodes.STATUS_REJECTED);
    }

    private boolean isPaymentMethodOff(@NonNull final PaymentResult paymentResult) {
        String paymentTypeId = paymentResult.getPaymentData().getPaymentMethod().getPaymentTypeId();
        return paymentTypeId.equals(PaymentTypes.TICKET) || paymentTypeId.equals(PaymentTypes.ATM) || paymentTypeId.equals(PaymentTypes.BANK_TRANSFER);
    }
}