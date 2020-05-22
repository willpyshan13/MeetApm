package com.android.okhttpsample.gps;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

/**
 * Preference基类，方便保存数据,
 * 使用：
 * 1. 继承基类
 * 2. 对外提供单例方法，并在构造函数里，setPrefName； 参考PrefTest 实现；
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 16/11/15 下午5:03
 */

//public class PrefTest extends PrefBase {
//
//
//    private static PrefTest instance;
//
//    public PrefTest(Context context) {
//        super(context);
//    }
//
//    public static PrefTest getInstance(Context context) {
//        if (instance == null) {
//            instance = new PrefTest(context);
//            instance.setPrefName("pref_new");
//        }
//        return instance;
//    }
//
//}

public class PrefBase {
    public SharedPreferences.Editor editor;
    public SharedPreferences shared;
    public Context context;
    /**
     * Preferences的存储文件名称
     */

    public String SHARED_PREFERENCE = "pref_base";

    public PrefBase(@NonNull Context context) {
        this.context = context;
        setPrefName(SHARED_PREFERENCE);
    }

    public void setPrefName(@NonNull String name) {
        SHARED_PREFERENCE = name;
        shared = context.getSharedPreferences(SHARED_PREFERENCE, Context.MODE_PRIVATE);
        editor = shared.edit();
    }

    /**
     * 存储字符串到Preference
     */

    public void putStr(String key, String content) {
        editor.putString(key, content);
        editor.commit();

    }


    /**
     * 获取存储在Preference中的字符串
     */

    public String getStr(String key, String defValue) {
        return shared.getString(key, defValue);
    }

    /**
     * 存储整型数据到Preference
     */

    public void putInt(String name, int content) {
        editor.putInt(name, content);
        editor.commit();
    }

    public int getInt(String name, int def) {
        return shared.getInt(name, def);
    }

    public long getLong(String name, long def) {
        return shared.getLong(name, def);
    }

    public void putLong(String name, long value) {
        editor.putLong(name, value);
        editor.commit();
    }

    public float getFloat(String name, float def) {
        return shared.getFloat(name, def);
    }

    public void putFloat(String name, Float value) {
        editor.putFloat(name, value);
        editor.commit();
    }

    public void putBoolean(String name, boolean vaule) {
        editor.putBoolean(name, vaule);
        editor.commit();
    }

    public boolean getBoolean(String key, boolean def) {
        return shared.getBoolean(key, def);
    }

    public boolean containKey(String key) {
        return shared.contains(key);
    }

}

