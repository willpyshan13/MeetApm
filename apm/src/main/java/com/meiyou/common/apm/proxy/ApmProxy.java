package com.meiyou.common.apm.proxy;

import android.text.TextUtils;

import com.meiyou.common.apm.util.LogUtils;

import java.lang.reflect.Method;

/**
 * Apm 代理类： 需要在主工程实现的类，
 * ps: 类不能混淆
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 18/4/8
 */

public class ApmProxy {

    public static final String APM_IMPL = "com.meiyou.common.apm.ApmProxyImpl";
    private static String myClient = "";

    /**
     * 是否有Tcp 实现类
     *
     * @return
     */
    public static boolean hasTcp() {
        boolean hasTcpImpl = false;
        try {
            hasTcpImpl = Class.forName(APM_IMPL) != null;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("Didn't find Tcp class.");
        }
        return hasTcpImpl;
    }

    /**
     * 使用TCP发送数据
     *
     * @param message
     * @return
     */
    public static int sendMsgTcp(String message) {
        int ret = -1;
        try {
            Object instance = Class.forName(APM_IMPL).newInstance();
            Method method = instance.getClass().getMethod("sendMessage", String.class);
            ret = (int) method.invoke(instance, message);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("Apm: sendMsgTcp： fail");
        }
        return ret;
    }

    public static String getMyClient() {
        if (!TextUtils.isEmpty(myClient)) {
            return myClient;
        }
        //主工程拿
        try {
            Object instance = Class.forName(APM_IMPL).newInstance();
            Method method = instance.getClass().getMethod("getClient");
            myClient = (String) method.invoke(instance);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.e("Apm: 获取主工程myClient失败");
        }
        if (TextUtils.isEmpty(myClient)) {
            myClient = "0130620111100000";
        }
        return myClient;
    }

}
