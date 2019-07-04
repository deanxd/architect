package com.deanxd.handler.core;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class MessageQueue {

    private BlockingQueue<Message> mBlockingDeque;

    MessageQueue() {
        mBlockingDeque = new ArrayBlockingQueue<>(50);
    }

    void enqueueMessage(Message msg) {
        try {
            mBlockingDeque.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Message next() {
        try {
            return mBlockingDeque.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
