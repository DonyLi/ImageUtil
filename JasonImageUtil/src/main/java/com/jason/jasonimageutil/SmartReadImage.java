package com.jason.jasonimageutil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SmartReadImage implements ReadImage {
    Context context;


    public SmartReadImage(Context context) {
        this.context = context;
    }

    @Override
    public ReadImageResult readImage(String path, int widthLim, boolean isGif) {

        if (path == null)
            return null;
        ReadImageResult result = new ReadImageResult();
        if (path.startsWith("http")) {
            File localFile = new File(Environment.getExternalStorageDirectory(), "Android/" + context.getPackageName() + "/" + MD5Util.MD5(path));
            LockMap.Lock lock = LockMap.getLock(path);
            synchronized (lock) {
                if (lock.mark == 1 || ImageDownloader.loadFromIntert(path, localFile.getAbsolutePath())) {
                    path = localFile.getAbsolutePath();
                    lock.mark = 1;
                } else {
                    return null;
                }
            }
        }
        String bitmapType = getBitmapTpye(path);
        if (isGif && bitmapType != null && bitmapType.contains("gif")) {
            try {
                GifDecoder decoder = new GifDecoder(new FileInputStream(path));
                decoder.setWidthLimit(widthLim);
                decoder.decode();
                if (decoder.getStatus() == GifDecoder.STATUS_FINISH) {
                    for (int i = 0; i < decoder.getFrameCount(); i++) {
                        result.getFrameList().add(decoder.getFrame(i));
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                result.getFrameList().add(new Frame(compressBitmap(path, widthLim), 0, widthLim));
            }catch (Exception e){
               e.printStackTrace();
            }
        }

        return result;
    }

    public Bitmap compressBitmap(String path, int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = options.outWidth / width;
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }


    public String getBitmapTpye(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        return options.outMimeType;
    }
}
