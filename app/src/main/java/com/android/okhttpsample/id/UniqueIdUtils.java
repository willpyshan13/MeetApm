//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.android.okhttpsample.id;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.preference.PreferenceManager;
import android.provider.Settings.System;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class UniqueIdUtils {
    private static final String TAG = "UniqueIdUtils";
    static Map<String, String> result = new HashMap();

    public UniqueIdUtils() {
    }

    public static String getDeviceInfo(Context context, UniqueIdUtils.DEVICES_INFO info) {
        return (String) getDeviceInfo(context).get(info.name());
    }

    public static Map<String, String> getDeviceInfo(Context context) {
//        if (!result.keySet().isEmpty()) {
//            return result;
//        } else {
        result = fetch(context);

        String serial;
        try {
            serial = getIMEI(context);
            result.put(UniqueIdUtils.DEVICES_INFO.IMEI.name(), serial);
        } catch (UniqueException var5) {
            var5.printStackTrace();
        }

        try {
            serial = getMacId(context);
            result.put(UniqueIdUtils.DEVICES_INFO.MAC.name(), serial);
        } catch (UniqueException var4) {
            var4.printStackTrace();
        }

        try {
            serial = getAndroidId(context);
            result.put(UniqueIdUtils.DEVICES_INFO.ANDROID_ID.name(), serial);
        } catch (UniqueException var3) {
            var3.printStackTrace();
        }

        try {
            serial = getSerialNumber(context);
            result.put(UniqueIdUtils.DEVICES_INFO.SERIAL.name(), serial);
        } catch (UniqueException var2) {
            var2.printStackTrace();
        }

        if (result.keySet().isEmpty()) {
            result.put(UniqueIdUtils.DEVICES_INFO.ANDROID_ID.name(), getUUID());
        }

        save(context, result);
        return result;
//        }
    }

    private static void save(Context context, Map<String, String> result) {
        if (result != null && !result.isEmpty()) {
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            Iterator var3 = result.keySet().iterator();

            while (var3.hasNext()) {
                String key = (String) var3.next();
                String value = (String) result.get(key);
//                LogUtils.d("UniqueIdUtils", "save key:" + key + "-->value:" + value, new Object[0]);
                sp.edit().putString(key, (String) result.get(key)).apply();
            }

        }
    }

    private static Map<String, String> fetch(Context context) {
        Map<String, String> map = new HashMap();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        UniqueIdUtils.DEVICES_INFO[] var3 = UniqueIdUtils.DEVICES_INFO.values();
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            UniqueIdUtils.DEVICES_INFO info = var3[var5];
            String value = sp.getString(info.name(), "");
            map.put(info.name(), value);
        }

        return map;
    }

    @SuppressLint("MissingPermission")
    private static String getIMEI(Context context) throws UniqueException {
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            return tm.getDeviceId().toLowerCase();
        } catch (Exception var2) {
            throw new UniqueException(var2);
        }
    }

    private static boolean validMac(String address) {
        return StringUtils.isNotEmpty(address) && !StringUtils
                .equals(address, "00:00:00:00:00:00") && !StringUtils
                .equals(address, "ff:ff:ff:ff:ff:ff") && !StringUtils.equals(address, "02:00:00:00:00:00");
    }

    private static String getMacId(Context context) throws UniqueException {
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            String address = info.getMacAddress();
            if (validMac(address)) {
                throw new UniqueException("mac addr is empty");
            } else {
                return address.toLowerCase();
            }
        } catch (Exception var4) {
            throw new UniqueException(var4);
        }
    }

    private static String getAndroidId(Context context) throws UniqueException {
        try {
            String androidId = System.getString(context.getContentResolver(), "android_id");
            if (!StringUtils.isEmpty(androidId) && !androidId.equals("9774d56d682e549c")) {
                return androidId.toLowerCase();
            } else {
                throw new UniqueException("bug androidId " + androidId);
            }
        } catch (Exception var2) {
            throw new UniqueException(var2);
        }
    }

    private static String getSerialNumber(Context context) throws UniqueException {
        try {
            String result = "35" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.CPU_ABI
                    .length() % 10 + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + VERSION.RELEASE
                    .length() % 10 + Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 + Build.MODEL
                    .length() % 10 + Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 + Build.TYPE
                    .length() % 10 + Build.USER.length() % 10;
            return result.toLowerCase();
        } catch (Exception var2) {
            throw new UniqueException(var2);
        }
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().toLowerCase();
    }

    public static enum DEVICES_INFO {
        IMEI,
        MAC,
        SERIAL,
        ANDROID_ID;

//        private static String marshmallowMacAddress;

        private DEVICES_INFO() {
        }

    }

    /***
     * 获取Mac  新的方式
     * @param context
     * @return
     */
    public static String getAdresseMAC(Context context) {
        WifiManager wifiMan = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInf = wifiMan.getConnectionInfo();

        if (wifiInf != null && marshmallowMacAddress.equals(wifiInf.getMacAddress())) {
            String result = null;
            try {
                result = getAdressMacByInterface();
                if (result != null) {
                    return result;
                } else {
                    result = getAddressMacByFile(wifiMan);
                    return result;
                }
            } catch (IOException e) {
                Log.e("MobileAccess", "Erreur lecture propriete Adresse MAC");
            } catch (Exception e) {
                Log.e("MobileAcces", "Erreur lecture propriete Adresse MAC ");
            }
        } else {
            if (wifiInf != null && wifiInf.getMacAddress() != null) {
                return wifiInf.getMacAddress();
            } else {
                return "";
            }
        }
        return marshmallowMacAddress;
    }

    private static String getAdressMacByInterface() {
        try {
            List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface nif : all) {
                if (nif.getName().equalsIgnoreCase("wlan0")) {
                    byte[] macBytes = nif.getHardwareAddress();
                    if (macBytes == null) {
                        return "";
                    }

                    StringBuilder res1 = new StringBuilder();
                    for (byte b : macBytes) {
                        res1.append(String.format("%02X:", b));
                    }

                    if (res1.length() > 0) {
                        res1.deleteCharAt(res1.length() - 1);
                    }
                    return res1.toString();
                }
            }

        } catch (Exception e) {
            Log.e("MobileAcces", "Erreur lecture propriete Adresse MAC ");
        }
        return null;
    }


    private static final String marshmallowMacAddress = "02:00:00:00:00:00";
    private static final String fileAddressMac = "/sys/class/net/wlan0/address";

    private static String getAddressMacByFile(WifiManager wifiMan) throws Exception {
        String ret;
        int wifiState = wifiMan.getWifiState();

        wifiMan.setWifiEnabled(true);
        File fl = new File(fileAddressMac);
        FileInputStream fin = new FileInputStream(fl);
        ret = crunchifyGetStringFromStream(fin);
        fin.close();

        boolean enabled = WifiManager.WIFI_STATE_ENABLED == wifiState;
        wifiMan.setWifiEnabled(enabled);
        return ret;
    }

    private static String crunchifyGetStringFromStream(InputStream crunchifyStream) throws IOException {
        if (crunchifyStream != null) {
            Writer crunchifyWriter = new StringWriter();

            char[] crunchifyBuffer = new char[2048];
            try {
                Reader crunchifyReader = new BufferedReader(new InputStreamReader(crunchifyStream, "UTF-8"));
                int counter;
                while ((counter = crunchifyReader.read(crunchifyBuffer)) != -1) {
                    crunchifyWriter.write(crunchifyBuffer, 0, counter);
                }
            } finally {
                crunchifyStream.close();
            }
            return crunchifyWriter.toString();
        } else {
            return "No Contents";
        }
    }

}
