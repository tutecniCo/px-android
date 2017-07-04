package com.mercadopago.entitytypes;

import com.mercadopago.callbacks.OnSelectedCallback;
import com.mercadopago.controllers.CheckoutTimer;
import com.mercadopago.mocks.EntityTypes;
import com.mercadopago.model.EntityType;
import com.mercadopago.model.Identification;
import com.mercadopago.model.IdentificationType;
import com.mercadopago.model.PaymentMethod;
import com.mercadopago.model.Site;
import com.mercadopago.presenters.EntityTypePresenter;
import com.mercadopago.providers.EntityTypeProvider;
import com.mercadopago.util.TextUtil;
import com.mercadopago.utils.MVPStructure;
import com.mercadopago.views.EntityTypeActivityView;

import org.junit.Test;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by marlanti on 7/5/17.
 */

public class EntityTypePresenterTest {

    private static final String EMPTY_ENTITY_TYPES_LIST = "empty_entity_types_list";
    private static final String ONE_ENTITY_TYPE = "one_entity_type";
    private static final String ALL_ENTITY_TYPES_LIST = "all_entity_types_list";
    private static final String NO_ENTITY_TYPES = "no_entity_types";
    private static final String ALL_PARAMETERS_SET = "all_parameters_set";
    private static final String NO_PARAMETERS_SET = "no_parameters_set";
    private static final String PAYMENT_METHOD_NOT_SET = "payment_method_not_set";
    private static final String SITE_NOT_SET = "site_not_set";
    private static final String IDENTIFICATION_NOT_SET = "identification_not_set";
    private static final String IDENTIFICATION_TYPE_NOT_SET = "identification_type_not_set";
    private static final String PUBLIC_KEY_NOT_SET = "public_key_not_set";

    //If someone adds a new parameter and forgets to test it.
    @Test
    public void showErrorWhenInvalidParameters() {
        MVPStructure<EntityTypePresenter, EntityTypeMockedProvider, EntityTypeMockedView, EntityType> mvp = getMVPStructure(NO_PARAMETERS_SET, ALL_ENTITY_TYPES_LIST);

        EntityTypeMockedProvider provider = mvp.getProvider();
        EntityTypeMockedView view = mvp.getView();
        EntityTypePresenter presenter = mvp.getPresenter();

        presenter.initialize();

        assertTrue(provider.standardErrorMessageGotten);
        assertTrue(isErrorShown(view));
        assertFalse(view.initializeDone);
    }

    @Test
    public void ifPaymentMethodNotSetShowError() {
        MVPStructure<EntityTypePresenter, EntityTypeMockedProvider, EntityTypeMockedView, EntityType> mvp = getMVPStructure(PAYMENT_METHOD_NOT_SET, ALL_ENTITY_TYPES_LIST);

        EntityTypeMockedProvider provider = mvp.getProvider();
        EntityTypeMockedView view = mvp.getView();
        EntityTypePresenter presenter = mvp.getPresenter();

        presenter.initialize();

        assertTrue(provider.standardErrorMessageGotten);
        assertTrue(isErrorShown(view));
        assertFalse(view.initializeDone);
    }

    @Test
    public void ifIdentificationTypeNotSetShowError() {
        MVPStructure<EntityTypePresenter, EntityTypeMockedProvider, EntityTypeMockedView, EntityType> mvp = getMVPStructure(IDENTIFICATION_TYPE_NOT_SET, ALL_ENTITY_TYPES_LIST);

        EntityTypeMockedProvider provider = mvp.getProvider();
        EntityTypeMockedView view = mvp.getView();
        EntityTypePresenter presenter = mvp.getPresenter();

        presenter.initialize();

        assertTrue(provider.standardErrorMessageGotten);
        assertTrue(isErrorShown(view));
        assertFalse(view.initializeDone);
    }

    @Test
    public void ifIdentificationNotSetShowError() {
        MVPStructure<EntityTypePresenter, EntityTypeMockedProvider, EntityTypeMockedView, EntityType> mvp = getMVPStructure(IDENTIFICATION_NOT_SET, ALL_ENTITY_TYPES_LIST);

        EntityTypeMockedProvider provider = mvp.getProvider();
        EntityTypeMockedView view = mvp.getView();
        EntityTypePresenter presenter = mvp.getPresenter();

        presenter.initialize();

        assertTrue(provider.standardErrorMessageGotten);
        assertTrue(isErrorShown(view));
        assertFalse(view.initializeDone);
    }

    @Test
    public void ifSiteNotSetShowError() {
        MVPStructure<EntityTypePresenter, EntityTypeMockedProvider, EntityTypeMockedView, EntityType> mvp = getMVPStructure(SITE_NOT_SET, ALL_ENTITY_TYPES_LIST);

        EntityTypeMockedProvider provider = mvp.getProvider();
        EntityTypeMockedView view = mvp.getView();
        EntityTypePresenter presenter = mvp.getPresenter();

        presenter.initialize();

        assertTrue(provider.standardErrorMessageGotten);
        assertTrue(isErrorShown(view));
        assertFalse(view.initializeDone);
    }

