package com.jason.jasonimageutil;

import android.widget.ImageView;

public interface ImageLoadCallBack {
    void onStart(ImageView imageView);

    void onLoadCache(ImageView imageView, ReadImageResult result);

    void onFinish(ImageView imageView, ReadImageResult result);

    void onFailed(ImageView imageView, ReadImageResult result);
}
