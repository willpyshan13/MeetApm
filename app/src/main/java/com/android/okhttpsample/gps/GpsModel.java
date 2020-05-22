package com.android.okhttpsample.gps;

import android.text.TextUtils;

/**
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/12/5
 */

public class GpsModel {
    String timestamp = "";
    /**
     * 维度
     */
    String latitude = "";
    /**
     * 经度
     */
    String longitude = "";


    public boolean isEmpty() {
        return isEmpty(timestamp, latitude, longitude);
    }

    private boolean isEmpty(String... args) {
        boolean isEmpty = false;
        for (String arg : args) {
            if (TextUtils.isEmpty(arg)) {
                isEmpty = true;
                break;
            }
        }
        return isEmpty;
    }
}
