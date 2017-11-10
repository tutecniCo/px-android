package com.mercadopago.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.mercadopago.examples.R;
import com.mercadopago.images.ImageCache;
import com.mercadopago.images.ImageLoader;
import com.mercadopago.util.ScaleUtil;
import com.squareup.picasso.Picasso;

public class ImageLoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_load);

        findViewById(R.id.reload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadImages();
            }
        });

        findViewById(R.id.clear_cache).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageCache.reset();
            }
        });

        loadImages();
    }

    private void loadImages() {

        final ImageView img = findViewById(R.id.image);
        final int dimen = ScaleUtil.getPxFromDp(10, this);
        final int placeholder = R.drawable.mpsdk_review_product_placeholder;
        final String url = "https://vignette.wikia.nocookie.net/halonocanon/images/2/2e/Paisaje_de_Handia_por_la_noche.png/revision/latest?cb=20120605154655&path-prefix=es";

        ImageLoader.with(this)
            .load(url)
            .transform(ImageLoader.TRANSFORM_CRCLE)
            .placeholder(placeholder)
            .resize(dimen)
            .into(img);


    }
}