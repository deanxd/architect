package com.deanxd.handler;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler mH = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                Log.e("car--->", Thread.currentThread().getName() + " get message content:" + message.obj);
                return true;
            }
        });

        HandlerThread threadOne = new HandlerThread();
        threadOne.setH(mH);

        threadOne.start();
    }

    public static class HandlerThread extends Thread {
        Handler mOtherHandler;

        void setH(Handler mHTwo) {
            this.mOtherHandler = mHTwo;
        }

        @Override
        public void run() {
            super.run();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message obtain = Message.obtain();
            obtain.obj = " message from " + Thread.currentThread().getName();
            mOtherHandler.sendMessage(obtain);
        }
    }

}
