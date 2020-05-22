package com.meiyou.common.apm;


import com.meiyou.common.apm.controller.Config;

public class Constant {
    private static final String HOST = "https://test-apm.meiyou.com";
    private static final String HOST_TEST = "http://test-apm.meiyou.com";
    public static final String TAG = "XLogging";
    public static final String VERSION = "1.0.0";

    public static final int SECOND = 1000;
    public static final int MINUTE = 60 * SECOND;


    public static String getHost() {
        if (Config.debug) {
            return HOST_TEST;
        } else {
            return HOST;
        }
    }

}
