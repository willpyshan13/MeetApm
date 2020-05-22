package com.meiyou.common.apm.util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.Random;
import java.util.UUID;

/**
 * 获取UUID
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 18/1/23
 */

public class UUIDUtils {

    public static String getDeviceUUID(Context context) {
        String uuid = loadDeviceUUID(context);
        if (TextUtils.isEmpty(uuid)) {
            uuid = buildDeviceUUID(context);
            saveDeviceUUID(context, uuid);
        }
        return uuid;
    }

    private static String buildDeviceUUID(Context context) {
        String androidId = DeviceUtils.getAndroidID(context);
        if ("9774d56d682e549c".equals(androidId)) {
            Random random = new Random();
            androidId = Integer.toHexString(random.nextInt())
                    + Integer.toHexString(random.nextInt())
                    + Integer.toHexString(random.nextInt());
        }
        return new UUID(androidId.hashCode(), DeviceUtils.getBuildInfo().hashCode()).toString();
    }

    private static void saveDeviceUUID(Context context, String uuid) {
        SPUtils.setSP(context,"uuid",uuid);
    }

    @Nullable
    private static String loadDeviceUUID(Context context) {
        return (String) SPUtils.getSp(context,"uuid","");
//        return context.getSharedPreferences("device_uuid", Context.MODE_PRIVATE)
//                      .getString("uuid", null);
    }

}
