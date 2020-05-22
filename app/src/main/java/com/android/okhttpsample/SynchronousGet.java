package com.android.okhttpsample;

import android.util.Log;

import com.android.okhttpsample.mock.UrlMock;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ali on 4/10/17.
 */

public class SynchronousGet {
    private static final String TAG = "SynchronousGet";

    public static void executeSynchronousGet() {
//    final OkHttpClient client = new OkHttpClient();
        String url = UrlMock.mock();
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        final OkHttpClient client = clientBuilder.build();

        final Request request = new Request.Builder()
                .url(url)
                .build();
        //Need to be called from background thread otherwise network on main thread exception will be occurred

        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Response response = null;
                    response = client.newCall(request).execute();
                    if (!response.isSuccessful()) {
                        // manage error
                        Log.e(TAG, request.url() + " run: fail");
//                Log.e(TAG, "Unexpected code " + response);
                    }
                    Log.d(TAG, request.url() + " run: success");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                

//            // show body content
//            try {
//                Log.i(TAG, response.body().string());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            }
        }).start();


    }
}
