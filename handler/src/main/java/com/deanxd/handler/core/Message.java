package com.deanxd.handler.core;

public class Message {

    public int what;
    public int arg1;
    public int arg2;

    public Object obj;

    Handler target;
    Runnable callback;

    public static Message obtain() {
        return new Message();
    }

    public Runnable getCallback() {
        return callback;
    }

    public void setCallback(Runnable callback) {
        this.callback = callback;
    }
}
