package com.mercadopago;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.mercadopago.adapters.FinancialInstitutionsAdapter;
import com.mercadopago.callbacks.OnSelectedCallback;
import com.mercadopago.controllers.CheckoutTimer;
import com.mercadopago.customviews.MPTextView;
import com.mercadopago.listeners.RecyclerItemClickListener;
import com.mercadopago.model.ApiException;
import com.mercadopago.model.FinancialInstitution;
import com.mercadopago.model.PaymentMethod;
import com.mercadopago.mptracker.MPTracker;
import com.mercadopago.observers.TimerObserver;
import com.mercadopago.preferences.DecorationPreference;
import com.mercadopago.presenters.FinancialInstitutionsPresenter;
import com.mercadopago.providers.FinancialInstitutionsProviderImpl;
import com.mercadopago.uicontrollers.FontCache;
import com.mercadopago.util.ApiUtil;
import com.mercadopago.util.ColorsUtil;
import com.mercadopago.util.ErrorUtil;
import com.mercadopago.util.JsonUtil;
import com.mercadopago.util.LayoutUtil;
import com.mercadopago.views.FinancialInstitutionsActivityView;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by marlanti on 3/13/17.
 */

public class FinancialInstitutionsActivity extends MercadoPagoBaseActivity implements FinancialInstitutionsActivityView, TimerObserver {

    private static final String DECORATION_PREFERENCE_BUNDLE = "mDecorationPreference";
    private static final String PRESENTER_BUNDLE = "mFinancialInstitutionsPresenter";

    protected FinancialInstitutionsPresenter mFinancialInstitutionsPresenter;
    protected Activity mActivity;

    //View controls
    protected FinancialInstitutionsAdapter mFinancialInstitutionsAdapter;
    protected RecyclerView mFinancialInstitutionsRecyclerView;
    protected DecorationPreference mDecorationPreference;
    //Low Res View
    protected Toolbar mLowResToolbar;
    protected MPTextView mLowResTitleToolbar;
    protected MPTextView mTimerTextView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;

