package com.android.okhttpsample;

import android.util.Log;

import com.android.okhttpsample.mock.UrlMock;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 异步 测试
 * Created by ali on 4/10/17.
 */

public class AsynchronousGet {
    private static final String TAG = "AsynchronousGet: ";

//    public static final String URL = "http://101.132.24.156/d/d.swf";


    public static void runLoop() {
        //默认同步策略 :60S*3同步一次； 延迟1s
        int time = 1000;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                executeAsynchronousGet();
            }
        }, 1000, time);


    }


    public static void executeAsynchronousGet() {

        //默认创建client 方式
//        OkHttpClient client = new OkHttpClient();
//        client = XLogging.install(client, XLogging.Level.BODY);

        //build 创建 client 方式
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
//        clientBuilder.addNetworkInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                return chain.proceed(chain.request());
//            }
//        });
//        clientBuilder.addNetworkInterceptor(new XLoggingInterceptor(XLogging.Level.BODY));
        
        OkHttpClient client = clientBuilder.build();

        String URL = UrlMock.mock();
        Request request = new Request.Builder()
                .url(URL)
                .build();

        Call call = client.newCall(request);

        try {

            //弱网下 多次快速 执行这个，会崩溃
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG, call.request().url() + ": onFailure: ", e);
                    // manage failure !
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.e(TAG, call.request().url() + "  : onResponse : Success");
//                if (!response.isSuccessful()) {
//                    // manage error
//                    Log.e(TAG, "onResponse: fail");
////                    Log.e(TAG, "Unexpected code " + response);
//                    return;
//                }

//                Headers responseHeaders = response.headers();
//                // show response headers
//                for (int i = 0; i < responseHeaders.size(); i++) {
//                    Log.i(TAG, responseHeaders.name(i) + ": " + responseHeaders.value(i));
//                }
//
//                // show body content
//                Log.i(TAG, response.body().string());
//
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
