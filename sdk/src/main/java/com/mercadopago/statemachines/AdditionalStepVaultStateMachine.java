package com.mercadopago.statemachines;

import com.mercadopago.presenters.AdditionalStepVaultPresenter;

/**
 * Created by marlanti on 3/27/17.
 */


public enum AdditionalStepVaultStateMachine {

    IDENTIFICATION {
        @Override
        public AdditionalStepVaultStateMachine onBackPressed(AdditionalStepVaultPresenter mPresenter) {
            mPresenter.finishWithCancel();
            return this;
        }

        @Override
        public AdditionalStepVaultStateMachine onNextStep(AdditionalStepVaultPresenter mPresenter) {
            if(mPresenter.isOnlyIdentificationAndFinancialStepRequired()){
                mPresenter.startFinancialInstitutionsStep();
                return FINANCIAL_INSTITUTIONS;
            }
            else if (mPresenter.isEntityTypeStepRequired()){
                mPresenter.startEntityTypeStep();
                return ENTITY_TYPES;
            }

            mPresenter.finishWithResult();
            return this;
        }

        @Override
        public AdditionalStepVaultStateMachine onInit(AdditionalStepVaultPresenter mPresenter) {
            mPresenter.startIdentificationStep();
            return this;
        }
    },
    ENTITY_TYPES {
        @Override
        public AdditionalStepVaultStateMachine onBackPressed(AdditionalStepVaultPresenter mPresenter) {
            mPresenter.startIdentificationStepAnimatedBack();
            return IDENTIFICATION;
        }

        @Override
        public AdditionalStepVaultStateMachine onNextStep(AdditionalStepVaultPresenter mPresenter) {
            if (mPresenter.isFinancialInstitutionsStepRequired()) {
                mPresenter.startFinancialInstitutionsStep();
                return FINANCIAL_INSTITUTIONS;

            }

            mPresenter.finishWithResult();
            return this;

        }

        @Override
        public AdditionalStepVaultStateMachine onInit(AdditionalStepVaultPresenter mPresenter) {
            mPresenter.startEntityTypeStep();
            return this;
        }

    },
    FINANCIAL_INSTITUTIONS {
        @Override
        public AdditionalStepVaultStateMachine onBackPressed(AdditionalStepVaultPresenter mPresenter) {

            if(mPresenter.isOnlyIdentificationAndFinancialStepRequired()){
                mPresenter.startIdentificationStepAnimatedBack();
                return IDENTIFICATION;
            }
            else if(mPresenter.isEntityTypeStepRequired()){
                mPresenter.startEntityTypeStepAnimatedBack();
                return ENTITY_TYPES;
            }

            mPresenter.finishWithCancel();
            return this;
        }

        @Override
        public AdditionalStepVaultStateMachine onNextStep(AdditionalStepVaultPresenter mPresenter) {
            mPresenter.finishWithResult();
            return this;
        }

        @Override
        public AdditionalStepVaultStateMachine onInit(AdditionalStepVaultPresenter mPresenter) {
            mPresenter.startFinancialInstitutionsStep();
            return this;
        }
    }, ERROR {
        @Override
        public AdditionalStepVaultStateMachine onBackPressed(AdditionalStepVaultPresenter mPresenter) {
            return onNextStep(mPresenter);
        }

        @Override
        public AdditionalStepVaultStateMachine onNextStep(AdditionalStepVaultPresenter mPresenter) {
            mPresenter.onError(mPresenter.getInvalidAdditionalStepErrorMessage());
            return ERROR;
        }

        @Override
        public AdditionalStepVaultStateMachine onInit(AdditionalStepVaultPresenter mPresenter) {
            return onNextStep(mPresenter);
        }
    };

    public abstract AdditionalStepVaultStateMachine onBackPressed(AdditionalStepVaultPresenter mPresenter);

    public abstract AdditionalStepVaultStateMachine onNextStep(AdditionalStepVaultPresenter mPresenter);

    public abstract AdditionalStepVaultStateMachine onInit(AdditionalStepVaultPresenter mPresenter);
}
