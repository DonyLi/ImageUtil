package com.jason.jasonimageutil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.widget.ImageView;

import java.lang.ref.WeakReference;


public class JasonImageUtil implements Cloneable {
    private int defaultDrwable;
    private int failedDrwable;
    private ImageView imageView;
    private String path;
    private int width = 200;
    private boolean isGif = false;
    private Context context;
    ImageLoadCallBack imageLoadCallBack;
    private Handler handler = new Handler();
    private WeakReference<ImageView> imageViewWeakReference;
    private static JasonImageUtil imageUtil = new JasonImageUtil();


    public void load() {
        BaseImageUtil.loadImage(path, imageView, new SmartReadImage(context), new ImageLoadCallBack() {
            Object tag;

            @Override
            public void onStart(ImageView imageView) {
                tag = imageView.getTag(BaseImageUtil.TAGKEY);
                if (defaultDrwable != 0) {
                    imageView.setImageResource(defaultDrwable);
                } else {
                    imageView.setImageBitmap(getDefaultBitmap());
                }
                if (imageLoadCallBack != null) {
                    imageLoadCallBack.onStart(imageView);
                }
            }

            @Override
            public void onLoadCache(ImageView imageView, ReadImageResult result) {
                onLoadFinishd(imageView, result);
                if (imageLoadCallBack != null) {
                    imageLoadCallBack.onLoadCache(imageView, result);
                }
            }

            @Override
            public void onFinish(ImageView imageView, ReadImageResult result) {
                onLoadFinishd(imageView, result);


                if (imageLoadCallBack != null) {
                    imageLoadCallBack.onFinish(imageView, result);
                }
            }


            @Override
            public void onFailed(ImageView imageView, ReadImageResult result) {
                if (failedDrwable != 0) {
                    imageView.setImageResource(failedDrwable);
                } else {
                    imageView.setImageBitmap(getDefaultBitmap());
                }
                if (imageLoadCallBack != null) {
                    imageLoadCallBack.onFailed(imageView, result);
                }

            }


            private void onLoadFinishd(ImageView imageView, ReadImageResult result) {
                if (!isGif) {
                    imageView.setImageBitmap(result.getFrameList().get(0).getBitmap());
                } else {
                    if (result.getFrameList().size() > 1) {
                        handGif(result);
                    } else {
                        imageView.setImageBitmap(result.getFrameList().get(0).getBitmap());
                    }
                }
            }

            private void handGif(final ReadImageResult result) {
                imageView.setImageBitmap(result.getFrameList().get(0).getBitmap());
                imageViewWeakReference = new WeakReference<>(imageView);
                Runnable runnable = new Runnable() {
                    int i = 0;

                    @Override
                    public void run() {
                        ImageView imageView = imageViewWeakReference.get();
                        i++;
                        if (imageView == null) {
                            return;
                        }
                        Object newTag = imageView.getTag(BaseImageUtil.TAGKEY);
                        if ((int) tag == (int) newTag) {
                            Frame frame = result.getFrameList().get(i % result.getFrameList().size());
                            imageView.setImageBitmap(frame.getBitmap());
                            handler.postDelayed(this, frame.getDelayTime());
                        }
                    }
                };
                handler.postDelayed(runnable, result.getFrameList().get(0).getDelayTime());

            }
        }, width, isGif);
    }


    public Bitmap getDefaultBitmap() {
        ColorDrawable colorDrawable = new ColorDrawable(Color.TRANSPARENT);
        Bitmap bitmap = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        colorDrawable.draw(canvas);
        return bitmap;

    }

    public JasonImageUtil setGif(boolean gif) {
        isGif = gif;
        return this;
    }

    public JasonImageUtil setDefaultDrwable(int defaultDrwable) {
        this.defaultDrwable = defaultDrwable;

        return this;
    }

    public JasonImageUtil setFailedDrwable(int failedDrwable) {
        this.failedDrwable = failedDrwable;
        return this;
    }

    public JasonImageUtil setImageView(ImageView imageView) {
        this.imageView = imageView;
        return this;
    }

    public JasonImageUtil setPath(String path) {
        this.path = path;
        return this;
    }

    public JasonImageUtil setWidth(int width) {
        this.width = width;
        return this;
    }

    public static JasonImageUtil with(Context context) {
        JasonImageUtil imageUtil;
        try {
            imageUtil = (JasonImageUtil) JasonImageUtil.imageUtil.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            imageUtil = new JasonImageUtil();
        }
        imageUtil.context = context;
        return imageUtil;
    }

    public JasonImageUtil setImageLoadCallBack(ImageLoadCallBack imageLoadCallBack) {
        this.imageLoadCallBack = imageLoadCallBack;
        return this;
    }
}
