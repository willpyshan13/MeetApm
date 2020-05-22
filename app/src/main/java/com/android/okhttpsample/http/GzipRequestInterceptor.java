//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.android.okhttpsample.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;

public class GzipRequestInterceptor implements Interceptor {
    public GzipRequestInterceptor() {
    }

    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        if(originalRequest.body() != null && originalRequest.header("Content-Encoding").equals( "gzip")) {
            Request compressedRequest = originalRequest.newBuilder().method(originalRequest.method(), this.forceContentLength(this.gzip(originalRequest.body()))).build();
            return chain.proceed(compressedRequest);
        } else {
            return chain.proceed(originalRequest);
        }
    }

    private RequestBody forceContentLength(final RequestBody requestBody) throws IOException {
        final Buffer buffer = new Buffer();
        requestBody.writeTo(buffer);
        return new RequestBody() {
            public MediaType contentType() {
                return requestBody.contentType();
            }

            public long contentLength() {
                return buffer.size();
            }

            public void writeTo(BufferedSink sink) throws IOException {
                sink.write(buffer.snapshot());
            }
        };
    }

    private RequestBody gzip(final RequestBody body) {
        RequestBody bodyNew = new RequestBody() {
            public MediaType contentType() {
                return body.contentType();
            }

            public long contentLength() {
                return -1L;
            }

            public void writeTo(BufferedSink sink) throws IOException {
                BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
                body.writeTo(gzipSink);
                gzipSink.close();
            }
        };
        RequestBody bodyReturn = bodyNew;

        try {
            if(bodyNew.contentLength() > body.contentLength()) {
                bodyReturn = body;
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return bodyReturn;
    }
}
