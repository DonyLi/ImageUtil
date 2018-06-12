package com.jason.jasonimageutil;

import android.graphics.Bitmap;

public class Frame {
    private Bitmap bitmap;
    private int delayTime;
    private Frame frame;

    public Frame(Bitmap bitmap, int delayTime, Frame frame) {
        this.bitmap = bitmap;
        this.delayTime = delayTime;
        this.frame = frame;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getDelayTime() {
        return delayTime;
    }

    public Frame getFrame() {
        return frame;
    }
}
