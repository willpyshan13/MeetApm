package com.meiyou.common.apm.controller;

import com.meiyou.common.apm.Constant;
import com.meiyou.common.apm.core.Proguard;

/**
 * 下发的配置信息，
 *
 * @since 17/5/12
 */
@Proguard
public class ConfigRemote {
    public static final int MINUTE_3 = Constant.MINUTE * 3;
    /**
     * 是否运行开始
     */
    public boolean enable = false;

    /**
     * 轮询上报间隔时间；3分钟
     */
    public int interval = MINUTE_3;

    public String token = "";


    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public int getInterval() {
        return interval;
    }

    /**
     * 设置 轮询上传时间
     *
     * @param interval < 3Min 无效
     */
    public void setInterval(int interval) {
//        if (interval > MINUTE_3) {
        this.interval = interval;
//        }
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