    @Test
    public void ifPublicKeyNotSetShowError() {
        MVPStructure<EntityTypePresenter, EntityTypeMockedProvider, EntityTypeMockedView, EntityType> mvp = getMVPStructure(PUBLIC_KEY_NOT_SET, ALL_ENTITY_TYPES_LIST);

        EntityTypeMockedProvider provider = mvp.getProvider();
        EntityTypeMockedView view = mvp.getView();
        EntityTypePresenter presenter = mvp.getPresenter();

        presenter.initialize();

        assertTrue(provider.standardErrorMessageGotten);
        assertTrue(isErrorShown(view));
        assertFalse(view.initializeDone);
    }

    @Test
    public void ifGetEntityTypesReturnsNullThenShowError() {
        MVPStructure<EntityTypePresenter, EntityTypeMockedProvider, EntityTypeMockedView, EntityType> mvp = getMVPStructure(ALL_PARAMETERS_SET, NO_ENTITY_TYPES);

        EntityTypeMockedProvider provider = mvp.getProvider();
        EntityTypeMockedView view = mvp.getView();
        EntityTypePresenter presenter = mvp.getPresenter();

        presenter.initialize();

        assertTrue(provider.standardErrorMessageGotten);
        assertTrue(provider.emptyEntityTypesErrorGotten);
        assertTrue(isErrorShown(view));
        assertFalse(view.initializeDone);
        assertFalse(view.entityTypesShown);
    }

    @Test
    public void ifGetEntityTypesReturnsAnEmptyListThenShowError(){
        MVPStructure<EntityTypePresenter, EntityTypeMockedProvider, EntityTypeMockedView, EntityType> mvp = getMVPStructure(ALL_PARAMETERS_SET, EMPTY_ENTITY_TYPES_LIST);

        EntityTypeMockedProvider provider = mvp.getProvider();
        EntityTypeMockedView view = mvp.getView();
        EntityTypePresenter presenter = mvp.getPresenter();

        presenter.initialize();

        assertTrue(provider.standardErrorMessageGotten);
        assertTrue(provider.emptyEntityTypesErrorGotten);
        assertTrue(isErrorShown(view));
        assertFalse(view.initializeDone);
        assertFalse(view.entityTypesShown);
    }

    @Test
    public void ifEntityTypesAreNullThenGetEntityTypesAndShow() {

        MVPStructure<EntityTypePresenter, EntityTypeMockedProvider, EntityTypeMockedView, EntityType> mvp = getMVPStructure(ALL_PARAMETERS_SET, ALL_ENTITY_TYPES_LIST);

        EntityTypeMockedProvider provider = mvp.getProvider();
        EntityTypeMockedView view = mvp.getView();
        EntityTypePresenter presenter = mvp.getPresenter();

        presenter.initialize();

        assertFalse(provider.standardErrorMessageGotten);
        assertFalse(provider.emptyEntityTypesErrorGotten);
        assertFalse(isErrorShown(view));
        assertTrue(view.initializeDone);
        assertTrue(view.entityTypesShown);
        assertTrue(view.headerShown);
        assertTrue(view.screenTracked);
        assertTrue(view.timerShown);

    }

    @Test
    public void ifEntityTypeSelectedThenFinishWithResult() {

        MVPStructure<EntityTypePresenter, EntityTypeMockedProvider, EntityTypeMockedView, EntityType> mvp = getMVPStructure(ALL_PARAMETERS_SET, ALL_ENTITY_TYPES_LIST);

        EntityTypeMockedProvider provider = mvp.getProvider();
        EntityTypeMockedView view = mvp.getView();
        EntityTypePresenter presenter = mvp.getPresenter();
        List<EntityType> entityTypes = mvp.getObjects();

        presenter.initialize();

        view.simulateEntityTypeSelection(0);

        assertTrue(view.entityTypesShown);
        assertTrue(view.headerShown);
        assertEquals(entityTypes.get(0), view.selectedEntityType);
        assertTrue(view.finishWithResult);
        assertFalse(isErrorShown(view));
        assertFalse(provider.emptyEntityTypesErrorGotten);
    }

    @Test
    public void ifGetEntityTypesHaveOneEntityTypeThenFinishWithResult(){
        MVPStructure<EntityTypePresenter, EntityTypeMockedProvider, EntityTypeMockedView, EntityType> mvp = getMVPStructure(ALL_PARAMETERS_SET, ONE_ENTITY_TYPE);

        EntityTypeMockedProvider provider = mvp.getProvider();
        EntityTypeMockedView view = mvp.getView();
        EntityTypePresenter presenter = mvp.getPresenter();

        presenter.initialize();

        assertFalse(provider.standardErrorMessageGotten);
        assertFalse(provider.emptyEntityTypesErrorGotten);
        assertFalse(isErrorShown(view));
        assertFalse(view.initializeDone);
        assertFalse(view.entityTypesShown);
        assertFalse(view.headerShown);
        assertFalse(view.screenTracked);
        assertFalse(view.timerShown);

        assertTrue(view.finishWithResult);
    }


