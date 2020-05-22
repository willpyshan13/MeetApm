package com.android.okhttpsample.gps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;

import com.android.okhttpsample.ui.MyApp;

import java.util.HashSet;
import java.util.Set;

/**
 * 过滤上传过 图片GPS
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/12/5
 */

public class GpsPref extends PrefBase {
    /**
     * Preferences的存储文件名称
     */
    private final static String SHARED_PREFERENCE = "pref_door";
    private static String key_gps_photo = "key_gps_photo";

    private GpsPref(@NonNull Context context) {
        super(context);
        setPrefName(SHARED_PREFERENCE);
    }

    @SuppressLint("StaticFieldLeak")
    private static GpsPref instance;

    public static GpsPref getInstance() {
        if (instance == null) {
            instance = new GpsPref(MyApp.getContext());
        }
        return instance;
    }

    public void putString(String str) {
        Set<String> set = this.shared.getStringSet(key_gps_photo, new HashSet<String>());
        set.add(str);
        this.editor.putStringSet(key_gps_photo, set);
    }

    public boolean isContain(String str) {
        Set<String> set = this.shared.getStringSet(key_gps_photo, new HashSet<String>());
        return set.contains(str);
    }

}
