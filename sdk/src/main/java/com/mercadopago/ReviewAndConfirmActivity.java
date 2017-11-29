package com.mercadopago;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.mercadopago.adapters.ReviewablesAdapter;
import com.mercadopago.constants.ReviewKeys;
import com.mercadopago.constants.Sites;
import com.mercadopago.controllers.CheckoutTimer;
import com.mercadopago.core.MercadoPagoCheckout;
import com.mercadopago.customviews.MPTextView;

import com.mercadopago.model.Discount;
import com.mercadopago.model.Issuer;
import com.mercadopago.model.Item;
import com.mercadopago.model.PayerCost;
import com.mercadopago.model.PaymentData;
import com.mercadopago.model.PaymentMethod;
import com.mercadopago.model.ReviewSubscriber;
import com.mercadopago.model.Reviewable;
import com.mercadopago.model.Site;
import com.mercadopago.model.Token;
import com.mercadopago.observers.TimerObserver;
import com.mercadopago.preferences.DecorationPreference;
import com.mercadopago.preferences.ReviewScreenPreference;
import com.mercadopago.presenters.ReviewAndConfirmPresenter;
import com.mercadopago.tracker.MPTrackingContext;
import com.mercadopago.providers.ReviewAndConfirmProviderImpl;
import com.mercadopago.tracking.model.ScreenViewEvent;
import com.mercadopago.tracking.utils.TrackingUtil;
import com.mercadopago.uicontrollers.FontCache;
import com.mercadopago.util.ErrorUtil;
import com.mercadopago.util.JsonUtil;
import com.mercadopago.views.ReviewAndConfirmView;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ReviewAndConfirmActivity extends MercadoPagoBaseActivity implements TimerObserver, ReviewAndConfirmView, ReviewSubscriber {

    public static final int RESULT_CHANGE_PAYMENT_METHOD = 3;
    public static final int RESULT_CANCEL_PAYMENT = 4;

    //Controls
    protected CollapsingToolbarLayout mCollapsingToolbar;
    protected Toolbar mToolbar;
    protected AppBarLayout mAppBar;

    protected MPTextView mConfirmTextButton;
    protected MPTextView mFloatingConfirmTextButton;
    protected MPTextView mCancelTextView;
    protected MPTextView mTermsAndConditionsTextView;
    protected MPTextView mTimerTextView;

    protected FrameLayout mConfirmButton;
    protected FrameLayout mFloatingConfirmButton;
    protected FrameLayout mFloatingConfirmView;
    protected FrameLayout mCancelButton;

    protected View mSeparatorView;

    protected NestedScrollView mScrollView;
    protected RecyclerView mReviewables;
    protected LinearLayout mTermsAndConditionsButton;

    protected ReviewAndConfirmPresenter mPresenter;
    protected DecorationPreference mDecorationPreference;
    protected ReviewScreenPreference mReviewScreenPreference;
    protected String mPublicKey;
    protected Site mSite;

    private Issuer mIssuer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getUIPreferences();
        createPresenter();
        getActivityParameters();
        mPresenter.attachView(this);
        mPresenter.attachResourcesProvider(new ReviewAndConfirmProviderImpl(this, mReviewScreenPreference));

        if (mDecorationPreference != null && mDecorationPreference.hasColors()) {
            setTheme(R.style.Theme_MercadoPagoTheme_NoActionBar);
        }

        setContentView(R.layout.mpsdk_activity_review_confirm);
        initializeControls();
        showTimer();
        setListeners();

        initializeReviewablesRecyclerView();

        mPresenter.initialize();
    }

    private void setListeners() {
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmPayment();
            }
        });
        mFloatingConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmPayment();
            }
        });
        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelPayment();
            }
        });
        mTermsAndConditionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTermsAndConditionsActivity();
            }
        });

        mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                checkFloatingButtonVisibility();
            }
        });

        ViewTreeObserver viewTreeObserver = mScrollView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                checkFloatingButtonVisibility();
            }
        });
    }

    private void createPresenter() {
        mPresenter = new ReviewAndConfirmPresenter();
    }

    private void getActivityParameters() {
        mPublicKey = getIntent().getStringExtra("merchantPublicKey");
        mSite = JsonUtil.getInstance().fromJson(getIntent().getStringExtra("site"), Site.class);
        mIssuer = JsonUtil.getInstance().fromJson(getIntent().getStringExtra("issuer"), Issuer.class);

        Boolean termsAndConditionsEnabled = getIntent().getBooleanExtra("termsAndConditionsEnabled", true);
        Boolean editionEnabled = getIntent().getBooleanExtra("editionEnabled", true);
        Boolean discountEnabled = getIntent().getBooleanExtra("discountEnabled", true);
        BigDecimal amount = new BigDecimal(getIntent().getStringExtra("amount"));
        Discount discount = JsonUtil.getInstance().fromJson(getIntent().getStringExtra("discount"), Discount.class);
        PayerCost payerCost = JsonUtil.getInstance().fromJson(getIntent().getStringExtra("payerCost"), PayerCost.class);
        Token token = JsonUtil.getInstance().fromJson(getIntent().getStringExtra("token"), Token.class);
        PaymentMethod paymentMethod = JsonUtil.getInstance().fromJson(getIntent().getStringExtra("paymentMethod"), PaymentMethod.class);
        String paymentMethodCommentInfo = getIntent().getStringExtra("paymentMethodCommentInfo");
        String paymentMethodDescriptionInfo = getIntent().getStringExtra("paymentMethodDescriptionInfo");
        List<Item> items;

        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Item>>() {
            }.getType();
            items = gson.fromJson(this.getIntent().getStringExtra("items"), listType);
        } catch (Exception ex) {
            items = null;
        }

        mPresenter.setItems(items);
        mPresenter.setAmount(amount);
        mPresenter.setSite(mSite);
        mPresenter.setIssuer(mIssuer);
        mPresenter.setDiscount(discount);
        mPresenter.setPayerCost(payerCost);
        mPresenter.setToken(token);
        mPresenter.setPaymentMethod(paymentMethod);
        mPresenter.setPaymentMethodCommentInfo(paymentMethodCommentInfo);
        mPresenter.setPaymentMethodDescriptionInfo(paymentMethodDescriptionInfo);
        mPresenter.setEditionEnabled(editionEnabled);
        mPresenter.setDecorationPreference(mDecorationPreference);
        mPresenter.setTermsAndConditionsEnabled(termsAndConditionsEnabled);
        mPresenter.setDiscountEnabled(discountEnabled);

        if (mReviewScreenPreference == null || !mReviewScreenPreference.hasReviewOrder()) {
            mPresenter.setReviewOrder(getDefaultOrder());
        } else {
            mPresenter.setReviewOrder(mReviewScreenPreference.getReviewOrder());
        }
    }

    private void initializeControls() {
        mScrollView = (NestedScrollView) findViewById(R.id.mpsdkReviewScrollView);
        mCollapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.mpsdkCollapsingToolbar);
        mAppBar = (AppBarLayout) findViewById(R.id.mpsdkCheckoutAppBar);
        mToolbar = (Toolbar) findViewById(R.id.mpsdkRegularToolbar);
        mFloatingConfirmButton = (FrameLayout) findViewById(R.id.mpsdkCheckoutFloatingConfirmButton);
        mFloatingConfirmTextButton = (MPTextView) findViewById(R.id.mpsdkFloatingConfirmText);
        mFloatingConfirmView = (FrameLayout) findViewById(R.id.mpsdkCheckoutFloatingConfirmView);
        mConfirmButton = (FrameLayout) findViewById(R.id.mpsdkCheckoutConfirmButton);
        mConfirmTextButton = (MPTextView) findViewById(R.id.mpsdkConfirmText);
        mCancelButton = (FrameLayout) findViewById(R.id.mpsdkReviewCancelButton);
        mCancelTextView = (MPTextView) findViewById(R.id.mpsdkCancelText);
        mTermsAndConditionsButton = (LinearLayout) findViewById(R.id.mpsdkCheckoutTermsAndConditions);
        mTermsAndConditionsTextView = (MPTextView) findViewById(R.id.mpsdkReviewTermsAndConditions);
        mTimerTextView = (MPTextView) findViewById(R.id.mpsdkTimerTextView);
        mReviewables = (RecyclerView) findViewById(R.id.mpsdkReviewablesRecyclerView);
        mSeparatorView = findViewById(R.id.mpsdkFirstSeparator);

        initializeToolbar();
        decorateButtons();
    }

    private void initializeReviewablesRecyclerView() {
        mReviewables = (RecyclerView) findViewById(R.id.mpsdkReviewablesRecyclerView);
        mReviewables.setNestedScrollingEnabled(false);
        mReviewables.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setTimerColor() {
        if (mTimerTextView != null) {
            mTimerTextView.setTextColor(mDecorationPreference.getBaseColor());
        }
    }

    private void initializeToolbar() {

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Drawable upArrow = mToolbar.getNavigationIcon();
        if (upArrow != null && getSupportActionBar() != null) {
            if (mDecorationPreference != null && mDecorationPreference.hasColors()) {
                setTimerColor();
                upArrow.setColorFilter(mDecorationPreference.getBaseColor(), PorterDuff.Mode.SRC_ATOP);
            } else {
                upArrow.setColorFilter(ContextCompat.getColor(this, R.color.mpsdk_background_blue), PorterDuff.Mode.SRC_ATOP);
            }
            getSupportActionBar().setHomeAsUpIndicator(upArrow);
        }

        if (FontCache.hasTypeface(FontCache.CUSTOM_REGULAR_FONT)) {
            mCollapsingToolbar.setCollapsedTitleTypeface(FontCache.getTypeface(FontCache.CUSTOM_REGULAR_FONT));
            mCollapsingToolbar.setExpandedTitleTypeface(FontCache.getTypeface(FontCache.CUSTOM_REGULAR_FONT));
        }

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void showTimer() {
        if (CheckoutTimer.getInstance().isTimerEnabled()) {
            CheckoutTimer.getInstance().addObserver(this);
            mTimerTextView.setVisibility(View.VISIBLE);
            mTimerTextView.setText(CheckoutTimer.getInstance().getCurrentTime());
        }
    }

    @Override
    public void trackScreen() {
        MPTrackingContext mpTrackingContext = new MPTrackingContext.Builder(this, mPublicKey)
                .setCheckoutVersion(BuildConfig.VERSION_NAME)
                .setTrackingStrategy(TrackingUtil.BATCH_STRATEGY)
                .build();

        PaymentData paymentData = mPresenter.getPaymentData();
        if (paymentData != null) {

            ScreenViewEvent.Builder builder = new ScreenViewEvent.Builder()
                    .setScreenId(TrackingUtil.SCREEN_ID_REVIEW_AND_CONFIRM)
                    .setScreenName(TrackingUtil.SCREEN_NAME_REVIEW_AND_CONFIRM)
                    .addMetaData(TrackingUtil.METADATA_SHIPPING_INFO, TrackingUtil.HAS_SHIPPING_DEFAULT_VALUE);

            if (paymentData.getPaymentMethod() != null) {
                if (paymentData.getPaymentMethod().getPaymentTypeId() != null) {
                    builder.addMetaData(TrackingUtil.METADATA_PAYMENT_TYPE_ID, paymentData.getPaymentMethod().getPaymentTypeId());
                }
                if (paymentData.getPaymentMethod().getId() != null) {
                    builder.addMetaData(TrackingUtil.METADATA_PAYMENT_METHOD_ID, paymentData.getPaymentMethod().getId());
                }
            }
            if (paymentData.getIssuer() != null && paymentData.getIssuer().getId() != null) {
                builder.addMetaData(TrackingUtil.METADATA_ISSUER_ID, String.valueOf(paymentData.getIssuer().getId()));
            }

            ScreenViewEvent event = builder.build();
            mpTrackingContext.trackEvent(event);
        }
    }

    private void startTermsAndConditionsActivity() {
        Intent termsAndConditionsIntent = new Intent(this, TermsAndConditionsActivity.class);
        termsAndConditionsIntent.putExtra("siteId", Sites.ARGENTINA.getId());
        termsAndConditionsIntent.putExtra("decorationPreference", JsonUtil.getInstance().toJson(mDecorationPreference));
        startActivity(termsAndConditionsIntent);
    }

    private void getUIPreferences() {
        if (getIntent().getStringExtra("decorationPreference") != null) {
            mDecorationPreference = JsonUtil.getInstance().fromJson(getIntent().getStringExtra("decorationPreference"), DecorationPreference.class);
        }
        mReviewScreenPreference = JsonUtil.getInstance().fromJson(getIntent().getStringExtra("reviewScreenPreference"), ReviewScreenPreference.class);

    }

    protected boolean isCustomColorSet() {
        return mDecorationPreference != null && mDecorationPreference.hasColors();
    }

    protected int getCustomBaseColor() {
        return mDecorationPreference.getBaseColor();
    }

    protected boolean isDarkFontEnabled() {
        return mDecorationPreference != null && mDecorationPreference.isDarkFontEnabled();
    }

    protected int getDarkFontColor() {
        return mDecorationPreference.getDarkFontColor(this);
    }

    protected void decorate(Button button) {
        if (isCustomColorSet()) {
            button.setBackgroundColor(getCustomBaseColor());
        }

        if (isDarkFontEnabled()) {
            button.setTextColor(getDarkFontColor());
        }
    }

    protected void decorate(Toolbar toolbar) {
        if (toolbar != null) {
            if (isCustomColorSet()) {
                toolbar.setBackgroundColor(getCustomBaseColor());
            }
            decorateUpArrow(toolbar);
        }
    }

    protected void decorateUpArrow(Toolbar toolbar) {
        if (isDarkFontEnabled()) {
            int darkFont = getDarkFontColor();
            Drawable upArrow = toolbar.getNavigationIcon();
            if (upArrow != null && getSupportActionBar() != null) {
                upArrow.setColorFilter(darkFont, PorterDuff.Mode.SRC_ATOP);
                getSupportActionBar().setHomeAsUpIndicator(upArrow);
            }
        }
    }

    private void decorateButtons() {
        if (mDecorationPreference != null && mDecorationPreference.hasColors()) {
            mConfirmButton.setBackgroundColor(mDecorationPreference.getBaseColor());
            mFloatingConfirmButton.setBackgroundColor(mDecorationPreference.getBaseColor());
            if (mDecorationPreference.isDarkFontEnabled()) {
                mConfirmTextButton.setTextColor(mDecorationPreference.getDarkFontColor(this));
                mFloatingConfirmTextButton.setTextColor(mDecorationPreference.getDarkFontColor(this));
            }
            mCancelTextView.setTextColor(mDecorationPreference.getBaseColor());
            mTermsAndConditionsTextView.setTextColor(mDecorationPreference.getBaseColor());
        }
    }

    @Override
    public void showTitle(String title) {
        mCollapsingToolbar.setTitle(title);
        if (mDecorationPreference != null && mDecorationPreference.hasColors()) {
            mCollapsingToolbar.setExpandedTitleColor(mDecorationPreference.getBaseColor());
            mCollapsingToolbar.setCollapsedTitleTextColor(mDecorationPreference.getBaseColor());
        } else {
            mCollapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this, R.color.mpsdk_background_blue));
            mCollapsingToolbar.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.mpsdk_background_blue));
        }
    }

    @Override
    public void showConfirmationMessage(String message) {
        mConfirmTextButton.setText(message);
    }

    @Override
    public void showCancelMessage(String message) {
        mCancelTextView.setText(message);
    }

    @Override
    public void showTermsAndConditions() {
        mTermsAndConditionsButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTimeChanged(String timeToShow) {
        mTimerTextView.setText(timeToShow);
    }

    @Override
    public void onFinish() {
        Intent intent = new Intent();
        setResult(MercadoPagoCheckout.TIMER_FINISHED_RESULT_CODE, intent);
        this.finish();
    }

    @Override
    public void showError(String message) {
        ErrorUtil.startErrorActivity(this, getString(R.string.mpsdk_standard_error_message), message, false, mPublicKey);
    }

    @Override
    public void showReviewables(List<Reviewable> reviewables) {
        ReviewablesAdapter reviewablesAdapter = new ReviewablesAdapter(reviewables);
        mReviewables.setAdapter(reviewablesAdapter);
    }

    @Override
    public void changePaymentMethod() {
        setResult(RESULT_CHANGE_PAYMENT_METHOD);
        finish();
    }

    @Override
    public void confirmPayment() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void cancelPayment() {
        setResult(RESULT_CANCEL_PAYMENT);
        finish();
    }

    public void cancelPayment(Integer resultCode, Bundle resultData, PaymentData paymentData) {
        Intent intent = new Intent();
        if (resultData != null) {
            intent.putExtras(resultData);
        }
        intent.putExtra("resultCode", resultCode);
        intent.putExtra("paymentData", JsonUtil.getInstance().toJson(paymentData));
        setResult(RESULT_CANCEL_PAYMENT, intent);
        finish();
    }

    public boolean isConfirmButtonVisible() {
        ViewGroup.MarginLayoutParams cancelButtonLayout = (ViewGroup.MarginLayoutParams) mCancelButton.getLayoutParams();
        float scrollContent = mScrollView.getChildAt(0).getHeight();
        float scrollViewHeight = mScrollView.getHeight();
        float cancelHeight = mCancelButton.getHeight();

        float finalSize = scrollContent - scrollViewHeight - cancelHeight - cancelButtonLayout.bottomMargin - mSeparatorView.getHeight() * 5;

        return mScrollView.getScrollY() > finalSize;
    }

    public void checkFloatingButtonVisibility() {
        if (isConfirmButtonVisible()) {
            mFloatingConfirmView.setVisibility(View.GONE);
        } else {
            mFloatingConfirmView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public ReviewSubscriber getReviewSubscriber() {
        return this;
    }

    private List<String> getDefaultOrder() {
        return new ArrayList<String>() {{
            add(ReviewKeys.SUMMARY);
            add(ReviewKeys.ITEMS);
            add(ReviewKeys.PAYMENT_METHODS);
            add(ReviewKeys.DEFAULT);
        }};
    }

    @Override
    public void changeRequired(Integer resultCode, Bundle resultData) {
        cancelPayment(resultCode, resultData, mPresenter.getPaymentData());
    }

}
