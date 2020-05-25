package com.meiyou.common.apm.net.tcp;

import android.content.Context;

import com.meiyou.common.apm.Constant;
import com.meiyou.common.apm.controller.Config;
import com.meiyou.common.apm.net.ApmSyncManager;
import com.meiyou.common.apm.net.factory.IApmSync;
import com.meiyou.common.apm.net.factory.OnSyncListener;
import com.meiyou.common.apm.net.http.RequestHelper;
import com.meiyou.common.apm.proxy.ApmProxy;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * HTTP上传通道
 *
 */

public class ApmSyncTcp implements IApmSync {
    private static final String TAG = "ApmSyncHttp";

    public static final String TCP_IMPL = "com.meiyou.common.apm.net.tcp.ApmTcpAgent";

    @Override
    public void sync(Context mContext, final ArrayList<String[]> gaList, final OnSyncListener onSyncListener) {
        try {
            ArrayList<HashMap> list = new ArrayList<>();
            HashMap<String, Object> data = new HashMap<>();
            data.put("type", "networkMetrics");
            data.put("actions", gaList);

            list.add(data);

            JSONArray jsonArray = new JSONArray(list);

            JSONObject result = new JSONObject();
            result.put("version", Constant.VERSION);
            result.put("token", Config.token);
            result.put("myclient", RequestHelper.getMyClient());
            result.put("User-Agent", RequestHelper.getUserAgent());
            result.put("data", jsonArray);

            String resultStr =result.toString();
            //反射调用
            int ret = ApmProxy.sendMsgTcp(resultStr);
//            Object instance = Class.forName(TCP_IMPL).newInstance();
//            Method method = instance.getClass().getMethod("sendMessage", String.class);
//            int ret= (int) method.invoke(instance,resultStr);
            if (ret > -1) {
                onSyncListener.onSuccess();
            }
            ApmSyncManager.isUploading = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
