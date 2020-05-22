package com.android.okhttpsample.permission;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import com.android.okhttpsample.R;

/**
 * 提供 透明页面，申请权限，方便在无Activity的地方调用
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/12/5 下午3:22
 */
public class PermissionActivity extends FragmentActivity {
    private static final String TAG = "PermissionActivity";
    public static final String KEY_PERMISSION = "permission";
    public static PermissionsResultAction action;
    Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        activity = this;
        Intent intent = getIntent();
        String[] permissions = intent.getStringArrayExtra(KEY_PERMISSION);
        PermissionsManager manager = PermissionsManager.getInstance();
        manager.requestPermissionsIfNecessaryForResult(this, permissions, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                if (action != null) {
                    action.onGranted();
                }
                action = null;
                activity.finish();
//                Log.e(TAG, "onGranted: ");
            }

            @Override
            public void onDenied(String permission) {
                if (action != null) {
                    action.onDenied(permission);
                }
                action = null;
                activity.finish();
//                Log.e(TAG, "onDenied: ");
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionsManager.getInstance().notifyPermissionsChange(permissions, grantResults);
    }
}

