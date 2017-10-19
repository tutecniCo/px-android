package com.mercadopago.paymentresult;

import android.support.annotation.NonNull;


public class PaymentResultProps {

    public final int count;
    public final String title;
    public final String subtitle;

    public PaymentResultProps(int count, String title, String subtitle) {
        this.count = count;
        this.title = title;
        this.subtitle = subtitle;
    }

    public PaymentResultProps(@NonNull final Builder builder) {
        this.count = builder.count;
        this.title = builder.title;
        this.subtitle = builder.subtitle;
    }

    public Builder toBuilder() {
        return new Builder()
                .setCount(this.count)
                .setTitle(this.title)
                .setSubtitle(this.subtitle);
    }

    public class Builder {

        public int count = 0;
        public String title;
        public String subtitle;

        public Builder setCount(int count) {
            this.count = count;
            return this;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setSubtitle(String subtitle) {
            this.subtitle = subtitle;
            return this;
        }

        public PaymentResultProps build() {
            return new PaymentResultProps(this);
        }
    }
}
