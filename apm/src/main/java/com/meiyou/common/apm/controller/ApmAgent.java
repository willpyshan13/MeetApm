package com.meiyou.common.apm.controller;

import android.content.Context;
import android.support.annotation.NonNull;

import com.meiyou.common.apm.core.Proguard;
import com.meiyou.common.apm.util.LogUtils;

/**
 * Apm对外提供类
 * http://git.meiyou.im/iOS/iOS/blob/master/work/APM.md
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/5/11
 */
@Proguard
public class ApmAgent {

    private static final String TAG = "ApmAgent";
    //    private static ApmAgent instance;
//    private final Boolean isDebug;
//    private Config config;
    private static Context context;
    /**
     * 功能是否开启, 默认开启
     */
    public static boolean switchOn = true;


//    private ApmAgent(String licenseKey) {
//        Config.licenseKey = licenseKey;
//    }

    public ApmAgent(String appId) {
        Config.AppId = appId;
    }
//    public static ApmAgent getInstance() {
//        if (instance == null) {
//            instance = new ApmAgent();
//        }
//        return instance;
//    }


//    /**
//     * 设置 licenseKey 其实比setAppId 更通用
//     *
//     * @param licenseKey
//     * @return
//     */
//    public static ApmAgent setLicenseKey(@NonNull String licenseKey) {
//        return new ApmAgent(licenseKey);
//
//    }

    /**
     * 设置美柚应用的Id
     * <pre>
     * APP_PERIOD = "1";
     * com.meiyou.framework.common.AppId
     * </pre>
     *
     * @param appId
     * @return
     */
    public static ApmAgent setAppId(@NonNull String appId) {
        return new ApmAgent(appId);
    }

    /**
     * 启动
     *
     * @param context
     */
    public void start(@NonNull Context context) {
        ApmAgent.context = context.getApplicationContext();
        Config.loadSp(context);

//        //请求配置
//        ApmConfigManager.getInstance().init(context);
//        //收集数据
//        ApmSyncManager.getInstance().run(context);
    }

    public static Context getContext() {
        if (context == null) {
            Config.enable = false;
        }
        return context;
    }

    /**
     * 额外数据
     *
     * @param key
     * @param value
     */
    public static void setExtra(@NonNull String key, @NonNull Object value) {
        Config.extra.put(key, value);
    }

    /**
     * 设置是否开启调试
     *
     * @param isDebug, default: false
     */
    public ApmAgent setDebug(boolean isDebug) {
        LogUtils.init(isDebug, "APM-Agent");
        Config.debug = isDebug;
        return this;
    }

    /**
     * 是否关闭APM功能
     *
     * @param enable
     * @return
     */
    public ApmAgent setEnable(boolean enable) {
//        Config.enable = enable;
        switchOn = enable;
        return this;
    }

    /**
     * 是否使用TCP上报数据：
     *
     * @param useTcp default: true,
     * @return
     */
    public ApmAgent useTcp(boolean useTcp) {
        Config.useTcp = useTcp;
        return this;
    }

//    private ApmHelper(Context context) {
//        this.mContext = context.getApplicationContext();
//    }

//    /**
//     * 事件触发
//     */
//    public void onEvent(@NonNull String event) {
//        onEvent(event, null);
//    }
//
//    /**
//     * 事件触发
//     *
//     * @param event  事件
//     * @param params 需带入的参数
//     */
//    public void onEvent(@NonNull String event, HashMap<String, Object> params) {
//        if (!config.enable) return;
//        if (context == null) {
//            LogUtils.w(TAG, "context ==null, call init() first!");
//            return;
//        }
//        ApmBean bean = ApmBean.createBean(context);
////        bean.event = event;
//////        if (timestamp != 0) {
//////            bean.timestamp = timestamp;
//////        }
////        if (params != null) {
////            bean.attributes = JSON.toJSONString(params);
////        }
//        ApmDAO.getInstance(context).addApm(bean);
//    }
}
