package com.mercadopago.images;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by nfortuna on 11/9/17.
 */

public class ImageCache {

    private static LruCache<String, Bitmap> memoryCache;


    static {

        // Get max available VM memory, exceeding this amount will throw an
        // OutOfMemory exception. Stored in kilobytes as LruCache takes an
        // int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        memoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(final String key, final Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                return bitmap.getByteCount() / 1024;
            }
        };
    }

    public static synchronized void addBitmapToMemoryCache(final String key, final Bitmap bitmap) {
        if (key != null && bitmap != null && getBitmapFromMemCache(key) == null) {
            memoryCache.put(key, bitmap);
        }
    }

    public static synchronized Bitmap getBitmapFromMemCache(final String key) {
        return memoryCache.get(key);
    }

    public static synchronized void reset() {
        memoryCache.evictAll();
    }
}
