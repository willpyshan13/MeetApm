package com.meiyou.common.apm.net.http;

import android.os.Build;
import android.support.annotation.NonNull;

import com.meiyou.common.apm.controller.ApmAgent;
import com.meiyou.common.apm.proxy.ApmProxy;
import com.meiyou.common.apm.util.NetworkUtil;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 请求类
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 18/1/22
 */

public class RequestHelper {

    private static final String TAG = "RequestHelper";
    private static String userAgent;
    private static String myClient = "";

    public static void doGet(String url, Callback callback) {
        boolean hasNetWork = NetworkUtil.hasNetWork(ApmAgent.getContext());
        if (!hasNetWork) {
            return;
        }

        OkHttpClient client = new OkHttpClient();
//         new OkHttpClient.Builder()
//                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);

    }

    public static void doPost(String url, String content, Callback callback) {
        doPost(url, content, new PostConfig(), callback);
    }

    public static void doPost(String url, String content, @NonNull PostConfig config, Callback callback) {
        boolean hasNetWork = NetworkUtil.hasNetWork(ApmAgent.getContext());
        if (!hasNetWork) {
            return;
        }

        final MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();
        OkHttpClient.Builder builder = client.newBuilder();

        if (config.isGzip) {
            builder.addInterceptor(new GzipRequestInterceptor());
        }
        client = builder.build();

        RequestBody body = RequestBody.create(JSON, content);
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder
                .url(url)
                .header("User-Agent", getUserAgent())
                .header("myclient", getMyClient())
                .post(body);
        if (config.isGzip) {
            requestBuilder.header("Content-Encoding", "gzip");
        }

        Request request = requestBuilder.build();

        client.newCall(request).enqueue(callback);

//        new Callback() {
//            @Override
//            public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                // manage failure
//            }
//
//            @Override
//            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
//                // Manage response
////                Log.i(TAG, response.body().string());
//            }
//        });
    }

    /**
     * 渠道号： "3000020032030"
     *
     * @return
     */
    public static String getMyClient() {
        return ApmProxy.getMyClient();
//        Context context = ApmAgent.getContext();
//        if (!TextUtils.isEmpty(myClient)) {
//            return myClient;
//        }
//        if (TextUtils.isEmpty(myClient)) {
//            myClient = (String) SPUtils.getSp(context, "myclient", "");
//        }
//        if (TextUtils.isEmpty(myClient)) {
//            myClient = getFullClientIdNumFromApk(context, "cztchannel");
//            SPUtils.setSP(context, "myclient", myClient);
//        }
//        
//        return myClient;
    }

    public static String getUserAgent() {
        return "Meetyou APM Agent/2.7.1(Android " + Build.VERSION.RELEASE + ")";
    }

//    /**
//     * 从apk中获取版本信息
//     *
//     * @param context
//     * @param channelKey
//     * @return
//     */
//    private static String getFullClientIdNumFromApk(Context context, String channelKey) {
//        //从apk包中获取
//        ApplicationInfo appinfo = context.getApplicationInfo();
//        String sourceDir = appinfo.sourceDir;
//        //默认放在meta-inf/里， 所以需要再拼接一下
//        String key = "META-INF/" + channelKey;
//        String ret = "";
//        ZipFile zipfile = null;
//        try {
//            zipfile = new ZipFile(sourceDir);
//            Enumeration<?> entries = zipfile.entries();
//            while (entries.hasMoreElements()) {
//                ZipEntry entry = ((ZipEntry) entries.nextElement());
//                String entryName = entry.getName();
//                if (entryName.startsWith(key)) {
//                    ret = entryName;
//                    break;
//                }
//            }
//        } catch (FileNotFoundException e) {
//            //  e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (zipfile != null) {
//                try {
//                    zipfile.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        String[] split = ret.split("_");
//        String channel = "";
//        if (split.length >= 2) {
//            channel = ret.substring(split[0].length() + 1);
//           /* if (channel.length() > 10) {
//                channel = channel.substring(8, 11);
//            }*/
//        }
//
//        if (TextUtils.isEmpty(channel)) {
//            //默认值
//            channel = getDefaultClient(context);
//        }
//        return channel;
//    }
//
//    /**
//     * 获取默认 client
//     *
//     * @param context
//     * @return
//     */
//    private static String getDefaultClient(Context context) {
//        //默认值
//        String mDefaultChannel = "0130620111100000";
//        try {
//
//            String versionName = getVersionName(context);
//            //5.5;5.5.1;
//            if (versionName.length() <= 3) {
//                versionName += ".0";
//            }
//            String[] array = versionName.split("\\.");
//            StringBuilder stringBuilder = new StringBuilder();
//            for (String value : array) {
//                stringBuilder.append(value);
//            }
//            //0130556111100000
//            mDefaultChannel = "0130" + stringBuilder.toString() + "111100000";
//
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//        return mDefaultChannel;
//    }
//
//
//    public static PackageInfo getPackageInfo(Context context) {
//        PackageManager pm = context.getPackageManager();
//        try {
//            return pm.getPackageInfo(context.getPackageName(), 0);
//        } catch (PackageManager.NameNotFoundException e) {
//            LogUtils.e(e.getLocalizedMessage());
//        }
//        return new PackageInfo();
//    }
//
//    private static String sVersionName;
//
//    public static String getVersionName(Context context) {
//        if (sVersionName == null) {
//            sVersionName = getPackageInfo(context).versionName;
//        }
//        return sVersionName;
//    }


}
