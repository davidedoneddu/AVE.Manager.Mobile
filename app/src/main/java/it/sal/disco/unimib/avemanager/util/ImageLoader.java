package it.sal.disco.unimib.avemanager.util;

import android.graphics.Bitmap;

public interface ImageLoader {
    void loadImage(String orgId, DataCallback<Bitmap> callback);
}