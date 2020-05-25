package com.meiyou.common.apm.net;

import android.content.Context;
import android.text.TextUtils;

import com.meiyou.common.apm.Constant;
import com.meiyou.common.apm.controller.Config;
import com.meiyou.common.apm.net.bean.ApmDbFactory;
import com.meiyou.common.apm.net.factory.ApmSyncFactory;
import com.meiyou.common.apm.net.factory.IApmSync;
import com.meiyou.common.apm.net.factory.OnSyncListener;
import com.meiyou.common.apm.net.tcp.ApmSyncTcp;
import com.meiyou.common.apm.proxy.ApmProxy;
import com.meiyou.common.apm.util.LogUtils;
import com.meiyou.common.apm.util.NetworkUtil;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * APM 数据保存到本地，然后异步跟服务器同步
 * 现在是 没有保存到本地，直接同步到服务器；
 *
 */

public class ApmSyncManager {
    private static final String TAG = "ApmSyncManager";
    private static final int TIME = Constant.MINUTE * 3;
    private static final int TIME_DELAY = Constant.MINUTE;
//    public static final String TCP_Clazz = "com.meiyou.common.apm.net.ApmSyncTcp";
//    public static final String APM_VERSION = "0.2";

    private static ApmSyncManager instance;
    private Timer timer = new Timer();
    /**
     * 是否正在上传
     */
    public static boolean isUploading = false;
    /**
     * 是否已经初始化TCP
     */
    private static boolean hasInitTcp = false;
    private boolean isUseTcp = false;

    public static ApmSyncManager getInstance() {
        if (instance == null) {
            instance = new ApmSyncManager();
        }
        return instance;
    }

    public void run(final Context mContext) {
        //默认同步策略 :60S*3同步一次； 延迟1s
        int time = Config.interval;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                doSync(mContext);
            }
        }, TIME_DELAY, time);
    }


    public void stop() {
        timer.cancel();
    }

    public synchronized void doSync(Context context) {
        try {
            final Context mContext = context.getApplicationContext();
            if (!NetworkUtil.hasNetWork(mContext)) {
                return;
            }

            if (isUploading) return;
            isUploading = true;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    uploadApm(mContext);
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadApm(Context mContext) {
        //收集数据
        final ApmDbFactory dbFactory = ApmDbFactory.getInstance(mContext);
        ArrayList<String[]> gaList = dbFactory.getApmData();
//            ArrayList<String[]> gaList = gaDAO.getApmData();
        final int size = gaList.size();
        LogUtils.d("apm List size= " + size);
        if (size == 0 || TextUtils.isEmpty(Config.token)) {
            isUploading = false;
            return;
        }

        IApmSync http = null;
        //上传
        if (useTcp()) {
            http = (IApmSync) ApmSyncFactory.getClass(ApmSyncTcp.class.getName());
        } else {
            http = (IApmSync) ApmSyncFactory.getClass(ApmSyncHttp.class.getName());
        }
        http.sync(mContext, gaList, new OnSyncListener() {
            @Override
            public void onSuccess() {
                dbFactory.getHttpDao().deleteAll();
            }
        });
    }

    /**
     * 是否使用TCP
     *
     * @return
     */
    private boolean useTcp() {
        if (!hasInitTcp) {
            hasInitTcp = true;
            isUseTcp = ApmProxy.hasTcp() && Config.useTcp;
        }
        return isUseTcp;
    }


}
