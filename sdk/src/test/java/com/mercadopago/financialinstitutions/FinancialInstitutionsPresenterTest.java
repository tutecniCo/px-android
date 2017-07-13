package com.mercadopago.financialinstitutions;

import com.mercadopago.callbacks.OnSelectedCallback;
import com.mercadopago.model.EntityType;
import com.mercadopago.model.FinancialInstitution;
import com.mercadopago.model.PaymentMethod;
import com.mercadopago.model.Site;
import com.mercadopago.presenters.FinancialInstitutionsPresenter;
import com.mercadopago.providers.FinancialInstitutionsProvider;
import com.mercadopago.util.TextUtil;
import com.mercadopago.utils.MVPStructure;
import com.mercadopago.views.FinancialInstitutionsActivityView;

import org.junit.Test;

import java.util.List;

/**
 * Created by marlanti on 7/11/17.
 */

public class FinancialInstitutionsPresenterTest {

    private static final String EMPTY_FINANCIAL_INSTITUTIONS_LIST = "empty_financial_institutions_list";
    private static final String ONE_FINANCIAL_INSTITUTION = "one_financial_institution";
    private static final String ALL_FINANCIAL_INSTITUTIONS_LIST = "all_financial_institutions_list";
    private static final String NO_FINANCIAL_INSTITUTIONS = "no_financial_institutions";
    private static final String ALL_PARAMETERS_SET = "all_parameters_set";
    private static final String NO_PARAMETERS_SET = "no_parameters_set";
    private static final String PAYMENT_METHOD_NOT_SET = "payment_method_not_set";
    private static final String SITE_NOT_SET = "site_not_set";
    private static final String IDENTIFICATION_NOT_SET = "identification_not_set";
    private static final String IDENTIFICATION_TYPE_NOT_SET = "identification_type_not_set";
    private static final String PUBLIC_KEY_NOT_SET = "public_key_not_set";

