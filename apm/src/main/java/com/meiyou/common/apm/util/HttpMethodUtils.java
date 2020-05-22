package com.meiyou.common.apm.util;

import android.text.TextUtils;

import com.meiyou.common.apm.net.bean.MetricsBean;

/**
 * 获取 HTTP Method
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 18/2/6
 */

public class HttpMethodUtils {

    public static int getMethod(String method) {
        int result = MetricsBean.method_unknown;
        if (TextUtils.isEmpty(method)) return result;
        switch (method) {
            case "GET":
                result = MetricsBean.method_get;
                break;
            case "POST":
                result = MetricsBean.method_post;
                break;
            case "PATCH":
                result = MetricsBean.method_patch;
                break;
            case "PUT":
                result = MetricsBean.method_put;
                break;
            case "DELETE":
                result = MetricsBean.method_delete;
                break;
            case "HEAD":
                result = MetricsBean.method_head;
                break;
            default:
                break;
        }

        return result;
    }
}
