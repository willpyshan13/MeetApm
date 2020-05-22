package com.android.okhttpsample.http;

import android.content.Context;
import android.text.TextUtils;

import com.android.okhttpsample.ui.MyApp;

import org.json.JSONArray;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;

/**
 * 对 post 的数据进行一个 AES 加密
 * http://qa.meiyou.com/index.php?m=task&f=view&task=611
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/11/29
 */

public class AesRequestInterceptor implements Interceptor {

    private Request newRequest(Request request) throws Exception {
        RequestBody newRequest = encrypt(request.body());
        String headKey = "Content-Encoding";
        String encoding = request.header(headKey);
        if (TextUtils.isEmpty(encoding)) {
            encoding = "elder";
        } else {
            encoding = encoding + ",elder";
        }
//        encoding= "gzip,elder";
        Headers headers = request.headers()
                                 .newBuilder()
                                 .set(headKey, encoding)
                                 .build();
        Request result = request.newBuilder()
                                .method(request.method(), newRequest)
                                .headers(headers)
                                .build();
        return result;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Context context = MyApp.getContext();
        boolean enable_aes_host = true;
//        DoorHelper.getStatus(context, "enable_aes_host");
        if (!enable_aes_host) {
            return chain.proceed(request);
        }

        try {
//            JSONObject aes_host = DoorHelper.getValue(context, "enable_aes_host");
//            JSONArray list = aes_host.optJSONArray("list");
            String[] temp={"test-diaries.seeyouyima.com"};
            JSONArray list=new JSONArray(Arrays.asList(temp));
            String host = request.url().host();
            boolean isMatch = false;
            for (int i = 0; i < list.length(); i++) {
                String url = (String) list.opt(i);
                if (host.contains(url)) {
                    isMatch = true;
                    break;
                }
            }

            if ("POST".equals(request.method()) && isMatch) {
                Request newRequest = newRequest(request);
                return chain.proceed(newRequest);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return chain.proceed(request);
    }

    /**
     * 加密
     *
     * @param body
     * @return
     */
    private RequestBody encrypt(RequestBody body) throws Exception {
        BufferedSink sink = new Buffer();
        try {
            body.writeTo(sink);
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = sink.buffer().readByteArray();
        
//        String content = sink.buffer().readUtf8();
        AESOperator aes = new AESOperator();
        byte[] encrypt = aes.encrypt(bytes);
        RequestBody requestBody = RequestBody.create(body.contentType(), encrypt);
        return requestBody;
    }

}
