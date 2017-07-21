package com.mercadopago.securitycode;

import com.mercadopago.mocks.CardInfos;
import com.mercadopago.mocks.PaymentMethods;
import com.mercadopago.model.Card;
import com.mercadopago.model.CardInfo;
import com.mercadopago.model.PaymentMethod;
import com.mercadopago.model.SavedCardToken;
import com.mercadopago.model.SecurityCode;
import com.mercadopago.model.Token;
import com.mercadopago.mvp.OnResourcesRetrievedCallback;
import com.mercadopago.presenters.SecurityCodePresenter;
import com.mercadopago.providers.SecurityCodeProvider;
import com.mercadopago.util.TextUtil;
import com.mercadopago.utils.MVPStructure;
import com.mercadopago.views.SecurityCodeActivityView;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by marlanti on 7/18/17.
 */

public class SecurityCodePresenterTest {

    private static final String NO_PARAMETERS_SET = "no_parameters_set";
    private static final String ALL_PARAMETERS_SET = "all_parameters_set";
    private static final String PAYMENT_METHOD_NOT_SET = "payment_method_not_set";
    private static final String CARD_NOT_SET = "card_not_set";
    private static final String TOKEN_NOT_SET = "token_not_set";
    private static final String CARD_AND_TOKEN_NOT_SET = "card_and_token_not_set";
    private static final String FRONT_SECURITY_CODE = "front_security_code";
    private static final String BACK_SECURITY_CODE = "back_security_code";
    private static final String NO_CARD_CREATION_OPTION = "all_parameters_set";

    //If someone adds a new parameter and forgets to test it in Presenter.
    @Test
    public void showErrorWhenNoParametersSet() {
        MVPStructure<SecurityCodePresenter, SecurityCodeMockedProvider, SecurityCodeMockedView, SecurityCode> mvp = getMVPStructure(NO_PARAMETERS_SET, NO_CARD_CREATION_OPTION);

        SecurityCodeMockedProvider provider = mvp.getProvider();
        SecurityCodeMockedView view = mvp.getView();
        SecurityCodePresenter presenter = mvp.getPresenter();

        presenter.initialize();

        assertTrue(provider.standardErrorMessageGotten);
        assertTrue(isErrorShown(view));
        assertFalse(view.initializeDone);
    }

    @Test
    public void ifPaymentMethodNotSetShowError() {
        MVPStructure<SecurityCodePresenter, SecurityCodeMockedProvider, SecurityCodeMockedView, SecurityCode> mvp = getMVPStructure(PAYMENT_METHOD_NOT_SET, NO_CARD_CREATION_OPTION);

        SecurityCodeMockedProvider provider = mvp.getProvider();
        SecurityCodeMockedView view = mvp.getView();
        SecurityCodePresenter presenter = mvp.getPresenter();

        presenter.initialize();

        assertTrue(provider.standardErrorMessageGotten);
        assertTrue(isErrorShown(view));
        assertFalse(view.initializeDone);
    }

    @Test
    public void ifCardAndTokenNotSetShowError() {
        MVPStructure<SecurityCodePresenter, SecurityCodeMockedProvider, SecurityCodeMockedView, SecurityCode> mvp = getMVPStructure(CARD_AND_TOKEN_NOT_SET, NO_CARD_CREATION_OPTION);

        SecurityCodeMockedProvider provider = mvp.getProvider();
        SecurityCodeMockedView view = mvp.getView();
        SecurityCodePresenter presenter = mvp.getPresenter();

        presenter.initialize();

        assertTrue(provider.standardErrorMessageGotten);
        assertTrue(isErrorShown(view));
        assertFalse(view.initializeDone);
    }

    @Test
    public void ifCardAndTokenSetShowError() {
        MVPStructure<SecurityCodePresenter, SecurityCodeMockedProvider, SecurityCodeMockedView, SecurityCode> mvp = getMVPStructure(ALL_PARAMETERS_SET, NO_CARD_CREATION_OPTION);

        SecurityCodeMockedProvider provider = mvp.getProvider();
        SecurityCodeMockedView view = mvp.getView();
        SecurityCodePresenter presenter = mvp.getPresenter();

        presenter.initialize();

        assertTrue(provider.standardErrorMessageGotten);
        assertTrue(isErrorShown(view));
        assertFalse(view.initializeDone);
    }