        if (savedInstanceState == null) {
            createPresenter();
            configurePresenter();
            getActivityParameters();
            setTheme();
            setContentView();
            mFinancialInstitutionsPresenter.initialize();
        }
    }

    private void createPresenter() {
        if (mFinancialInstitutionsPresenter == null) {
            mFinancialInstitutionsPresenter = new FinancialInstitutionsPresenter();
        }
    }

    private void configurePresenter() {
        if(mFinancialInstitutionsPresenter != null){
            mFinancialInstitutionsPresenter.attachView(this);
            FinancialInstitutionsProviderImpl resourcesProvider = new FinancialInstitutionsProviderImpl(this);
            mFinancialInstitutionsPresenter.attachResourcesProvider(resourcesProvider);
        }
    }

    private boolean isCustomColorSet() {
        return mDecorationPreference != null && mDecorationPreference.hasColors();
    }

    private void getActivityParameters() {

        String publicKey = getIntent().getStringExtra("merchantPublicKey");
        mDecorationPreference = JsonUtil.getInstance().fromJson(getIntent().getStringExtra("decorationPreference"), DecorationPreference.class);

        PaymentMethod paymentMethod = JsonUtil.getInstance().fromJson(
                this.getIntent().getStringExtra("paymentMethod"), PaymentMethod.class);

        mFinancialInstitutionsPresenter.setPaymentMethod(paymentMethod);
        mFinancialInstitutionsPresenter.setPublicKey(publicKey);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        outState.putString(DECORATION_PREFERENCE_BUNDLE, JsonUtil.getInstance().toJson(mDecorationPreference));
        outState.putString(PRESENTER_BUNDLE, JsonUtil.getInstance().toJson(mFinancialInstitutionsPresenter));
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if (savedInstanceState != null) {
            mFinancialInstitutionsPresenter = JsonUtil.getInstance().fromJson(savedInstanceState.getString(PRESENTER_BUNDLE), FinancialInstitutionsPresenter.class);
            mDecorationPreference = JsonUtil.getInstance().fromJson(savedInstanceState.getString(DECORATION_PREFERENCE_BUNDLE), DecorationPreference.class);
            setTheme();
            setContentView();
            mFinancialInstitutionsPresenter.initialize();
        }
    }


    private void setTheme() {
        if (isCustomColorSet()) {
            setTheme(R.style.Theme_MercadoPagoTheme_NoActionBar);
        }
    }


    public void setContentView() {
        setContentViewLowRes();
    }

    @Override
    public void initialize() {
        initializeControls();
        loadViews();
        hideHeader();
        decorate();
        showTimer();
    }

    @Override
    public void showFinancialInstitutions(List<FinancialInstitution> financialInstitutionList, OnSelectedCallback<Integer> onSelectedCallback) {
        initializeAdapter(onSelectedCallback);
        mFinancialInstitutionsAdapter.addResults(financialInstitutionList);
    }

    @Override
    public void showTimer() {
        if (CheckoutTimer.getInstance().isTimerEnabled()) {
            CheckoutTimer.getInstance().addObserver(this);
            mTimerTextView.setVisibility(View.VISIBLE);
            mTimerTextView.setText(CheckoutTimer.getInstance().getCurrentTime());
        }
    }

    @Override
    public void trackScreen(){
        MPTracker.getInstance().trackScreen("FINANCIAL_INSTITUTIONS", "2", mFinancialInstitutionsPresenter.getPublicKey(),
                BuildConfig.VERSION_NAME, this);
    }


    private void setContentViewLowRes() {
        setContentView(R.layout.mpsdk_activity_financial_institutions_lowres);
    }


    private void initializeControls() {
        mFinancialInstitutionsRecyclerView = (RecyclerView) findViewById(R.id.mpsdkActivityFinancialInstitutionsView);
        mTimerTextView = (MPTextView) findViewById(R.id.mpsdkTimerTextView);

        mLowResToolbar = (Toolbar) findViewById(R.id.mpsdkRegularToolbar);

        mLowResToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mLowResTitleToolbar = (MPTextView) findViewById(R.id.mpsdkTitle);

        if (CheckoutTimer.getInstance().isTimerEnabled()) {
            Toolbar.LayoutParams marginParams = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            marginParams.setMargins(0, 0, 0, 0);
            mLowResTitleToolbar.setLayoutParams(marginParams);
            mLowResTitleToolbar.setTextSize(17);
            mTimerTextView.setTextSize(15);
        }

        mLowResToolbar.setVisibility(View.VISIBLE);

    }

    private void loadViews() {
        loadLowResViews();
    }

    private void initializeAdapter(OnSelectedCallback<Integer> onSelectedCallback) {
        mFinancialInstitutionsAdapter = new FinancialInstitutionsAdapter(this, onSelectedCallback);
        initializeAdapterListener(mFinancialInstitutionsAdapter, mFinancialInstitutionsRecyclerView);
    }


    private void initializeAdapterListener(RecyclerView.Adapter adapter, RecyclerView view) {
        view.setAdapter(adapter);
        view.setLayoutManager(new LinearLayoutManager(this));
        view.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        mFinancialInstitutionsPresenter.onItemSelected(position);
                    }
                }));
    }

    @Override
    public void showError(String message, String errorDetail) {
        ErrorUtil.startErrorActivity(mActivity, message, errorDetail, false);
    }

    private void loadLowResViews() {
        loadToolbarArrow(mLowResToolbar);
    }


    private void loadToolbarArrow(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        if (toolbar != null) {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                    finish();
                }
            });
        }
    }


    private void decorate() {
        if (isDecorationEnabled()) {
            decorateLowRes();
        }
    }

    private boolean isDecorationEnabled() {
        return mDecorationPreference != null && mDecorationPreference.hasColors();
    }

    private void decorateLowRes() {
        ColorsUtil.decorateLowResToolbar(mLowResToolbar, mLowResTitleToolbar, mDecorationPreference,
                getSupportActionBar(), this);
        if (mTimerTextView != null) {
            ColorsUtil.decorateTextView(mDecorationPreference, mTimerTextView, this);
        }
    }

    @Override
    public void showHeader(String title) {
        mLowResToolbar.setVisibility(View.VISIBLE);
        mLowResTitleToolbar.setText(title);
        if (FontCache.hasTypeface(FontCache.CUSTOM_REGULAR_FONT)) {
            mLowResTitleToolbar.setTypeface(FontCache.getTypeface(FontCache.CUSTOM_REGULAR_FONT));
        }
    }

    private void hideHeader() {
        mLowResToolbar.setVisibility(View.GONE);
    }

    @Override
    public void showLoadingView() {
        mFinancialInstitutionsRecyclerView.setVisibility(View.GONE);
        LayoutUtil.showProgressLayout(this);
    }

    @Override
    public void stopLoadingView() {
        mFinancialInstitutionsRecyclerView.setVisibility(View.VISIBLE);
        LayoutUtil.showRegularLayout(this);
    }

    @Override
    public void finishWithResult(FinancialInstitution financialInstitution) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("financialInstitution", JsonUtil.getInstance().toJson(financialInstitution));
        setResult(RESULT_OK, returnIntent);
        finish();
        overridePendingTransition(R.anim.mpsdk_hold, R.anim.mpsdk_hold);
    }

    @Override
    public void onBackPressed() {
        MPTracker.getInstance().trackEvent("FINANCIAL_INSTITUTIONS", "BACK_PRESSED", "2", mFinancialInstitutionsPresenter.getPublicKey(),
                BuildConfig.VERSION_NAME, this);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("backButtonPressed", true);
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ErrorUtil.ERROR_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                mFinancialInstitutionsPresenter.recoverFromFailure();
            } else {
                setResult(resultCode, data);
                finish();
            }
        }
    }

    @Override
    public void onTimeChanged(String timeToShow) {
        mTimerTextView.setText(timeToShow);
    }

    @Override
    public void onFinish() {
        this.finish();
    }
}
