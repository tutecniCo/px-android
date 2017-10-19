package com.mercadopago.paymentresult;

import android.support.annotation.NonNull;


public class PaymentResultProps {

    public final String title;
    public final String subtitle;

    public PaymentResultProps(@NonNull final String title, @NonNull final String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    public PaymentResultProps(@NonNull final Builder builder) {
        this.title = builder.title;
        this.subtitle = builder.subtitle;
    }

    public Builder toBuilder() {
        return new Builder()
                .setTitle(this.title)
                .setSubtitle(this.subtitle);
    }

    public class Builder {

        public String title;
        public String subtitle;

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