    //Amex case
    @Test
    public void ifCardHasFrontSecurityCodeThenShowFrontCardView() {
        MVPStructure<SecurityCodePresenter, SecurityCodeMockedProvider, SecurityCodeMockedView, SecurityCode> mvp = getMVPStructure(ALL_PARAMETERS_SET, FRONT_SECURITY_CODE);

        SecurityCodeMockedProvider provider = mvp.getProvider();
        SecurityCodeMockedView view = mvp.getView();
        SecurityCodePresenter presenter = mvp.getPresenter();

        presenter.initializeSecurityCodeSettings();
        presenter.setSecurityCodeCardType();

        assertTrue(view.frontSecurityCodeShown);
        assertFalse(view.backSecurityCodeShown);
    }

    @Test
    public void ifCardHasBackSecurityCodeThenShowBackCardView() {
        MVPStructure<SecurityCodePresenter, SecurityCodeMockedProvider, SecurityCodeMockedView, SecurityCode> mvp = getMVPStructure(ALL_PARAMETERS_SET, BACK_SECURITY_CODE);

        SecurityCodeMockedProvider provider = mvp.getProvider();
        SecurityCodeMockedView view = mvp.getView();
        SecurityCodePresenter presenter = mvp.getPresenter();

        presenter.initializeSecurityCodeSettings();
        presenter.setSecurityCodeCardType();

        assertTrue(view.backSecurityCodeShown);
        assertFalse(view.frontSecurityCodeShown);
    }


    // TODO EN QUE CASO ME LLEGA EL TOKEN??
    //TODO Hacer Los tres casos (if - elseif - catch) incluye: validateSecurityCodeFromToken, validateSecurityCodeFromToken, cloneToken--usa-->putSecurityCode, createToken
    @Test
    public void ifValidateSecurityCodeInputWithValidTokenThenCloneToken(){
        //TODO ver que cloneToken llama a loadingView, y a putSecurityCode en success. hacer que el mockedProvider  le pueda setear a validateSecurityCodeFromToken que devuelve.
        /*public void validateSecurityCodeInput() {
            try {
                if (mToken != null && validateSecurityCodeFromToken()) {
                    cloneToken();
                } else if (mCard != null) {
                    SavedCardToken savedCardToken = new SavedCardToken(mCard.getId(), mSecurityCode);
                    getResourcesProvider().validateSecurityCodeFromToken(savedCardToken, mCard);
                    createToken(savedCardToken);
                }
            } catch (Exception e) {
                getView().setErrorView(e.getMessage());
            }
        }*/
    }

    @Test
    public void ifValidateSecurityCodeInputWithInvalidTokenThenSetErrorView(){
        //TODO  hacer que el mockedProvider  le pueda setear a validateSecurityCodeFromToken que devuelve.

//        assertTrue(view.showErrorView);
    }

    @Test
    public void ifValidateSecurityCodeInputWithCardThenCreateToken(){
        //TODO ver casos onSuccess y onFailure de CreateToken en dos tests distintos según lo que devuelva el mockedProvider.
    }

    @Test
    public void ifValidateSecurityCodeInputWithInvalirCardOrSecurityCodeThenSetErrorView(){
        //TODO hacer q el provider haga que validateSecurityCodeFromToken devuelva false.
    }


    public boolean isErrorShown(SecurityCodeMockedView view) {
        return !TextUtil.isEmpty(view.errorMessage) && !TextUtil.isEmpty(view.errorDetail);
    }

