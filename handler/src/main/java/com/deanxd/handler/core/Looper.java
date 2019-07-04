package com.deanxd.handler.core;

public class Looper {

    private static ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();
    private final MessageQueue mQueue;

    private Looper() {
        mQueue = new MessageQueue();
    }

    public static void prepare() {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new Looper());
    }

    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    public MessageQueue myQueue() {
        return myLooper().mQueue;
    }

    public void loop() {
        while (true) {
            Message next = mQueue.next();
            if (next != null) {
                Handler target = next.target;
                target.dispatchMessage(next);
            }
        }
    }

}
