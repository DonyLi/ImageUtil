package com.jason.jasonimageutil;

import android.graphics.Bitmap;
import android.os.Handler;
import android.widget.ImageView;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class BaseImageUtil {

    private static int LOADINGTAG = 0;
    private static int TAGKEY = 3 << 24;
    private static int OLDTAG = 3 << 24 + 4;
    private static ThreadPool service = new ThreadPool(5);
    private static Handler handler = new Handler();
    private static ConcurrentHashMap<String, Task> taskConcurrentHashMap = new ConcurrentHashMap<>();
    private static LoadCache<ReadImageResult> bitmapLoadCache = new LoadCache<ReadImageResult>() {
        @Override
        public int sizeOf(String key, ReadImageResult value) {
            List<Frame> frames = value.getFrameList();
            int total = 0;
            for (int i = 0; i < frames.size(); i++) {
                total += frames.get(i).getBitmap().getByteCount();
            }
            return total / 1024;
        }
    };

    public static void loadImage(final String path, final ImageView imageView,
                                 final ReadImage image, final ImageLoadCallBack loadCallBack, final int widthLim, final boolean isGif) {
        if (path != null) {
            loadCallBack.onStart(imageView);
            final String tagString = path + widthLim;
            ReadImageResult result = bitmapLoadCache.getCache(tagString);
            if (result != null) {
                loadCallBack.onLoadCache(imageView, result);
                return;
            }
            final int tag = LOADINGTAG++;
            imageView.setTag(TAGKEY, tag);
            Object objectTag = imageView.getTag(OLDTAG);
            if (objectTag != null) {
                Task task = taskConcurrentHashMap.get(tagString);
                if (task != null) {
                    service.remove(task);
                    taskConcurrentHashMap.remove(tagString);
                }
            }
            if (!tagString.equals(objectTag)) {
                imageView.setTag(OLDTAG, tagString);
                Task task = new Task() {

                    @Override
                    public void run() {
                        taskConcurrentHashMap.remove(tagString);
                        final ReadImageResult result = image.readImage(path, widthLim, isGif);
                        if (result != null) {
                            bitmapLoadCache.putInCache(tagString, result);
                            if ((int) imageView.getTag() == tag) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadCallBack.onFinish(imageView, result);
                                    }
                                });
                            }
                        } else {
                            if ((int) imageView.getTag() == tag) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadCallBack.onFailed(imageView, result);
                                    }
                                });
                            }
                        }
                    }
                };
                service.execute(task);
                taskConcurrentHashMap.put(tagString, task);
            }
        }
    }
}
