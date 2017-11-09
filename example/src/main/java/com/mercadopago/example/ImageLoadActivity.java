package com.mercadopago.example;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.mercadopago.examples.R;
import com.mercadopago.images.ImageLoader;
import com.mercadopago.util.CircleTransform;
import com.mercadopago.util.ScaleUtil;
import com.squareup.picasso.Picasso;

public class ImageLoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_load);

        final ImageView p1 = findViewById(R.id.image_picasso_1);
        final ImageView p2 = findViewById(R.id.image_picasso_2);

        final ImageView l1 = findViewById(R.id.image_image_laoder_1);
        final ImageView l2 = findViewById(R.id.image_image_laoder_2);

        final int dimen = ScaleUtil.getPxFromDp(10, this);
        final String url = "https://vignette.wikia.nocookie.net/halonocanon/images/2/2e/Paisaje_de_Handia_por_la_noche.png/revision/latest?cb=20120605154655&path-prefix=es";

        Picasso.with(this)
            .load(url)
//            .centerInside()
            .placeholder(R.drawable.mpsdk_review_product_placeholder)
            .into(p1);

        Picasso.with(this)
            .load(url)
            .transform(new CircleTransform())
//            .resize(dimen, dimen)
//            .centerInside()
            .placeholder(R.drawable.mpsdk_review_product_placeholder)
            .into(p2);

        ImageLoader.with(this)
//            .load("http://es.seaicons.com/wp-content/uploads/2015/06/clock-icon.png")
            .load(url)
//            .transform(ImageLoader.TRANSFORM_CRCLE)
                .placeholder(R.drawable.mpsdk_review_product_placeholder)
//                .centerInside()
//                .resize(dimen)
            .into(l1);


        ImageLoader.with(this)
            .load(url)
            .transform(ImageLoader.TRANSFORM_CRCLE)
            .placeholder(R.drawable.mpsdk_review_product_placeholder)
//            .resize(dimen)
            .into(l2);

    }
}
