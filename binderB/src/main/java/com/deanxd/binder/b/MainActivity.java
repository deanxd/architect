package com.deanxd.binder.b;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.deanxd.binder.ILoginService;

public class MainActivity extends AppCompatActivity {

    private boolean mIsRemoteConnect;
    private ILoginService mLoginService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindService();
    }

    public void loginSuccess(View view) {
        if (mLoginService == null) {
            return;
        }

        try {
            mLoginService.loginCallback("dean", "123");
            finish();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setAction("Bind_service_A");
        intent.setPackage("com.deanxd.binder.a");

        bindService(intent, conn, BIND_AUTO_CREATE);
        mIsRemoteConnect = true;
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mLoginService = ILoginService.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onDestroy() {
        if (mIsRemoteConnect) {
            unbindService(conn);
        }
        super.onDestroy();
    }
}
