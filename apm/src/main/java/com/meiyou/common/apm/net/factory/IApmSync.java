package com.meiyou.common.apm.net.factory;

import android.content.Context;

import java.util.ArrayList;

/**
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 18/3/13
 */

public interface IApmSync {
    /**
     * 同步
     *
     * @param mContext
     * @param gaList    上报数据
     * @param onSuccess 成功回调
     */
    public void sync(Context mContext, final ArrayList<String[]> gaList, OnSyncListener onSuccess);
}

