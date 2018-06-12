package com.jason.jasonimageutil;

import java.util.ArrayList;
import java.util.List;

public class ReadImageResult {
    private List<Frame> frameList = new ArrayList<>();
    private Throwable throwable;

    public List<Frame> getFrameList() {
        return frameList;
    }

    public Throwable getThrowable() {
        return throwable;
    }
}
