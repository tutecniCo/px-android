package com.mercadopago.model;

import android.content.Context;

/**
 * Created by mromar on 10/24/17.
 */

public class Device {

    Fingerprint fingerprint;

    public Device(Context context) {
        this.fingerprint = new Fingerprint(context);
    }
}
