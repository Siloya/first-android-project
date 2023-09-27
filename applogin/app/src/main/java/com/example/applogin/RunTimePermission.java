package com.example.applogin;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class RunTimePermission extends AppCompatActivity {
    private PermissionCallback callback;
    private Context context;

    public RunTimePermission(Context context) {
        this.context = context;
    }

    public interface PermissionCallback {
        void onPermissionGranted(boolean isGranted);
    }

    public void requestPermission(String[] permissions, PermissionCallback callback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean granted = true;
           /* for (String permission : permissions) {
                if (context == null){
                    Log.i("msg:", "context is nullllll ");
                }else{
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    break;
                }}
            }*/
            if (granted) {
                callback.onPermissionGranted(true);// HON
            } else {
                this.callback = callback;
                requestPermissions(permissions, Integer.MAX_VALUE);// hy l3m tjib l exception
            }
        } else {
            callback.onPermissionGranted(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Integer.MAX_VALUE) {
            boolean granted = true;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    granted = false;
                    break;
                }
            }
            if (granted) {
                if (callback != null) {
                    callback.onPermissionGranted(true);
                }
            } else {
                onDenied();
            }
        }
    }

    private void onDenied() {
        if (callback != null) {
            callback.onPermissionGranted(false);
        }
    }
}
