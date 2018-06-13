package com.jason.jasonimageutil;

import android.graphics.Bitmap;

public class Frame {
    private Bitmap bitmap;
    private int delayTime;
    private Frame frame;

    public Frame(Bitmap bitmap, int delayTime, int width) {
        double width2height = bitmap.getWidth() * 1.0 / bitmap.getHeight();
        this.bitmap = width2height < width ? bitmap : Bitmap.createScaledBitmap(bitmap, width, (int) (width * width2height), true);
        this.delayTime = delayTime;

    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getDelayTime() {
        return delayTime == 0 ? 100 : delayTime;
    }

    public Frame getFrame() {
        return frame;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    public void setFrame(Frame frame) {
        this.frame = frame;
    }
}
