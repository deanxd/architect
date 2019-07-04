package com.deanxd.binder.a;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.deanxd.binder.ILoginService;

public class LoginResultService extends Service {

    @Override
    public IBinder onBind(final Intent intent) {
        return new ILoginService.Stub() {
            @Override
            public void requestLogin() throws RemoteException {

            }

            @Override
            public void loginCallback(String name, String pwd) throws RemoteException {
                Log.e("login>>", "get login result form b, name:" + name + " , pwd:" + pwd);
            }
        };
    }
}
