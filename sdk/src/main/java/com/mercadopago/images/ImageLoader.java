package com.mercadopago.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
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

    public static class Config {

        public final Context context;
        public final String url;
//        public final boolean centerInside;
        public final int size;
        public final @Transform String transform;
        public final @DrawableRes int placeholder;
        public final ImageView image;

        public Config(@NonNull final Builder builder) {
            this.context = builder.context;
            this.url = builder.url;
//            this.centerInside = builder.centerInside;
            this.size = builder.size;
            this.transform = builder.transform;
            this.placeholder = builder.placeholder;
            this.image = builder.image;
        }

        public String getPlaceholderKey() {
            return placeholder + size + transform;
        }

        public String getUrlKey() {
            return placeholder + size + transform;
        }
    }

    public static class Builder {

        private final Context context;
        private String url;
//        private boolean centerInside;
        private int size = 0;
        private @Transform String transform = TRANSFORM_NONE;
        private @DrawableRes int placeholder = 0;
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

        /** Resize the image maintaining aspec ratio to the specified size in pixels. */
        public Builder resize(final int size) {
            this.size = size;
            return this;
        }

//        public Builder centerInside() {
//            this.centerInside = true;
//            return this;
//        }

        public Builder placeholder(@DrawableRes final int placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public AsyncTask into(@NonNull final ImageView image) {
            this.image = image;
            return new LoadImageTask(new Config(this)).execute();
        }
    }

    public static class LoadImageTask extends AsyncTask<Void, Void, Bitmap> {

        private Config config;

        public LoadImageTask(final Config config) {
            this.config = config;
        }

        @Override
        protected void onPreExecute() {

//            if (config.centerInside) {
            config.image.setScaleType(ImageView.ScaleType.FIT_CENTER);
//            }

            if (config.placeholder != 0) {
                config.image.setImageBitmap(getBitmapFromResource(config));
            }
        }

        @Override
        protected Bitmap doInBackground(final Void... voids) {

            if (config.url != null && !config.url.isEmpty()) {

                Bitmap bitmap = ImageCache.getBitmapFromMemCache(config.getUrlKey());

                if (bitmap == null) {

                    final OkHttpClient client = new OkHttpClient();

                    final Request request = new Request.Builder()
                            .url(config.url)
                            .build();

                    final Response response;

                    try {

                        response = client.newCall(request).execute();

                        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                        final InputStream inputStream = response.body().byteStream();
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        bitmap = getResizedBitmap(bitmap, config.size);

                        if (config.transform == TRANSFORM_CRCLE) {
                            return transformCircle(bitmap);
                        }

                        ImageCache.addBitmapToMemoryCache(config.getUrlKey(), bitmap);

                        return bitmap;

                    } catch (final IOException e) {
                        Log.d(ImageLoader.class.getName(), e.getMessage(), e);
                    }

                } else {

                    return bitmap;
                }

            } else if (config.placeholder != 0) {

                return BitmapFactory.decodeResource(
                    config.context.getResources(),
                    config.placeholder
                );
            }

            return null;
        }

        @Override
        protected void onPostExecute(final Bitmap bitmap) {
            if (bitmap != null) {
                config.image.setImageBitmap(bitmap);
            }
        }
    }

    private static Bitmap getBitmapFromResource (final Config config) {

        Bitmap bmp = ImageCache.getBitmapFromMemCache(config.getPlaceholderKey());

        if (bmp == null) {
            bmp = BitmapFactory.decodeResource(config.context.getResources(), config.placeholder);
            if (config.size > 0) {
                bmp = getResizedBitmap(bmp, config.size);
            }
            ImageCache.addBitmapToMemoryCache(config.getPlaceholderKey(), bmp);
        }

        return bmp;
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

    public static Bitmap getResizedBitmap(final Bitmap bitmap, final int maxSize) {

        if (maxSize > 0) {

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            float bitmapRatio = (float) width / (float) height;
            if (bitmapRatio > 1) {
                width = maxSize;
                height = (int) (width / bitmapRatio);
            } else {
                height = maxSize;
                width = (int) (height * bitmapRatio);
            }

            return Bitmap.createScaledBitmap(bitmap, width, height, true);
        }

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
