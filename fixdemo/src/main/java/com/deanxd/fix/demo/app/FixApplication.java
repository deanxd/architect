package com.deanxd.fix.demo.app;

import android.support.multidex.MultiDexApplication;

import com.deanxd.fix.FixDexUtils;

public class FixApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        boolean isFixSuccess = FixDexUtils.loadFixFile(this);
    }
}
