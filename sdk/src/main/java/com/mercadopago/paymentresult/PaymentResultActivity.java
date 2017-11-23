package com.mercadopago.paymentresult;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mercadopago.components.Component;
import com.mercadopago.components.ComponentManager;
import com.mercadopago.components.LoadingComponent;
import com.mercadopago.components.LoadingRenderer;
import com.mercadopago.components.RendererFactory;
import com.mercadopago.core.MercadoPagoCheckout;
import com.mercadopago.core.MercadoPagoComponents;
import com.mercadopago.exceptions.MercadoPagoError;
import com.mercadopago.model.ApiException;
import com.mercadopago.model.PaymentResult;
import com.mercadopago.model.PaymentResultAction;
import com.mercadopago.model.Site;
import com.mercadopago.paymentresult.components.AccreditationComment;
import com.mercadopago.paymentresult.components.AccreditationTime;
import com.mercadopago.paymentresult.components.Footer;
import com.mercadopago.paymentresult.components.FooterContainer;
import com.mercadopago.paymentresult.components.FooterContainerRenderer;
import com.mercadopago.paymentresult.components.FooterRenderer;
import com.mercadopago.paymentresult.components.Header;
import com.mercadopago.paymentresult.components.Icon;
import com.mercadopago.paymentresult.components.InstructionReferenceComponent;
import com.mercadopago.paymentresult.components.InstructionsAction;
import com.mercadopago.paymentresult.components.InstructionsActions;
import com.mercadopago.paymentresult.components.Instructions;
import com.mercadopago.paymentresult.components.InstructionsContent;
import com.mercadopago.paymentresult.components.InstructionsInfo;
import com.mercadopago.paymentresult.components.InstructionsReferences;
import com.mercadopago.paymentresult.components.InstructionsSecondaryInfo;
import com.mercadopago.paymentresult.components.InstructionsSubtitle;
import com.mercadopago.paymentresult.components.InstructionsTertiaryInfo;
import com.mercadopago.paymentresult.components.Body;
import com.mercadopago.paymentresult.components.PaymentResultContainer;
import com.mercadopago.paymentresult.components.AccreditationCommentRenderer;
import com.mercadopago.paymentresult.components.AccreditationTimeRenderer;
import com.mercadopago.paymentresult.components.HeaderRenderer;
import com.mercadopago.paymentresult.components.IconRenderer;
import com.mercadopago.paymentresult.components.InstructionReferenceRenderer;
import com.mercadopago.paymentresult.components.InstructionsActionRenderer;
import com.mercadopago.paymentresult.components.InstructionsActionsRenderer;
import com.mercadopago.paymentresult.components.InstructionsContentRenderer;
import com.mercadopago.paymentresult.components.InstructionsInfoRenderer;
import com.mercadopago.paymentresult.components.InstructionsReferencesRenderer;
import com.mercadopago.paymentresult.components.InstructionsRenderer;
import com.mercadopago.paymentresult.components.InstructionsSecondaryInfoRenderer;
import com.mercadopago.paymentresult.components.InstructionsSubtitleRenderer;
import com.mercadopago.paymentresult.components.InstructionsTertiaryInfoRenderer;
import com.mercadopago.paymentresult.components.PaymentResultBodyRenderer;
import com.mercadopago.paymentresult.components.PaymentResultRenderer;
import com.mercadopago.preferences.PaymentResultScreenPreference;
import com.mercadopago.preferences.ServicePreference;
import com.mercadopago.util.ApiUtil;
import com.mercadopago.util.ErrorUtil;
import com.mercadopago.util.JsonUtil;

import java.math.BigDecimal;

public class PaymentResultActivity extends AppCompatActivity implements PaymentResultNavigator {

    public static final String PAYER_ACCESS_TOKEN_BUNDLE = "merchantPublicKey";
    public static final String MERCHANT_PUBLIC_KEY_BUNDLE = "payerAccessToken";

    public static final String CONGRATS_DISPLAY_BUNDLE = "congratsDisplay";
    public static final String PAYMENT_RESULT_SCREEN_PREFERENCE_BUNDLE = "paymentResultScreenPreference";
    public static final String SERVICE_PREFERENCE_BUNDLE = "servicePreference";

    public static final String PRESENTER_BUNDLE = "presenter";

