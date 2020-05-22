package com.meiyou.common.apm.okhttp;

import android.content.Context;

import com.meiyou.common.apm.XLogging;
import com.meiyou.common.apm.controller.ApmAgent;
import com.meiyou.common.apm.controller.Config;
import com.meiyou.common.apm.core.ApmHelper;
import com.meiyou.common.apm.net.bean.MetricsBean;
import com.meiyou.common.apm.okhttp.internal.XDns;
import com.meiyou.common.apm.okhttp.internal.XSSLSocket;
import com.meiyou.common.apm.okhttp.internal.XSocket;
import com.meiyou.common.apm.util.HttpMethodUtils;
import com.meiyou.common.apm.util.NetworkUtil;

import java.io.IOException;
import java.net.Socket;

import okhttp3.Connection;
import okhttp3.Dns;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;


/**
 * Provides easy integration with <a href="http://square.github.io/okhttp/">OkHttp</a> 3.x by way of
 * the new <a href="https://github.com/square/okhttp/wiki/Interceptors">Interceptor</a> system.
 * To use:
 * <pre>
 *   OkHttpClient client = new OkHttpClient.Builder()
 *       .addNetworkInterceptor(new XLoggingInterceptor())
 *       .build();
 * </pre>
 */
public class XLoggingInterceptor implements Interceptor {

    private XLogging.Level level;

    public XLoggingInterceptor(XLogging.Level level) {
        this.level = level;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long startTime = System.currentTimeMillis();
        Response response = null;
        request = fixRequest(request);
        HttpUrl url = request.url();
        String urlStr = url.toString();

        //connect 数据
//            Connection connection = chain.connection();
        Connection connection = chain.connection();
        response = chain.proceed(request);
        try {
            Context context = ApmAgent.getContext();
            //未开启或者需要过滤掉
            if (!Config.enable || filter(urlStr)) {
                return response;
            }
            //response 不会为空， 为空在RealChain会抛出 Exception

            //收集中
            long endTime = System.currentTimeMillis();

            MetricsBean metricsBean = new MetricsBean();
            metricsBean.startTime = startTime;
            metricsBean.endTime = endTime;
            metricsBean.host = url.host();
            metricsBean.url = url.toString();

            //request
            RequestBody requestBody = request.body();
            boolean hasRequestBody = requestBody != null;
            if (hasRequestBody) {
                MediaType contentType = requestBody.contentType();
                if (contentType != null) {
                    metricsBean.contentType = (contentType.toString());
                }
                metricsBean.requestBodyLength = requestBody.contentLength();
            }
            //response
            ResponseBody body = response.body();

            if (body != null) {

                metricsBean.responseBodyLength = getResponseBodyLength(body);

                long receivedResponseAtMillis = response.receivedResponseAtMillis();
                metricsBean.receivedResponseAtMillis = receivedResponseAtMillis;
                metricsBean.sentRequestAtMillis = response.sentRequestAtMillis();
                //不准，数据通常跟 响应时间一样
                metricsBean.firstPkg = receivedResponseAtMillis - startTime;
                metricsBean.totalByte = metricsBean.responseBodyLength;

            }
            metricsBean.httpCode = response.code();
            metricsBean.netType = NetworkUtil.getNetTypeDes(context);
            metricsBean.method = HttpMethodUtils.getMethod(request.method());
            //DNS, TCP,SSL
            if (connection != null) {
                Dns dns = connection.route().address().dns();
                if (dns instanceof XDns) {
                    metricsBean.dns = ((XDns) (dns)).getDns();
                }
                Socket socket = connection.socket();
                if (socket instanceof XSocket) {
                    XSocket xSocket = ((XSocket) socket);
                    metricsBean.tcp = xSocket.getTcp();
                    metricsBean.ip = xSocket.getInetAddress().getHostAddress();
                } else if (socket instanceof XSSLSocket) {
                    XSSLSocket xsslSocket = ((XSSLSocket) socket);
                    metricsBean.tcp = xsslSocket.getTcp();
                    metricsBean.ssl = xsslSocket.getSsl();
                    metricsBean.ip = xsslSocket.getInetAddress().getHostAddress();
                }
            }

            //总响应时间：
            metricsBean.totalMills = metricsBean.dns + metricsBean.tcp + metricsBean.ssl
                    + metricsBean.firstPkg;

            //入库
            ApmHelper.getInstance().onEvent(metricsBean);

        } catch (Exception e) {
            if (Config.debug) {
                e.printStackTrace();
//                throw e;
            }

        }

        return response;
    }

    /**
     * fixme 还是不准 ，带优化
     *
     * @return
     */
    private long getResponseBodyLength(ResponseBody body) {
        //gzip 请求会返回 -1
        long bodyLength = body.contentLength();
        //处理 ==-1 情况，需要自己解析
        if (bodyLength == -1) {
            try {
//                https://github.com/square/okhttp/issues/2869
//https://stackoverflow.com/questions/47386132/recording-okhttp-response-body-size-without-buffering-the-whole-thing?rq=1
//https://github.com/square/okhttp/issues/3309
// 测试发现在 弱网下，request方法会概率报EOFException。导致Exception很多，暂时关闭。
                BufferedSource source = body.source();
                source.request(Long.MAX_VALUE); // Buffer the entire body.
                Buffer buffer = source.buffer();
                bodyLength = buffer.size();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bodyLength;
    }

    /**
     * 拦截request。
     *
     * @param request
     * @return
     */
    private Request fixRequest(Request request) {
        //处理 美柚DNS url  http://101.132.24.156/d/d.swf 导致的
        // java.io.IOException: unexpected end of stream on okhttp3.Address
        //More See: https://github.com/square/okhttp/issues/2738
        //暂时不开启
//        String s = request.url().toString();
//        if (s.endsWith("/d/d.swf")) {
//            request = request.newBuilder()
//                             .header("Connection", "close")
//                             .build();
//        }
        return request;
    }

    /**
     * 过滤Url
     *
     * @param url
     * @return true : url需要过滤掉
     */
    private boolean filter(String url) {
        //filter
        for (String filter : Config.filterList) {
            if (url.contains(filter)) {
                return true;
            }
        }
        return false;
    }

//    private void update(HttpTransaction transaction) {
//        LogUtil.showLog(transaction, level);
//    }
}
