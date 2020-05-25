package com.meiyou.common.apm.net.factory;

/**
 * APM同步 工厂类
 *
 * @since 18/3/13 下午4:40
 */
public class ApmSyncFactory {
    public static Object getClass(String clazz) {
        Object obj = null;

        try {
            obj = Class.forName(clazz).newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return obj;
    }
}