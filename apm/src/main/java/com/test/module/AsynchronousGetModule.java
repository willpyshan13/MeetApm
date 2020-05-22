package com.test.module;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ali on 4/10/17.
 */

public class AsynchronousGetModule {
    private static final String TAG = "AsynchronousGet: ";

    public static void executeAsynchronousGet() {

        OkHttpClient client = new OkHttpClient();
//        OkHttpClient client = new OkHttpClient();
//        client = XLogging.install(client, XLogging.Level.BODY);

        Request request = new Request.Builder()
                .url("https://www.baidu.com")
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: ");
                // manage failure !
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    // manage error
                    Log.e(TAG, "Unexpected code " + response);
                    return;
                }

                Headers responseHeaders = response.headers();
                // show response headers
                for (int i = 0; i < responseHeaders.size(); i++) {
                    Log.i(TAG, responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                // show body content
                Log.i(TAG, response.body().string());

            }
        });
    }
    
    private void test(Context context){
        Intent intent = context.registerReceiver(null, new IntentFilter());
    }
}
