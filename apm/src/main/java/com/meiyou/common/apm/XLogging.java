package com.meiyou.common.apm;

import com.meiyou.common.apm.okhttp.XLoggingInterceptor;
import com.meiyou.common.apm.okhttp.internal.XDns;
import com.meiyou.common.apm.util.LogUtils;

import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

public class XLogging {

    public enum Level {

        /**
         * Logs request and response lines.
         * <p>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting HTTP/1.1 (3-byte body)
         *
         * <-- 200 OK (22ms, 6-byte body)
         * }</pre>
         */
        BASIC,

        /**
         * Logs request and response lines and their respective headers.
         * <p>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting HTTP/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * ==> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <== END HTTP
         * }</pre>
         */
        HEADERS,

        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         * <p>
         * <p>Example:
         * <pre>{@code
         * --> POST /greeting HTTP/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * ==> END GET
         *
         * <-- HTTP/1.1 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <== END HTTP
         * }</pre>
         */
        BODY
    }

    public static OkHttpClient install(OkHttpClient.Builder builder) {
        return install(builder.build());
    }

    public static OkHttpClient install(OkHttpClient.Builder builder, Level level) {
        return install(builder.build(), level);
    }

    public static OkHttpClient install(OkHttpClient client) {
        return install(client, Level.BASIC);
    }

    public static OkHttpClient install(OkHttpClient client, Level level) {
        if (isInstalled(client)) {
            LogUtils.i( "Already install XLogging!");
            return client;
        }
        OkHttpClient.Builder originBuilder = client.newBuilder();
        originBuilder.addNetworkInterceptor(new XLoggingInterceptor(level));
        originBuilder.dns(new XDns(client.dns()));
        //https 会失败, socketFactory 和SSLSocketFactory必须都设置。
//        originBuilder.socketFactory(new XSocketFactory(client.socketFactory()));
        //开启，运行失败
//        originBuilder.sslSocketFactory(new XSSLSocketFactory(client.sslSocketFactory()));
        return originBuilder.build();
    }

    

    /**
     * 检查是否安装过XLogging
     *
     * @param client OkHttpClient
     * @return boolean
     */
    private static boolean isInstalled(OkHttpClient client) {
        List<Interceptor> networkInterceptors = client.networkInterceptors();
        if (networkInterceptors == null) {
            return false;
        }
        for (Interceptor interceptor : networkInterceptors) {
            if (interceptor instanceof XLoggingInterceptor) {
                return true;
            }
        }
        return false;
    }


}
