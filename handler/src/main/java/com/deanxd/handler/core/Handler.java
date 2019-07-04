package com.deanxd.handler.core;

public class Handler {

    private Callback mCallback;
    private MessageQueue mQueue;

    public Handler() {
        this(null);
    }

    public Handler(Callback mCallback) {
        this.mCallback = mCallback;
        Looper looper = Looper.myLooper();
        if (looper == null) {
            throw new RuntimeException("Can't create handler inside thread " + Thread.currentThread() + " that has not called Looper.prepare()");
        }
        mQueue = looper.myQueue();
    }

    public void sendMessage(Message msg) {
        msg.target = this;
        enqueueMessage(msg);
    }

    private void enqueueMessage(Message msg) {
        mQueue.enqueueMessage(msg);
    }


    public void handleMessage(Message message) {

    }

    public void dispatchMessage(Message message) {
        if (message.callback != null) {
            message.callback.run();
        } else {
            if (mCallback != null) {
                if (mCallback.handleMessage(message)) {
                    return;
                }
            }
            handleMessage(message);
        }
    }

    public interface Callback {
        boolean handleMessage(Message message);
    }

}
