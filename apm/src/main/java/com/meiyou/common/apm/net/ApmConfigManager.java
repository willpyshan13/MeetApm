package com.meiyou.common.apm.net;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.meiyou.common.apm.Constant;
import com.meiyou.common.apm.controller.Config;
import com.meiyou.common.apm.controller.ConfigRemote;
import com.meiyou.common.apm.db.bean.DeviceBean;
import com.meiyou.common.apm.net.http.RequestHelper;
import com.meiyou.common.apm.util.DeviceUtils;
import com.meiyou.common.apm.util.LogUtils;
import com.meiyou.common.apm.util.SPUtils;
import com.meiyou.common.apm.util.UUIDUtils;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * "/init" 获取 Config 配置数据
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 18/1/22
 */

public class ApmConfigManager {

    private static ApmConfigManager instance;

    public static ApmConfigManager getInstance() {
        if (instance == null) {
            instance = new ApmConfigManager();
        }
        return instance;
    }

    public void init(final Context context) {
        String content = getDevice(context);
        String url = Constant.getHost() + "/stats/init?version=" + Constant.VERSION;
        RequestHelper.doPost(url, content, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
                LogUtils.w("request apm init fail,please check network");
                Config.log();
            }

            @Override
            public void onResponse(Call call, Response response) {
                ResponseBody responseBody = response.body();
                if (responseBody == null) return;
                try {
                    //https://github.com/square/okhttp/issues/2311
                    String body = responseBody.string();
                    JSONObject json = null;

                    json = new JSONObject(body);

                    String code = json.optString("code");
                    String message = json.optString("message");
                    if (!TextUtils.isEmpty(message)) {
                        LogUtils.d(message);
                        return;
                    }
                    JSONObject data = json.optJSONObject("data");
                    String enable = data.optString("enable");
                    String token = data.optString("token");
                    JSONObject cfg = data.optJSONObject("cfg");

                    int interval = cfg.optInt("interval");
                    //单位 ：s ,如：60S, 异常处理防止服务端太频繁
                    int realInterval = interval * 1000;
                    if (realInterval < 1000) {
                        realInterval = 1000;
                    }
                    //弱网情况下，token可能丢失
                    if (TextUtils.isEmpty(token)) return;

                    ConfigRemote config = new ConfigRemote();
                    config.setEnable("1".equals(enable));
                    config.setToken(token);
                    config.setInterval(realInterval);
                    String toJson = new Gson().toJson(config);

                    SPUtils.setSP(context, "config", toJson);
                    //初始 Config
                    Config.setConfigRemote(config);
                    //服务开关关闭=》 停止上传
                    if (!config.isEnable()) {
                        ApmSyncManager.getInstance().stop();
                    }
                } catch (Exception e) {
//                    e.printStackTrace();
                    ApmSyncManager.getInstance().stop();
                    LogUtils.e("Apm Init=> getConfig Error!");
                } finally {
                    Config.log();
                    responseBody.close();
                }
            }
        });
    }

    public ConfigRemote getConfig(Context context) {
        String config = (String) SPUtils.getSp(context, "config", "");
        ConfigRemote configRemote = new ConfigRemote();
        if (!TextUtils.isEmpty(config)) {
            configRemote = new Gson().fromJson(config, ConfigRemote.class);
        }
        return configRemote;
    }

    private String getDevice(Context context) {
        //IMEI,MAC,AndroidId?
        String device = (String) SPUtils.getSp(context, "device", "");
        if (TextUtils.isEmpty(device)) {
            DeviceBean bean = new DeviceBean();
            bean.did = UUIDUtils.getDeviceUUID(context);
            bean.dev.add("Android");
            bean.dev.add(DeviceUtils.getBuildVersionSDK() + "");
            bean.dev.add(DeviceUtils.getDevice());
            bean.dev.add(DeviceUtils.getModel());
            bean.dev.add(DeviceUtils.getManufacturer());
            bean.dev.add(DeviceUtils.getDensity(context));

            bean.app.add(context.getPackageName());
//            bean.app.add(context.getApplicationInfo().name);
            PackageInfo packageInfo = null;
            try {
                packageInfo = context.getPackageManager()
                                     .getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
                int labelRes = packageInfo.applicationInfo.labelRes;
                String appName = context.getResources().getString(labelRes);
                bean.app.add(appName);
                bean.app.add(packageInfo.versionName);
                bean.app.add(RequestHelper.getMyClient());
            } catch (Exception e) {
                e.printStackTrace();
            }
            String content = new Gson().toJson(bean);
            SPUtils.setSP(context, "device", content);
            device = content;
        }
        return device;
    }
}
