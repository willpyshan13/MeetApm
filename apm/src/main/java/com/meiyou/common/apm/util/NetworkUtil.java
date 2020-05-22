package com.meiyou.common.apm.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public final class NetworkUtil {
    /**
     * Current network is LTE
     */
    public static final int NETWORK_TYPE_LTE = 13;
    /**
     * 缓存，避免变化
     */
    private static int netType = 0;

    /**
     * 获取IP地址
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.INTERNET"/>}</p>
     *
     * @param useIPv4 是否用IPv4
     * @return IP地址
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            for (Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces(); nis.hasMoreElements(); ) {
                NetworkInterface ni = nis.nextElement();
                // 防止小米手机返回10.0.2.15
                if (!ni.isUp()) continue;
                for (Enumeration<InetAddress> addresses = ni.getInetAddresses(); addresses.hasMoreElements(); ) {
                    InetAddress inetAddress = addresses.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        String hostAddress = inetAddress.getHostAddress();
                        boolean isIPv4 = hostAddress.indexOf(':') < 0;
                        if (useIPv4) {
                            if (isIPv4) return hostAddress;
                        } else {
                            if (!isIPv4) {
                                int index = hostAddress.indexOf('%');
                                return index < 0 ? hostAddress.toUpperCase() : hostAddress.substring(0, index)
                                                                                          .toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static int getNetTypeDes(Context context) {
        if (netType != 0) {
            return netType;
        }
        
        try {
            if (isWifi(context)) {
                //Log.e("网络类型:", "wifi");
                netType = 1;
            } else {
                TelephonyManager telmng = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                /**
                 * 获取数据连接状态
                 *
                 * DATA_CONNECTED 数据连接状态：已连接
                 * DATA_CONNECTING 数据连接状态：正在连接
                 * DATA_DISCONNECTED 数据连接状态：断开
                 * DATA_SUSPENDED 数据连接状态：暂停
                 */
//                int dataState = telmng.getDataState();
//				if(dataState != TelephonyManager.DATA_DISCONNECTED){
                //数据连接无断开的情况
                switch (telmng.getNetworkType()) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                        //Log.e("网络类型:", "2G");
                        netType = 2;
                        break;

                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                        //Log.e("网络类型:", "3G");
                        netType = 3;
                        break;
                    case NETWORK_TYPE_LTE:
                        //Log.e("网络类型:", "4G");
                        netType = 4;
                        break;
                    default:
                        //Log.e("网络类型:", "无网络连接...");
                        netType = 0;
                }

//				}else {
//					//Log.e("网络类型:", "无网络连接...");
//					return NETWORK_CLASS_UNKNOWN;
//				}

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return netType;
    }

    /**
     * 是否WIFI网络
     *
     * @param ctx
     * @return
     */
    public static boolean isWifi(final Context ctx) {
        ConnectivityManager conManager = (ConnectivityManager) ctx.getApplicationContext()
                                                                  .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo ni = conManager.getActiveNetworkInfo();
        if (ni != null && ni.getTypeName() != null) {
            if (ni.getTypeName().compareToIgnoreCase("WIFI") != 0) {
                return false;
            }
            return true;

        } else {
            return false;
        }

    }

        public static boolean hasNetWork(Context ctx) {
            try {
                return getNetworkConnectionStatus(ctx);
            } catch (Exception e) {
                return false;
            }
        }


    /**
     * 是否有网络
     *
     * @param context
     * @return
     */
    private static boolean getNetworkConnectionStatus(Context context) {
        try {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager == null) {
                //Log.w(TAG, "getNetworkConnectionStatus,1,false");
                return false;
            }

            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info == null) {
                //Log.w(TAG, "getNetworkConnectionStatus,2,false");
                return false;
            }

            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm == null) {
                //Log.w(TAG, "getNetworkConnectionStatus,3,false");
                return false;
            }

            //Log.w(TAG, "getNetworkConnectionStatus,true");
//Log.w(TAG, "getNetworkConnectionStatus,false");
            return tm.getDataState() == TelephonyManager.DATA_CONNECTED || tm.getDataState() == TelephonyManager.DATA_ACTIVITY_NONE;
        } catch (Exception e) {
            return true;
        }
    }
}
