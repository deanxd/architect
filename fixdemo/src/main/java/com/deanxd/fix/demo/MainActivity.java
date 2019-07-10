package com.deanxd.fix.demo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.deanxd.fix.FixDexUtils;
import com.deanxd.fix.demo.test.A;
import com.deanxd.fix.demo.test.MathCalculator;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            String[] permission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
            if (checkSelfPermission(permission[0]) == PackageManager.PERMISSION_DENIED) {
                requestPermissions(permission, 200);
            }
        }
    }

    public void testForMath(View view) {
        int result = MathCalculator.divisionTest();
        Toast.makeText(this, "result is " + result, Toast.LENGTH_SHORT).show();
    }

    /**
     * 执行热更新
     */
    public void fixError(View view) {
        String newDexName = "classes2.dex";
        File newDexFile = new File(Environment.getExternalStorageDirectory(), newDexName);
        FixDexUtils.copyFixFile(this, newDexFile);

        boolean isFixSuccess = FixDexUtils.loadFixFile(this);

        if (isFixSuccess) {
            Toast.makeText(this, "fix success ", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "fix error !!! ", Toast.LENGTH_SHORT).show();
        }
    }
}
