package com.meiyou.common.apm.util;


/**
 * session Id 生成器
 * 应用前台=》后台, 超过30s，重新生成ID；
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/5/11
 */

public class SessionCreator {
    private static SessionCreator instance;
    private long sessionId;

    public static SessionCreator getInstance() {
        if (instance == null) {
            instance = new SessionCreator();
        }
        return instance;
    }

    /**
     * 生成ID
     * @return
     */
    public String getId() {
        if (sessionId == 0) {
            sessionId = System.currentTimeMillis();
        }
        return sessionId + "";
    }

    /**
     * 重置ID
     */
    public void resetId() {
        sessionId = 0;
        LogUtils.e("session Id  reset");
    }


}
