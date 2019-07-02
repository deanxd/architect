package com.deanxd.architect;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    private Handler handler = new Handler(new CallBack());

    private class  CallBack implements Handler.Callback{

        @Override
        public boolean handleMessage(Message msg) {
            Log.e("tst", "get a message form handler");
            return false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void testForHandler(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                handler.sendEmptyMessageDelayed(1, 100 * 1000);
            }
        }).start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
