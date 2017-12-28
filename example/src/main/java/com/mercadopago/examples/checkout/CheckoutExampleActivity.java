package com.mercadopago.examples.checkout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.mercadopago.constants.Sites;
import com.mercadopago.core.MercadoPagoCheckout;
import com.mercadopago.examples.R;
import com.mercadopago.examples.utils.ColorPickerDialog;
import com.mercadopago.examples.utils.ExamplesUtils;
import com.mercadopago.exceptions.MercadoPagoError;
import com.mercadopago.model.Discount;
import com.mercadopago.model.Item;
import com.mercadopago.model.Payment;
import com.mercadopago.model.PaymentData;
import com.mercadopago.model.PaymentResult;
import com.mercadopago.preferences.CheckoutPreference;
import com.mercadopago.preferences.DecorationPreference;
import com.mercadopago.util.JsonUtil;
import com.mercadopago.util.LayoutUtil;

import java.math.BigDecimal;

public class CheckoutExampleActivity extends AppCompatActivity {

    private Activity mActivity;
    private ProgressBar mProgressBar;
    private View mRegularLayout;
    private String mPublicKey;

    private Integer mDefaultColor;
    private CheckBox mDarkFontEnabled;
    private ImageView mColorSample;
    private Integer mSelectedColor;
    private CheckBox mVisaExcluded;
    private CheckBox mCashExcluded;
    private TextView mJsonConfigButton;
    private String mCheckoutPreferenceId;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_example);
        mActivity = this;
        mPublicKey = ExamplesUtils.DUMMY_MERCHANT_PUBLIC_KEY;
        mCheckoutPreferenceId = ExamplesUtils.DUMMY_PREFERENCE_ID;
        mDefaultColor = ContextCompat.getColor(this, R.color.colorPrimary);

        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mRegularLayout = findViewById(R.id.regularLayout);
        mDarkFontEnabled = (CheckBox) findViewById(R.id.darkFontEnabled);
        mColorSample = (ImageView) findViewById(R.id.colorSample);
        mVisaExcluded = (CheckBox) findViewById(R.id.visaExcluded);
        mCashExcluded = (CheckBox) findViewById(R.id.cashExcluded);
        mJsonConfigButton = (TextView) findViewById(R.id.jsonConfigButton);
        mJsonConfigButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startJsonInput();
            }
        });

        mSelectedColor = ContextCompat.getColor(this, R.color.mpsdk_colorPrimary);
    }

    public void onContinueClicked(View view) {
        startMercadoPagoCheckout();
    }

    private void startMercadoPagoCheckout() {

        Discount discount = new Discount();
        discount.setCurrencyId("ARS");
        discount.setId(77L);
        discount.setCouponAmount(new BigDecimal(20));
        discount.setAmountOff(new BigDecimal(20));
        
        CheckoutPreference checkoutPreference = new CheckoutPreference.Builder()
                .setPayerAccessToken("TEST-2278197349568462-091320-a1b4f0d7c18f4655a0cf620c4bc2e693__LD_LA__-207100706")
                .enableAccountMoney()
                .setSite(Sites.ARGENTINA)
                .addItem(new Item("lalal", 1, new BigDecimal(1000)))
                .build();

        new MercadoPagoCheckout.Builder()
                .setActivity(this)
                .setPublicKey(mPublicKey)
                .setDiscount(discount)
                .setCheckoutPreference(checkoutPreference)
                .setDecorationPreference(getCurrentDecorationPreference())
                .startForPaymentData();
    }

    private void showCongrats(PaymentData paymentData) {
        CheckoutPreference checkoutPreference = new CheckoutPreference.Builder()
                .setPayerAccessToken("TEST-2278197349568462-091320-a1b4f0d7c18f4655a0cf620c4bc2e693__LD_LA__-207100706")
                .enableAccountMoney()
                .setSite(Sites.ARGENTINA)
                .addItem(new Item("lalal", 1, new BigDecimal(1000)))
                .build();

        Discount discount = new Discount();
        discount.setCurrencyId("ARS");
        discount.setId(77L);
        discount.setCouponAmount(new BigDecimal(20));
        discount.setAmountOff(new BigDecimal(20));

        paymentData.setDiscount(discount);

        PaymentResult paymentResult = new PaymentResult.Builder()
                .setPaymentData(paymentData)
                .setPaymentId(1234L)
                .setPaymentStatus(Payment.StatusCodes.STATUS_APPROVED)
                .setPaymentStatusDetail(Payment.StatusCodes.STATUS_DETAIL_ACCREDITED)
                .setPayerEmail("sarasa@gmail.com")
                .setStatementDescription("mercado pago")
                .build();

        new MercadoPagoCheckout.Builder()
                .setActivity(this)
                .setPublicKey(mPublicKey)
                .setPaymentResult(paymentResult)
                .setCheckoutPreference(checkoutPreference)
                .startForPaymentData();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        LayoutUtil.showRegularLayout(this);

        if (requestCode == MercadoPagoCheckout.CHECKOUT_REQUEST_CODE) {
            if (resultCode == MercadoPagoCheckout.PAYMENT_RESULT_CODE) {
                Payment payment = JsonUtil.getInstance().fromJson(data.getStringExtra("payment"), Payment.class);
                Toast.makeText(mActivity, "Pago con status: " + payment.getStatus(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == MercadoPagoCheckout.PAYMENT_DATA_RESULT_CODE) {
                PaymentData paymentData = JsonUtil.getInstance().fromJson(data.getStringExtra("paymentData"), PaymentData.class);
                showCongrats(paymentData);
            }
            else if (resultCode == RESULT_CANCELED) {
                if (data != null && data.getStringExtra("mercadoPagoError") != null) {
                    MercadoPagoError mercadoPagoError = JsonUtil.getInstance().fromJson(data.getStringExtra("mercadoPagoError"), MercadoPagoError.class);
                    Toast.makeText(mActivity, "Error: " + mercadoPagoError.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mActivity, "Cancel", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        showRegularLayout();
    }

    private void showRegularLayout() {
        mProgressBar.setVisibility(View.GONE);
        mRegularLayout.setVisibility(View.VISIBLE);
    }

    public void changeColor(final View view) {
        new ColorPickerDialog(this, mDefaultColor, new ColorPickerDialog.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {
                mDarkFontEnabled.setEnabled(true);
                mColorSample.setBackgroundColor(color);
                mSelectedColor = color;
            }
        }).show();
    }

    public void resetSelection(final View view) {
        mSelectedColor = null;
        mColorSample.setBackgroundColor(mDefaultColor);
        mDarkFontEnabled.setChecked(false);
        mDarkFontEnabled.setEnabled(false);
        mVisaExcluded.setChecked(false);
        mCashExcluded.setChecked(false);
    }

    private DecorationPreference getCurrentDecorationPreference() {
        DecorationPreference.Builder decorationPreferenceBuilder =
                new DecorationPreference.Builder();
        if (mSelectedColor != null) {
            decorationPreferenceBuilder.setBaseColor(mSelectedColor);

            if (mDarkFontEnabled.isChecked()) {
                decorationPreferenceBuilder.enableDarkFont();
            }
        }
        return decorationPreferenceBuilder.build();
    }

    private void startJsonInput() {
        Intent intent = new Intent(this, JsonSetupActivity.class);
        startActivityForResult(intent, MercadoPagoCheckout.CHECKOUT_REQUEST_CODE);
    }
}