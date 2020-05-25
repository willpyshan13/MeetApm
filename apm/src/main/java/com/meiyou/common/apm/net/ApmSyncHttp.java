package com.meiyou.common.apm.net;

import android.content.Context;
import android.net.Uri;

import com.google.gson.Gson;
import com.meiyou.common.apm.Constant;
import com.meiyou.common.apm.controller.Config;
import com.meiyou.common.apm.net.factory.IApmSync;
import com.meiyou.common.apm.net.factory.OnSyncListener;
import com.meiyou.common.apm.net.http.PostConfig;
import com.meiyou.common.apm.net.http.RequestHelper;
import com.meiyou.common.apm.util.LogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * HTTP上传通道
 *
 */

public class ApmSyncHttp implements IApmSync {
    private static final String TAG = "ApmSyncHttp";

    @Override
    public void sync(Context mContext, final ArrayList<String[]> gaList, final OnSyncListener onSyncListener) {
        try {
            ArrayList<HashMap> list = new ArrayList<>();
            HashMap<String, Object> jsonObject = new HashMap<>();
            jsonObject.put("type", "networkMetrics");
            jsonObject.put("actions", gaList);
            list.add(jsonObject);
            if (Config.extra.size() > 1) {
                list.add(Config.extra);
            }
            String content = new Gson().toJson(list);

            String url = Constant.getHost() + "/stats/data";
            Uri.Builder builder = Uri.parse(url).buildUpon();
            builder.appendQueryParameter("version", Constant.VERSION);
            builder.appendQueryParameter("token", Config.token);
            String urlNew = builder.toString();
//            LogUtils.d(TAG,content);
            PostConfig postConfig = new PostConfig();
            postConfig.isGzip = true;
            RequestHelper.doPost(urlNew, content, postConfig, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    LogUtils.d(TAG, "apm同步失败，等待下一次");
                    ApmSyncManager.isUploading = false;
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    LogUtils.d(TAG, "apm同步成功: " + gaList.size());
                    if (response.code() != 500) {
                        onSyncListener.onSuccess();
                    }
                    ApmSyncManager.isUploading = false;
//                    gaDAO.deleteAll();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
