// ILoginService.aidl
package com.deanxd.binder;

// Declare any non-default types here with import statements

interface ILoginService {

    void requestLogin();

    void loginCallback(String name, String pwd);
}
