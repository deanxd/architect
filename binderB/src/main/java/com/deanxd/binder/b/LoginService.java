package com.deanxd.binder.b;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.deanxd.binder.ILoginService;

public class LoginService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return new ILoginService.Stub() {
            @Override
            public void requestLogin() throws RemoteException {
                startLoginActivity();
            }

            @Override
            public void loginCallback(String name, String pwd) throws RemoteException {

            }
        };
    }

    private void startLoginActivity() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(getApplication(), MainActivity.class);
        startActivity(intent);
    }
}