    public MVPStructure<SecurityCodePresenter, SecurityCodeMockedProvider, SecurityCodeMockedView, SecurityCode> getMVPStructure(String emptyParameter, String cardCreationOption) {
        MVPStructure<SecurityCodePresenter, SecurityCodeMockedProvider, SecurityCodeMockedView, SecurityCode> mvpStructure = new MVPStructure<>();

        //MVP Objets
        SecurityCodeMockedView view = new SecurityCodeMockedView();
        SecurityCodePresenter presenter = new SecurityCodePresenter();
        presenter.attachView(view);
        SecurityCodeMockedProvider provider = new SecurityCodeMockedProvider();
        presenter.attachResourcesProvider(provider);

        //SecurityCodePresenter Parameters
        Card card = new Card();
        PaymentMethod paymentMethod = new PaymentMethod();
        CardInfo cardInfo = null;

        //Presenter Initialization
        if (cardCreationOption.equals(FRONT_SECURITY_CODE)) {
            cardInfo = CardInfos.getFrontSecurityCodeCardInfoMLA();
            paymentMethod = PaymentMethods.getPaymentMethodOnWithFrontSecurityCode();

            presenter.setCardInfo(cardInfo);
            presenter.setPaymentMethod(paymentMethod);
        }

        if (cardCreationOption.equals(BACK_SECURITY_CODE)) {
            cardInfo = CardInfos.getBackSecurityCodeCardInfoMLA();
            paymentMethod = PaymentMethods.getPaymentMethodOn();
            presenter.setCardInfo(cardInfo);
            presenter.setPaymentMethod(paymentMethod);
        }

        if (!emptyParameter.equals(NO_PARAMETERS_SET)) {
            if (!emptyParameter.equals(PAYMENT_METHOD_NOT_SET)) {
                presenter.setPaymentMethod(paymentMethod);
            }
            if (!emptyParameter.equals(CARD_NOT_SET)) {
                presenter.setCard(card);
            }
            if (!emptyParameter.equals(TOKEN_NOT_SET)) {
                Token token = new Token();
                presenter.setToken(token);
            }
            if (!emptyParameter.equals(CARD_AND_TOKEN_NOT_SET)) {
                presenter.setCard(card);
                Token token = new Token();
                presenter.setToken(token);
            }
        }

        mvpStructure.setPresenter(presenter);
        mvpStructure.setProvider(provider);
        mvpStructure.setView(view);

        return mvpStructure;
    }


    private class SecurityCodeMockedProvider implements SecurityCodeProvider {

        private boolean standardErrorMessageGotten = false;

        public String getStandardErrorMessageGotten() {
            this.standardErrorMessageGotten = true;
            return "We are going to fix it. Try later.";
        }

        @Override
        public void cloneToken(String tokenId, OnResourcesRetrievedCallback<Token> onResourcesRetrievedCallback) {

        }

        @Override
        public void putSecurityCode(String securityCode, String tokenId, OnResourcesRetrievedCallback<Token> onResourcesRetrievedCallback) {

        }

        @Override
        public void createToken(SavedCardToken savedCardToken, OnResourcesRetrievedCallback<Token> onResourcesRetrievedCallback) {

        }

        @Override
        public void validateSecurityCodeFromToken(String mSecurityCode, PaymentMethod mPaymentMethod, String firstSixDigits) throws Exception {

        }

        @Override
        public void validateSecurityCodeFromToken(String mSecurityCode) {

        }

        @Override
        public void validateSecurityCodeFromToken(SavedCardToken savedCardToken, Card card) throws Exception {

        }

    }


    private class SecurityCodeMockedView implements SecurityCodeActivityView {

        private boolean screenTracked = false;
        private boolean loadingViewShown = false;
        private boolean finishWithResult = false;
        private boolean initializeDone = false;
        private boolean backSecurityCodeShown = false;
        private boolean frontSecurityCodeShown = false;
        private boolean showErrorView = false;
        private String errorDetail;
        private String errorMessage;
        private boolean timerShown = false;

        @Override
        public void initialize() {
            initializeDone = true;
        }


        @Override
        public void setSecurityCodeInputMaxLength(int length) {

        }

        @Override
        public void showError(String message, String errorDetail) {
            this.errorMessage = message;
            this.errorDetail = errorDetail;
        }

        @Override
        public void setErrorView(String message) {
            this.showErrorView = true;
        }

        @Override
        public void clearErrorView() {

        }

        @Override
        public void showLoadingView() {
            this.loadingViewShown = true;
        }

        @Override
        public void stopLoadingView() {
            this.loadingViewShown = false;
        }

        @Override
        public void showTimer() {
            this.timerShown = true;
        }

        @Override
        public void finishWithResult() {
            this.finishWithResult = true;
        }

        @Override
        public void trackScreen() {
            screenTracked = true;
        }

        @Override
        public void showBackSecurityCodeCardView() {
            backSecurityCodeShown = true;
            frontSecurityCodeShown = false;
        }

        @Override
        public void showFrontSecurityCodeCardView() {
            backSecurityCodeShown = false;
            frontSecurityCodeShown = true;
        }

    }
}
