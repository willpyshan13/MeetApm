package com.android.okhttpsample;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.okhttpsample.http.AESOperator;
import com.android.okhttpsample.id.UniqueIdUtils;
import com.android.okhttpsample.permission.PermissionActivity;
import com.android.okhttpsample.permission.PermissionsManager;
import com.android.okhttpsample.permission.PermissionsResultAction;
import com.android.okhttpsample.webview.WebViewActivity;
import com.meiyou.common.apm.controller.ApmAgent;
import com.meiyou.common.apm.net.ApmConfigManager;
import com.meiyou.common.apm.net.ApmSyncManager;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static String umengMac;
    TextView textView;
    private static final String TAG = "MainActivity";
    private PermissionsManager permissionsManager;
    private Context context;
    private boolean success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        XLogging.install();
        context = this.getApplicationContext();

        Button SyncGet = (Button) findViewById(R.id.SyncGet);
        SyncGet.setOnClickListener(this);
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(this);
        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(this);
        findViewById(R.id.button4).setOnClickListener(this);
        findViewById(R.id.btn_webview).setOnClickListener(this);

        textView = (TextView) findViewById(R.id.textView);

        permissionsManager = PermissionsManager.getInstance();

//        XLogging.install(new XLoggingCallback() {
//            @Override
//            public void handle(TransactionData transactionData) {
//                System.out.println(transactionData.toString());
//            }
//        });

        requestAll();
        
        ApmAgent.setAppId("1")
                .setDebug(true)
                .useTcp(false)
                .setEnable(true)
                .start(this);

        ApmAgent.setExtra("uid", 0);
        ApmAgent.setExtra("user_name", "joe");
//        LogUtils.d("what  the  cool");
        
    }


    public void requestAll() {
        permissionsManager
                .requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
                    @Override
                    public void onGranted() {

                    }

                    @Override
                    public void onDenied(String permission) {


                    }
                });
    }

    // Implement the OnClickListener callback

    public void testImei() {
        Map<String, String> deviceInfo = UniqueIdUtils.getDeviceInfo(context);
        deviceInfo.put("umengMac", getUmengMac());
        deviceInfo.put("newMac", UniqueIdUtils.getAdresseMAC(context));
        String toString = deviceInfo.toString();
        Log.e(TAG, "onClick: " + toString);
        Toast.makeText(context, "" + toString, Toast.LENGTH_SHORT).show();

    }

    private static String getUmengMac() {
        try {
            if (umengMac != null)
                return umengMac;
            String mac = null;
            FileReader fstream = null;
            try {
                fstream = new FileReader("/sys/class/net/wlan0/address");
            } catch (FileNotFoundException e) {
                fstream = new FileReader("/sys/class/net/eth0/address");
            }
            BufferedReader in = null;
            if (fstream != null) {
                try {
                    in = new BufferedReader(fstream, 1024);
                    mac = in.readLine();
                } catch (IOException e) {
                } finally {
                    if (fstream != null) {
                        try {
                            fstream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (in != null) {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            umengMac = mac;
            return mac;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        umengMac = "";
        return "";
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.SyncGet:
//                testImei();
//                WifiStatController.getInstance().init();
//                System.out.println("Test:  click button1");
                SynchronousGet.executeSynchronousGet();

//                startTestActivity();
                break;

            case R.id.button2:
//                requestAll();
//                System.out.println("Test:  click button2");
                AsynchronousGet.executeAsynchronousGet();
//                AsynchronousGetModule.executeAsynchronousGet();
//                AGetModuleTemp.executeAsynchronousGet();
//                AsynchronousGet.runLoop();

                break;

            case R.id.button3:
                System.out.println("Test:  click ApmConfigManager.init;");
//                PostRequest.executePostRequest();
                ApmConfigManager.getInstance().init(context);
                break;
            case R.id.button4:
                Log.d(TAG, "onClick: ApmSyncManager.getInstance().doSync");
//                test();
//                GpsPhotoController.getInstance().getGps();
//                requestPermissionsUseContext();
                ApmSyncManager.getInstance().doSync(context);
                break;
            case R.id.btn_webview:
                startActivity(new Intent(this, WebViewActivity.class));
                break;
            default:

                break;
        }
    }


    public void startTestActivity() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.putExtra(PermissionActivity.KEY_PERMISSION, permissions);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        PermissionActivity.action = action;
        startActivity(intent);

    }


    public void test() {
        String content = "啊啊坎大哈的卡扩大2342jndsfsx";

        try {
            AESOperator aes = AESOperator.getInstance();

//            String temp="7prXtbnTSDKCMUEXJCVXjpvx/5crbsLTV6TCHkSuYEg=";
//            Log.e(TAG, aes.decrypt(temp));


            //加密
            Log.e(TAG, "加密前：" + content);
            String encrypt = aes.encrypt(content);
            Log.e(TAG, "加密后：" + new String(encrypt));
            //解密
            String decrypt = aes.decrypt(encrypt);
            Log.e(TAG, "解密后：" + decrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void testCheckPermission() {
        HashMap map = new HashMap();
        if (!hasPermission(Manifest.permission.CAMERA) || !hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            map.put("success", 0);
            success = false;
        } else {
            map.put("success", 1);
            success = true;
        }
        Toast.makeText(context, "success:" + success, Toast.LENGTH_SHORT).show();
    }

    public boolean hasPermission(String permission) {
        try {
            if (PermissionsManager.getInstance()
                                  .hasPermission(context, permission) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return true;
            } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void requestPermissionsUseContext() {
        permissionsManager.requestPermissionsUseContext(context, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                Log.e(TAG, "onGranted: New: ");
            }

            @Override
            public void onDenied(String permission) {
                Log.e(TAG, "onDenied: New: ");
            }
        });
    }


}