    private static final String EXTRA_NEXT_ACTION = "nextAction";

    private PaymentResultPresenter presenter;

    private String merchantPublicKey;
    private String payerAccessToken;
    private Integer congratsDisplay;
    private PaymentResultScreenPreference paymentResultScreenPreference;
    private ServicePreference servicePreference;

    private ComponentManager componentManager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final PaymentResultPropsMutator mutator = new PaymentResultPropsMutator();

        presenter = new PaymentResultPresenter(this);
        getActivityParameters();

        final PaymentResultProvider provider = new PaymentResultProviderImpl(this, merchantPublicKey, payerAccessToken);
        presenter.attachView(mutator);
        presenter.attachResourcesProvider(provider);

        componentManager = new ComponentManager(this);

        RendererFactory.register(PaymentResultContainer.class, PaymentResultRenderer.class);
        RendererFactory.register(Header.class, HeaderRenderer.class);
        RendererFactory.register(Body.class, PaymentResultBodyRenderer.class);
        RendererFactory.register(FooterContainer.class, FooterContainerRenderer.class);
        RendererFactory.register(Footer.class, FooterRenderer.class);
        RendererFactory.register(Icon.class, IconRenderer.class);
        RendererFactory.register(LoadingComponent.class, LoadingRenderer.class);
        RendererFactory.register(Instructions.class, InstructionsRenderer.class);
        RendererFactory.register(InstructionsSubtitle.class, InstructionsSubtitleRenderer.class);
        RendererFactory.register(InstructionsContent.class, InstructionsContentRenderer.class);
        RendererFactory.register(InstructionsInfo.class, InstructionsInfoRenderer.class);
        RendererFactory.register(InstructionsReferences.class, InstructionsReferencesRenderer.class);
        RendererFactory.register(InstructionReferenceComponent.class, InstructionReferenceRenderer.class);
        RendererFactory.register(AccreditationTime.class, AccreditationTimeRenderer.class);
        RendererFactory.register(AccreditationComment.class, AccreditationCommentRenderer.class);
        RendererFactory.register(InstructionsSecondaryInfo.class, InstructionsSecondaryInfoRenderer.class);
        RendererFactory.register(InstructionsTertiaryInfo.class, InstructionsTertiaryInfoRenderer.class);
        RendererFactory.register(InstructionsActions.class, InstructionsActionsRenderer.class);
        RendererFactory.register(InstructionsAction.class, InstructionsActionRenderer.class);

        final Component root = new PaymentResultContainer(componentManager, provider);
        componentManager.setActionsListener(presenter);
        componentManager.setComponent(root);

