package com.jason.jasonimageutil;


import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPool implements RemoveThreadPool<Task> {
    private Thread[] threads;
    private boolean[] isAlives;
    private ArrayBlockingQueue<Task> runnableList = new ArrayBlockingQueue<Task>(200, true);

    public ThreadPool(int threadNum) {
        threads = new Thread[threadNum];
        isAlives = new boolean[threadNum];
    }

    public Thread initThread(final int position) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                Task task;
                while ((task = runnableList.poll()) != null) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (task.tag == 0) {
                        task.run();
                    }
                }
                isAlives[position] = false;
            }
        };
        return thread;
    }

    @Override
    public void execute(Task runnable) {
        runnableList.add(runnable);
        awakeThread(runnableList.size());

    }

    private void awakeThread(int size) {
        for (int i = 0; i < threads.length && i < size; i++) {
            if (!isAlives[i]) {
                isAlives[i] = true;
                Thread thread = initThread(i);
                thread.start();

            }

        }

    }

    @Override
    public void remove(Task runnable) {
        runnable.tag = 1;
    }
}
