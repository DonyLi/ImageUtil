package com.jason.jasonimageutil;

public interface RemoveThreadPool<T extends Runnable> {
    void execute(T runnable);

    void remove(T runnable);
}
