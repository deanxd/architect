package com.deanxd.binder.a;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.deanxd.binder.ILoginService;

public class MainActivity extends Activity {

    private boolean mIsRemoteConnect;
    private ILoginService mLoginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindService();
    }

    public void login(View view) {
        if (mLoginService == null) {
            Toast.makeText(this, "B应用未安装", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            mLoginService.requestLogin();

        } catch (RemoteException e) {
            e.printStackTrace();
            Toast.makeText(this, "B应用未安装", Toast.LENGTH_SHORT).show();
        }
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setAction("Bind_service_B");
        intent.setPackage("com.deanxd.binder.b");
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
