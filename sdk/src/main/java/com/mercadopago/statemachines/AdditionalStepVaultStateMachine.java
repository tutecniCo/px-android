package com.mercadopago.statemachines;

import com.mercadopago.views.AdditionalStepVaultActivityView;

/**
 * Created by marlanti on 3/27/17.
 */


public enum AdditionalStepVaultStateMachine {

    IDENTIFICATION {
        @Override
        public AdditionalStepVaultStateMachine onBackPressed(AdditionalStepVaultActivityView mView) {
            mView.finishWithCancel();
            return this;
        }

        @Override
        public AdditionalStepVaultStateMachine onNextStep(AdditionalStepVaultActivityView mView) {
            mView.startEntityTypeStep();
            return ENTITY_TYPES;
        }

        @Override
        public AdditionalStepVaultStateMachine onInit(AdditionalStepVaultActivityView mView) {
            mView.startIdentificationStep();
            return this;
        }
    },
    ENTITY_TYPES {
        @Override
        public AdditionalStepVaultStateMachine onBackPressed(AdditionalStepVaultActivityView mView) {
            mView.startIdentificationStep();
            return IDENTIFICATION;
        }

        @Override
        public AdditionalStepVaultStateMachine onNextStep(AdditionalStepVaultActivityView mView) {
            mView.startFinancialInstitutionsStep();
            return FINANCIAL_INSTITUTIONS;
        }

        @Override
        public AdditionalStepVaultStateMachine onInit(AdditionalStepVaultActivityView mView) {
            mView.startEntityTypeStep();
            return this;
        }

    },
    FINANCIAL_INSTITUTIONS {
        @Override
        public AdditionalStepVaultStateMachine onBackPressed(AdditionalStepVaultActivityView mView) {
            mView.startEntityTypeStep();
            return ENTITY_TYPES;
        }

        @Override
        public AdditionalStepVaultStateMachine onNextStep(AdditionalStepVaultActivityView mView) {
            return this;
        }

        @Override
        public AdditionalStepVaultStateMachine onInit(AdditionalStepVaultActivityView mView) {
            mView.startFinancialInstitutionsStep();
            return this;
        }
    };

    public abstract AdditionalStepVaultStateMachine onBackPressed(AdditionalStepVaultActivityView mView);

    public abstract AdditionalStepVaultStateMachine onNextStep(AdditionalStepVaultActivityView mView);

    public abstract AdditionalStepVaultStateMachine onInit(AdditionalStepVaultActivityView mView);
}
