package com.mercadopago.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.AsyncTask;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringDef;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by nfortuna on 11/6/17.
 */

public class ImageLoader {

    public static final String TRANSFORM_NONE = "transform_none";
    public static final String TRANSFORM_CRCLE = "transform_circle";


    @StringDef({TRANSFORM_NONE, TRANSFORM_CRCLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Transform {

    }

    public static Builder with(final Context context) {
        return new Builder(context);

    }

    public static class Builder {

        private final Context context;
        private String url;
        private boolean centerInside;
        private Point size;
        private @Transform String transform = TRANSFORM_NONE;
        private @DrawableRes int placeholder;
        private ImageView image;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder load(final String url) {
            this.url = url;
            return this;
        }

        public Builder transform(@Transform String transform) {
            this.transform = transform;
            return this;
        }

        /** Resize the image to the specified size in pixels. */
        public Builder resize(final int width, final int height) {
            if (width < 0) {
                throw new IllegalArgumentException("Width must be positive number or 0.");
            }
            if (height < 0) {
                throw new IllegalArgumentException("Height must be positive number or 0.");
            }
            if (height == 0 && width == 0) {
                throw new IllegalArgumentException("At least one dimension has to be positive number.");
            }
            this.size = new Point(width, height);
            return this;
        }

        public Builder centerInside() {
            this.centerInside = true;
            return this;
        }

        public Builder placeholder(@DrawableRes final int placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public AsyncTask into(@NonNull final ImageView image) {
            this.image = image;
            return new LoadImageTask().execute(this);
        }
    }

    public static class LoadImageTask extends AsyncTask<Builder, Void, Bitmap> {

        private ImageView image;

        @Override
        protected Bitmap doInBackground(final Builder... builders) {

            final OkHttpClient client = new OkHttpClient();

            if (builders != null && builders.length > 0) {

                final Builder builder = builders[0];
                image = builder.image;

                if (builder.centerInside) {
                    image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }

                if (builder.size != null) {

                }

                final Request request = new Request.Builder()
                        .url(builder.url)
                        .build();

                final Response response;

                try {

                    response = client.newCall(request).execute();

                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    final InputStream inputStream = response.body().byteStream();
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);


                    if (builder.transform == TRANSFORM_CRCLE) {
                        return transformCircle(bitmap);
                    }

                    return bitmap;

                } catch (final IOException e) {
                    Log.d(ImageLoader.class.getName(), e.getMessage(), e);
                }
            }

            //DEvolver placeholder!!!
            return null;
        }

        @Override
        protected void onPostExecute(final Bitmap bitmap) {
            if (bitmap != null) {
                image.setImageBitmap(bitmap);
            }
        }
    }

    private static Bitmap transformCircle(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());

        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;

        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (!squaredBitmap.equals(source)) {
            source.recycle();
        }

        Bitmap bitmap = Bitmap.createBitmap(size, size, source.getConfig());

        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);

        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);

        squaredBitmap.recycle();
        return bitmap;
    }



//    Picasso.with(mContext)
//            .load(pictureUrl)
//                    .transform(new CircleTransform())
//            .resize(dimen, dimen)
//                    .centerInside()
//                    .placeholder(resId)
//                    .into(mProductImage);
}