    public boolean isErrorShown(EntityTypeMockedView view) {
        return !TextUtil.isEmpty(view.errorMessage) && !TextUtil.isEmpty(view.errorDetail);
    }

    public MVPStructure getMVPStructure(String emptyParameter, String entityTypesParameter) {
        MVPStructure mvpStructure = new MVPStructure<EntityTypePresenter, EntityTypeMockedProvider, EntityTypeMockedView, EntityType>();

        EntityTypeMockedView view = new EntityTypeMockedView();
        EntityTypeMockedProvider provider = new EntityTypeMockedProvider();

        EntityTypePresenter presenter = new EntityTypePresenter();
        presenter.attachView(view);
        presenter.attachResourcesProvider(provider);

        Site site = new Site("MCO", "COP");

        if (emptyParameter != NO_PARAMETERS_SET) {
            if (emptyParameter != PAYMENT_METHOD_NOT_SET) {
                PaymentMethod paymentMethod = new PaymentMethod();
                presenter.setPaymentMethod(paymentMethod);
            }
            if (emptyParameter != IDENTIFICATION_TYPE_NOT_SET) {
                IdentificationType identificationType = new IdentificationType();
                presenter.setIdentificationType(identificationType);
            }
            if (emptyParameter != IDENTIFICATION_NOT_SET) {
                Identification identification = new Identification();
                presenter.setIdentification(identification);
            }
            if (emptyParameter != SITE_NOT_SET) {
                presenter.setSite(site);
            }
            if (emptyParameter != PUBLIC_KEY_NOT_SET) {
                String publicKey = "publickey";
                presenter.setPublicKey(publicKey);
            }
        }

        if (entityTypesParameter == ALL_ENTITY_TYPES_LIST) {
            List<EntityType> entityTypes = EntityTypes.getEntityTypesBySite(site);
            mvpStructure.setObjects(entityTypes);
            provider.setResponse(entityTypes);
        }

        if(entityTypesParameter == ONE_ENTITY_TYPE){
            List<EntityType> entityTypes = new ArrayList<>();
            entityTypes.add(new EntityType("individual","Natural"));
            mvpStructure.setObjects(entityTypes);
            provider.setResponse(entityTypes);
        }

        if (entityTypesParameter == EMPTY_ENTITY_TYPES_LIST) {
            List<EntityType> entityTypes = new ArrayList<>();
            mvpStructure.setObjects(entityTypes);
            provider.setResponse(entityTypes);
        }

        mvpStructure.setPresenter(presenter);
        mvpStructure.setProvider(provider);
        mvpStructure.setView(view);

        return mvpStructure;
    }

    private class EntityTypeMockedProvider implements EntityTypeProvider {

        private boolean emptyEntityTypesErrorGotten = false;
        private List<EntityType> successfulResponse;
        private boolean shouldFail;
        private boolean standardErrorMessageGotten = false;


        @Override
        public List<EntityType> getEntityTypesBySite(Site site) {

            List<EntityType> list = null;

            if (shouldFail) {
                list = null;
            } else {
                list = successfulResponse;
            }

            return list;
        }

        public String getStandardErrorMessageGotten() {
            this.standardErrorMessageGotten = true;
            return "We are going to fix it. Try later.";
        }

        @Override
        public String getEmptyEntityTypesErrorMessage() {
            this.emptyEntityTypesErrorGotten = true;
            return "No entityTypes found at EntityTypesActivity";
        }

        @Override
        public String getEntityTypesTitle() {
            return null;
        }

        public void setResponse(List<EntityType> response) {
            if (response == null || response.isEmpty()) {
                this.shouldFail = true;
            } else {
                this.successfulResponse = response;
                this.shouldFail = false;
            }

        }
    }


    private class EntityTypeMockedView implements EntityTypeActivityView {

        private boolean screenTracked = false;
        private boolean entityTypesShown = false;
        private boolean headerShown = false;
        private boolean loadingViewShown = false;
        private boolean finishWithResult = false;
        private EntityType selectedEntityType;
        private OnSelectedCallback<Integer> entityTypeSelectionCallback;
        private boolean initializeDone = false;
        private String errorDetail;
        private String errorMessage;
        private boolean timerShown = false;

        @Override
        public void initialize() {
            initializeDone = true;
        }

        @Override
        public void showEntityTypes(List<EntityType> entityTypesList, OnSelectedCallback<Integer> onSelectedCallback) {
            this.entityTypeSelectionCallback = onSelectedCallback;
            this.entityTypesShown = true;
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
        public void showTimer() {
            this.timerShown = true;
        }

        @Override
        public void finishWithResult(EntityType entityType) {
            this.finishWithResult = true;
            this.selectedEntityType = entityType;
        }

        @Override
        public void trackScreen() {
            screenTracked = true;
        }

        private void simulateEntityTypeSelection(int index) {
            entityTypeSelectionCallback.onSelected(index);
        }
    }
}