   /* //If someone adds a new parameter and forgets to test it.
    @Test
    public void showErrorWhenInvalidParameters() {
        MVPStructure<FinancialInstitutionsPresenter, FinancialInstitutionsMockedProvider, FinancialInstitutionsMockedView, FinancialInstitution> mvp = getMVPStructure(NO_PARAMETERS_SET, ALL_FINANCIAL_INSTITUTIONS_LIST);

        FinancialInstitutionsMockedProvider provider = mvp.getProvider();
        FinancialInstitutionsMockedView view = mvp.getView();
        EntityTypePresenter presenter = mvp.getPresenter();

        presenter.initialize();

        assertTrue(provider.standardErrorMessageGotten);
        assertTrue(isErrorShown(view));
        assertFalse(view.initializeDone);
    }

    @Test
    public void ifPaymentMethodNotSetShowError() {
        MVPStructure<EntityTypePresenter, FinancialInstitutionsMockedProvider, FinancialInstitutionsMockedView, EntityType> mvp = getMVPStructure(PAYMENT_METHOD_NOT_SET, ALL_FINANCIAL_INSTITUTIONS_LIST);

        FinancialInstitutionsMockedProvider provider = mvp.getProvider();
        FinancialInstitutionsMockedView view = mvp.getView();
        EntityTypePresenter presenter = mvp.getPresenter();

        presenter.initialize();

        assertTrue(provider.standardErrorMessageGotten);
        assertTrue(isErrorShown(view));
        assertFalse(view.initializeDone);
    }

    @Test
    public void ifIdentificationTypeNotSetShowError() {
        MVPStructure<EntityTypePresenter, FinancialInstitutionsMockedProvider, FinancialInstitutionsMockedView, EntityType> mvp = getMVPStructure(IDENTIFICATION_TYPE_NOT_SET, ALL_FINANCIAL_INSTITUTIONS_LIST);

        FinancialInstitutionsMockedProvider provider = mvp.getProvider();
        FinancialInstitutionsMockedView view = mvp.getView();
        EntityTypePresenter presenter = mvp.getPresenter();

        presenter.initialize();

        assertTrue(provider.standardErrorMessageGotten);
        assertTrue(isErrorShown(view));
        assertFalse(view.initializeDone);
    }

    @Test
    public void ifIdentificationNotSetShowError() {
        MVPStructure<EntityTypePresenter, FinancialInstitutionsMockedProvider, FinancialInstitutionsMockedView, EntityType> mvp = getMVPStructure(IDENTIFICATION_NOT_SET, ALL_FINANCIAL_INSTITUTIONS_LIST);

        FinancialInstitutionsMockedProvider provider = mvp.getProvider();
        FinancialInstitutionsMockedView view = mvp.getView();
        EntityTypePresenter presenter = mvp.getPresenter();

        presenter.initialize();

        assertTrue(provider.standardErrorMessageGotten);
        assertTrue(isErrorShown(view));
        assertFalse(view.initializeDone);
    }

    @Test
    public void ifSiteNotSetShowError() {
        MVPStructure<EntityTypePresenter, FinancialInstitutionsMockedProvider, FinancialInstitutionsMockedView, EntityType> mvp = getMVPStructure(SITE_NOT_SET, ALL_FINANCIAL_INSTITUTIONS_LIST);

        FinancialInstitutionsMockedProvider provider = mvp.getProvider();
        FinancialInstitutionsMockedView view = mvp.getView();
        EntityTypePresenter presenter = mvp.getPresenter();

        presenter.initialize();

        assertTrue(provider.standardErrorMessageGotten);
        assertTrue(isErrorShown(view));
        assertFalse(view.initializeDone);
    }

    @Test
    public void ifPublicKeyNotSetShowError() {
        MVPStructure<EntityTypePresenter, FinancialInstitutionsMockedProvider, FinancialInstitutionsMockedView, EntityType> mvp = getMVPStructure(PUBLIC_KEY_NOT_SET, ALL_FINANCIAL_INSTITUTIONS_LIST);

        FinancialInstitutionsMockedProvider provider = mvp.getProvider();
        FinancialInstitutionsMockedView view = mvp.getView();
        EntityTypePresenter presenter = mvp.getPresenter();

        presenter.initialize();

        assertTrue(provider.standardErrorMessageGotten);
        assertTrue(isErrorShown(view));
        assertFalse(view.initializeDone);
    }

    @Test
    public void ifGetEntityTypesReturnsNullThenShowError() {
        MVPStructure<EntityTypePresenter, FinancialInstitutionsMockedProvider, FinancialInstitutionsMockedView, EntityType> mvp = getMVPStructure(ALL_PARAMETERS_SET, NO_FINANCIAL_INSTITUTIONS);

        FinancialInstitutionsMockedProvider provider = mvp.getProvider();
        FinancialInstitutionsMockedView view = mvp.getView();
        EntityTypePresenter presenter = mvp.getPresenter();

        presenter.initialize();

        assertTrue(provider.standardErrorMessageGotten);
        assertTrue(provider.emptyFinancialInstitutionsErrorGotten);
        assertTrue(isErrorShown(view));
        assertFalse(view.initializeDone);
        assertFalse(view.financialInstitutionsShown);
    }

    @Test
    public void ifGetEntityTypesReturnsAnEmptyListThenShowError(){
        MVPStructure<EntityTypePresenter, FinancialInstitutionsMockedProvider, FinancialInstitutionsMockedView, EntityType> mvp = getMVPStructure(ALL_PARAMETERS_SET, EMPTY_FINANCIAL_INSTITUTIONS_LIST);

        FinancialInstitutionsMockedProvider provider = mvp.getProvider();
        FinancialInstitutionsMockedView view = mvp.getView();
        EntityTypePresenter presenter = mvp.getPresenter();

        presenter.initialize();

        assertTrue(provider.standardErrorMessageGotten);
        assertTrue(provider.emptyFinancialInstitutionsErrorGotten);
        assertTrue(isErrorShown(view));
        assertFalse(view.initializeDone);
        assertFalse(view.financialInstitutionsShown);
    }

    @Test
    public void ifEntityTypesAreNullThenGetEntityTypesAndShow() {

        MVPStructure<EntityTypePresenter, FinancialInstitutionsMockedProvider, FinancialInstitutionsMockedView, EntityType> mvp = getMVPStructure(ALL_PARAMETERS_SET, ALL_FINANCIAL_INSTITUTIONS_LIST);

        FinancialInstitutionsMockedProvider provider = mvp.getProvider();
        FinancialInstitutionsMockedView view = mvp.getView();
        EntityTypePresenter presenter = mvp.getPresenter();

        presenter.initialize();

        assertFalse(provider.standardErrorMessageGotten);
        assertFalse(provider.emptyFinancialInstitutionsErrorGotten);
        assertFalse(isErrorShown(view));
        assertTrue(view.initializeDone);
        assertTrue(view.financialInstitutionsShown);
        assertTrue(view.headerShown);
        assertTrue(view.screenTracked);
        assertTrue(view.timerShown);

    }

    @Test
    public void ifEntityTypeSelectedThenFinishWithResult() {

        MVPStructure<EntityTypePresenter, FinancialInstitutionsMockedProvider, FinancialInstitutionsMockedView, EntityType> mvp = getMVPStructure(ALL_PARAMETERS_SET, ALL_FINANCIAL_INSTITUTIONS_LIST);

        FinancialInstitutionsMockedProvider provider = mvp.getProvider();
        FinancialInstitutionsMockedView view = mvp.getView();
        EntityTypePresenter presenter = mvp.getPresenter();
        List<EntityType> entityTypes = mvp.getObjects();

        presenter.initialize();

        view.simulateFinancialInstitutionsSelection(0);

        assertTrue(view.financialInstitutionsShown);
        assertTrue(view.headerShown);
        assertEquals(entityTypes.get(0), view.selectedFinancialInstitution);
        assertTrue(view.finishWithResult);
        assertFalse(isErrorShown(view));
        assertFalse(provider.emptyFinancialInstitutionsErrorGotten);
    }

    @Test
    public void ifGetEntityTypesHaveOneEntityTypeThenFinishWithResult(){
        MVPStructure<EntityTypePresenter, FinancialInstitutionsMockedProvider, FinancialInstitutionsMockedView, EntityType> mvp = getMVPStructure(ALL_PARAMETERS_SET, ONE_FINANCIAL_INSTITUTION);

        FinancialInstitutionsMockedProvider provider = mvp.getProvider();
        FinancialInstitutionsMockedView view = mvp.getView();
        EntityTypePresenter presenter = mvp.getPresenter();

        presenter.initialize();

        assertFalse(provider.standardErrorMessageGotten);
        assertFalse(provider.emptyFinancialInstitutionsErrorGotten);
        assertFalse(isErrorShown(view));
        assertFalse(view.initializeDone);
        assertFalse(view.financialInstitutionsShown);
        assertFalse(view.headerShown);
        assertFalse(view.screenTracked);
        assertFalse(view.timerShown);

        assertTrue(view.finishWithResult);
    }


    public boolean isErrorShown(FinancialInstitutionsMockedView view) {
        return !TextUtil.isEmpty(view.errorMessage) && !TextUtil.isEmpty(view.errorDetail);
    }

    public MVPStructure getMVPStructure(String emptyParameter, String financialInstitutionsParameter) {
        MVPStructure mvpStructure = new MVPStructure<FinancialInstitutionsPresenter, FinancialInstitutionsMockedProvider, FinancialInstitutionsMockedView, EntityType>();

        FinancialInstitutionsMockedView view = new FinancialInstitutionsMockedView();
        FinancialInstitutionsMockedProvider provider = new FinancialInstitutionsMockedProvider();

        FinancialInstitutionsPresenter presenter = new FinancialInstitutionsPresenter();
        presenter.attachView(view);
        presenter.attachResourcesProvider(provider);

        if (emptyParameter != NO_PARAMETERS_SET) {
            if (emptyParameter != PAYMENT_METHOD_NOT_SET) {
                PaymentMethod paymentMethod = new PaymentMethod();
                presenter.setPaymentMethod(paymentMethod);
            }
            if (emptyParameter != PUBLIC_KEY_NOT_SET) {
                String publicKey = "publickey";
                presenter.setPublicKey(publicKey);
            }
        }

        if (financialInstitutionsParameter == ALL_FINANCIAL_INSTITUTIONS_LIST) {
            List<EntityType> entityTypes = FinancialInstitutions.getFinancialInstitutions();
            mvpStructure.setObjects(entityTypes);
            provider.setResponse(entityTypes);
        }

        if(financialInstitutionsParameter == ONE_FINANCIAL_INSTITUTION){
            List<EntityType> entityTypes = new ArrayList<>();
            entityTypes.add(new EntityType("individual","Natural"));
            mvpStructure.setObjects(entityTypes);
            provider.setResponse(entityTypes);
        }

        if (financialInstitutionsParameter == EMPTY_FINANCIAL_INSTITUTIONS_LIST) {
            List<EntityType> entityTypes = new ArrayList<>();
            mvpStructure.setObjects(entityTypes);
            provider.setResponse(entityTypes);
        }

        mvpStructure.setPresenter(presenter);
        mvpStructure.setProvider(provider);
        mvpStructure.setView(view);

        return mvpStructure;
    }
*/
    private class FinancialInstitutionsMockedProvider implements FinancialInstitutionsProvider {