        mutator.setPropsListener(componentManager);
        mutator.renderDefaultProps();
        presenter.initialize();
    }

    @Override
    public void showApiExceptionError(final ApiException exception, final String requestOrigin) {
        ApiUtil.showApiExceptionError(this, exception, merchantPublicKey, requestOrigin);
    }

    @Override
    public void showError(final MercadoPagoError error, final String requestOrigin) {
        if (error != null && error.isApiException()) {
            showApiExceptionError(error.getApiException(), requestOrigin);
        } else {
            ErrorUtil.startErrorActivity(this, error, merchantPublicKey);
        }
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        //TODO fix: explota al abrir el link de las instrucciones y al salir de la activity
        outState.putString(PRESENTER_BUNDLE, JsonUtil.getInstance().toJson(presenter));

        outState.putString(MERCHANT_PUBLIC_KEY_BUNDLE, merchantPublicKey);
        outState.putString(PAYER_ACCESS_TOKEN_BUNDLE, payerAccessToken);

        outState.putInt(CONGRATS_DISPLAY_BUNDLE, congratsDisplay);
        outState.putString(PAYMENT_RESULT_SCREEN_PREFERENCE_BUNDLE, JsonUtil.getInstance().toJson(paymentResultScreenPreference));
    }

    @Override
    protected void onRestoreInstanceState(final Bundle savedInstanceState) {
        presenter = JsonUtil.getInstance().fromJson(savedInstanceState.getString(PRESENTER_BUNDLE), PaymentResultPresenter.class);

        merchantPublicKey = savedInstanceState.getString(MERCHANT_PUBLIC_KEY_BUNDLE);
        payerAccessToken = savedInstanceState.getString(PAYER_ACCESS_TOKEN_BUNDLE);

        congratsDisplay = savedInstanceState.getInt(CONGRATS_DISPLAY_BUNDLE, -1);

        paymentResultScreenPreference = JsonUtil.getInstance().fromJson(savedInstanceState.getString(PAYMENT_RESULT_SCREEN_PREFERENCE_BUNDLE), PaymentResultScreenPreference.class);
        super.onRestoreInstanceState(savedInstanceState);
    }

    protected void getActivityParameters() {

        Boolean discountEnabled = getIntent().getExtras().getBoolean("discountEnabled", true);
        Site site = JsonUtil.getInstance().fromJson(getIntent().getExtras().getString("site"), Site.class);
        BigDecimal amount = null;
        if (getIntent().getStringExtra("amount") != null) {
            amount = new BigDecimal(getIntent().getStringExtra("amount"));
        }
        PaymentResult paymentResult = JsonUtil.getInstance().fromJson(getIntent().getExtras().getString("paymentResult"), PaymentResult.class);

        presenter.setDiscountEnabled(discountEnabled);
        presenter.setSite(site);
        presenter.setAmount(amount);
        presenter.setPaymentResult(paymentResult);

        merchantPublicKey = getIntent().getStringExtra("merchantPublicKey");
        payerAccessToken = getIntent().getStringExtra("payerAccessToken");
        congratsDisplay = getIntent().getIntExtra("congratsDisplay", -1);
        servicePreference = JsonUtil.getInstance().fromJson(getIntent().getExtras().getString("servicePreference"), ServicePreference.class);
        paymentResultScreenPreference = JsonUtil.getInstance().fromJson(getIntent().getExtras().getString("paymentResultScreenPreference"), PaymentResultScreenPreference.class);

        presenter.setServicePreference(servicePreference);
        presenter.setPaymentResultScreenPreference(paymentResultScreenPreference);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {

        if (resultCode == MercadoPagoCheckout.TIMER_FINISHED_RESULT_CODE) {
            resolveTimerObserverResult(resultCode);
        } else if (requestCode == MercadoPagoComponents.Activities.CONGRATS_REQUEST_CODE) {
            finishWithOkResult(resultCode, data);
        } else if (requestCode == MercadoPagoComponents.Activities.PENDING_REQUEST_CODE) {
            resolveRequest(resultCode, data);
        } else if (requestCode == MercadoPagoComponents.Activities.REJECTION_REQUEST_CODE) {
            resolveRequest(resultCode, data);
        } else if (requestCode == MercadoPagoComponents.Activities.CALL_FOR_AUTHORIZE_REQUEST_CODE) {
            resolveRequest(resultCode, data);
        } else if (requestCode == MercadoPagoComponents.Activities.INSTRUCTIONS_REQUEST_CODE) {
            finishWithOkResult(resultCode, data);
        } else {
            finishWithCancelResult(data);
        }
    }

    private void resolveTimerObserverResult(final int resultCode) {
        setResult(resultCode);
        finish();
    }

    private void resolveRequest(final int resultCode, final Intent data) {
        if (resultCode == RESULT_CANCELED && data != null) {
            finishWithCancelResult(data);
        } else {
            finishWithOkResult(resultCode, data);
        }
    }

    private void finishWithCancelResult(final Intent data) {
        setResult(RESULT_CANCELED, data);
        finish();
    }

    private void finishWithOkResult(final int resultCode, final Intent data) {
        setResult(resultCode, data);
        finish();
    }

    @Override
    public void openLink(String url) {
        //TODO agregar try catch
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    public void finishWithResult(int resultCode) {
        final Intent intent = new Intent();
        intent.putExtra("resultCode", resultCode);
        setResult(resultCode, intent);
        finish();
    }

    @Override
    public void changePaymentMethod() {
        final Intent returnIntent = new Intent();
        final String action = PaymentResultAction.SELECT_OTHER_PAYMENT_METHOD;
        returnIntent.putExtra(EXTRA_NEXT_ACTION, action);
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }

    @Override
    public void recoverPayment() {
        final Intent returnIntent = new Intent();
        final String action = PaymentResultAction.RECOVER_PAYMENT;
        returnIntent.putExtra(EXTRA_NEXT_ACTION, action);
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }
}
