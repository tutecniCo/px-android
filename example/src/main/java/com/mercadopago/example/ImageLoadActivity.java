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

public class ImageLoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_load);



        ImageLoader.with(this)
//            .load("http://es.seaicons.com/wp-content/uploads/2015/06/clock-icon.png")
            .load("https://vignette.wikia.nocookie.net/halonocanon/images/2/2e/Paisaje_de_Handia_por_la_noche.png/revision/latest?cb=20120605154655&path-prefix=es")
            .transform(ImageLoader.TRANSFORM_CRCLE)
                .sc
            .into((ImageView) findViewById(R.id.image));
    }
}