        private boolean emptyFinancialInstitutionsErrorGotten = false;
        private List<FinancialInstitution> successfulResponse;
        private boolean shouldFail;
        private boolean standardErrorMessageGotten = false;


        @Override
        public List<FinancialInstitution> getFinancialInstitutions(PaymentMethod paymentMethod) {
            List<FinancialInstitution> list = null;

            if (shouldFail) {
                list = null;
            } else {
                list = successfulResponse;
            }

            return list;
        }

        @Override
        public String getStandardErrorMessageGotten() {
            this.standardErrorMessageGotten = true;
            return "We are going to fix it. Try later.";
        }

        @Override
        public String getFinancialInstitutionsTitle() {
            return null;
        }


        @Override
        public String getEmptyFinancialInstitutionsErrorMessage() {
            this.emptyFinancialInstitutionsErrorGotten = true;
            return "No Financial Institutions found at EntityTypesPresenter";
        }


        public void setResponse(List<FinancialInstitution> response) {
            if (response == null || response.isEmpty()) {
                this.shouldFail = true;
            } else {
                this.successfulResponse = response;
                this.shouldFail = false;
            }

        }
    }


    private class FinancialInstitutionsMockedView implements FinancialInstitutionsActivityView {

        private boolean screenTracked = false;
        private boolean financialInstitutionsShown = false;
        private boolean headerShown = false;
        private boolean loadingViewShown = false;
        private boolean finishWithResult = false;
        private FinancialInstitution selectedFinancialInstitution;
        private OnSelectedCallback<Integer> financialInstitutionsSelectionCallback;
        private boolean initializeDone = false;
        private String errorDetail;
        private String errorMessage;
        private boolean timerShown = false;

        @Override
        public void initialize() {
            initializeDone = true;
        }

        @Override
        public void showFinancialInstitutions(List<FinancialInstitution> financialInstitutionList, OnSelectedCallback<Integer> onSelectedCallback) {
            this.financialInstitutionsSelectionCallback = onSelectedCallback;
            this.financialInstitutionsShown = true;
        }

        @Override
        public void showError(String message, String errorDetail) {
            this.errorMessage = message;
            this.errorDetail = errorDetail;
        }

        @Override
        public void showHeader(String title) {
            this.headerShown = true;
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
        public void finishWithResult(FinancialInstitution financialInstitution) {
            this.finishWithResult = true;
            this.selectedFinancialInstitution = financialInstitution;
        }

        @Override
        public void showTimer() {
            this.timerShown = true;
        }


        @Override
        public void trackScreen() {
            screenTracked = true;
        }

        private void simulateFinancialInstitutionsSelection(int index) {
            financialInstitutionsSelectionCallback.onSelected(index);
        }
    }
}
