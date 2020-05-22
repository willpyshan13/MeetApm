package com.meiyou.common.apm.controller;

import android.content.Context;

import com.meiyou.common.apm.Constant;
import com.meiyou.common.apm.net.ApmConfigManager;
import com.meiyou.common.apm.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 配置类： 静态变量实现
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/5/12
 */

public class Config {
    public static final int MINUTE_3 = Constant.MINUTE * 3;

    /**
     * 是否运行开始
     */
    public static boolean enable = false;


    public static String licenseKey = "";

    /**
     * 轮询上报间隔时间；3分钟
     */
    public static int interval = MINUTE_3;

    /**
     * 是否过滤的Url ,不拦截
     */
    public static ArrayList<String> filterList = new ArrayList<>();
    public static String AppId = "1";
    /**
     * 是否Debug模式
     */
    public static boolean debug = false;
    /**
     * 是否使用TCP上传
     */
    public static boolean useTcp = true;

    static {
        //DNS轮询接口
        filterList.add("d.swf");
        filterList.add("apm.meiyou.com");
        filterList.add("qa-test");
    }


//    /**
//     * events 是要处理的事件名称列表，例如 ["foo", "!bar"] 这种，前面加 ! 代表过滤， [""] 或空代表允许所有，["!"] 代表禁止所有
//     */
//    public String[] events = {};
    /**
     * 服务端返回的Token
     */
    public static String token = "";

    public static HashMap<String, Object> extra = new HashMap<String, Object>();

    public Config() {
    }

    public static void loadSp(Context context) {
        ConfigRemote config = ApmConfigManager.getInstance().getConfig(context);
        setConfigRemote(config);

//        LogUtils.d("Config: success");
    }

    public static void setConfigRemote(ConfigRemote config) {
        interval = (config.interval);
        enable = config.isEnable();
        token = (config.getToken());
    }

    /**
     * 打印 Config log
     */
    public static void log() {
        String log = String.format("Config: enable: %s, interval: %s, useTcp: %s;  token: %s", enable, interval, useTcp, token);
        LogUtils.d(log);
    }

//    @Override
//    public String toString() {
//        return super.toString();
//        return String.format("Apm => Config: enable: %s, interval: %s, useTcp: %s;  token: %s", enable, interval, useTcp, token);
//    }


    //    public boolean isEnable() {
//        return enable;
//    }
//
//    public void setEnable(boolean enable) {
//        this.enable = enable;
//    }

//    public int getInterval() {
//        return interval;
//    }
//
//    /**
//     * 设置 轮询上传时间
//     *
//     * @param interval < 3Min 无效
//     */
//    public void setInterval(int interval) {
//        if (interval > MINUTE_3) {
//        this.interval = interval;
//        }
//    }
//
//    public String[] getEvents() {
//        return events;
//    }
//
//    public void setEvents(String[] events) {
//        this.events = events;
//    }

//    public String getToken() {
//        return token;
//    }
//
//    public void setToken(String token) {
//        this.token = token;
//    }


//    /**
//     * 设置配置
//     *
//     * @param config
//     */
//    public void setConfig(Config config) {
//
//        if (config == null) {
//            LogUtils.w("config ==null ,Use default config");
//            return;
//        }
//        this.config = config;
//    }
//
//    public Config getConfig() {
//        return this.config;
//    }
}
