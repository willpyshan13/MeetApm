package com.meiyou.common.apm.aop;

import android.content.Context;

import com.meiyou.common.apm.Constant;
import com.meiyou.common.apm.XLogging;
import com.meiyou.common.apm.controller.ApmAgent;
import com.meiyou.common.apm.controller.Config;
import com.meiyou.common.apm.net.ApmConfigManager;
import com.meiyou.common.apm.net.ApmSyncManager;
import com.meiyou.common.apm.okhttp.XLoggingInterceptor;
import com.meiyou.common.apm.okhttp.internal.XDns;
import com.meiyou.common.apm.okhttp.internal.XSSLSocketFactory;
import com.meiyou.common.apm.okhttp.internal.XSocketFactory;
import com.meiyou.common.apm.util.LogUtils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;

import okhttp3.Dns;
import okhttp3.OkHttpClient;

@Aspect
public class AspectApm {

    private static final String TAG = "AspectTest";

//    @Before("execution(* android.app.Activity.on**(..))")
//    public void onActivityMethodBefore(JoinPoint joinPoint) throws Throwable {
//        String key = joinPoint.getSignature().toString();
//        Log.d(TAG, "onActivityMethodBefore: " + key);
//    }


//    @Before("execution(* okhttp3.OkHttpClient.newCall(..))")
//    public void onNewCall(JoinPoint joinPoint) throws Throwable {
//        OkHttpClient okHttpClient = (OkHttpClient) joinPoint.getThis();
//        okHttpClient = XLogging.install(okHttpClient, XLogging.Level.BODY);
////        return RealCall.newRealCall(this, request, false /* for web socket */);
//        String args = joinPoint.getArgs().toString();
//        String kind = joinPoint.getKind();
//        String thisStr = joinPoint.getThis().toString();
//        String target = joinPoint.getTarget().toString();
//        String key = joinPoint.getSignature().toString();
//        String format = String.format("%s,%s, %s,%s,%s", args, kind, thisStr, target, key);
//        Log.d(TAG, "onActivityMethodBefore: " + format);
//    }

//    @Around("call(* okhttp3.OkHttpClient.newCall(..))")
//    public Object onNewCallPre(ProceedingJoinPoint joinPoint) throws Throwable {
////        OkHttpClient okHttpClient = (OkHttpClient) joinPoint.getThis();
//        OkHttpClient okHttpClient = (OkHttpClient) joinPoint.getTarget();
//        okHttpClient = XLogging.install(okHttpClient, XLogging.Level.BODY);
//
////        joinPoint.
////       return okHttpClient.newCall((Request) joinPoint.getArgs()[0]);
//        OkHttpClient target = (OkHttpClient) joinPoint.getTarget();
//
////        target.networkInterceptors().add(new XLoggingInterceptor(XLogging.Level.BODY));
////        target.dns()=new XDns(target.dns());
////         joinPoint.set$AroundClosure(new AroundClosure() {
////             @Override
////             public Object run(Object[] args) throws Throwable {
////                 return null;
////             }
////         });
////        
//
////        return joinPoint.proceed(joinPoint.getArgs());
//        long start = System.currentTimeMillis();
//        Object result = joinPoint.proceed();
//        long time = System.currentTimeMillis() - start;
//        Log.d(TAG, "onNewCallPre: " + time);
//        
//        return result;
//
//    }

    //拦截写法：    
    // OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
    // OkHttpClient client = clientBuilder.build();
    @Around("call(okhttp3.OkHttpClient.Builder.new())")
    public Object onNewBuilder(ProceedingJoinPoint joinPoint) {
//        LogUtils.d("onNewBuilder");
        OkHttpClient.Builder builder = null;
        try {
            builder = (OkHttpClient.Builder) joinPoint.proceed();
            if (Config.enable) {
                builder.addNetworkInterceptor(new XLoggingInterceptor(XLogging.Level.BODY));
                builder.dns(new XDns(Dns.SYSTEM));
                builder.socketFactory(new XSocketFactory(SocketFactory.getDefault()));
                builder.sslSocketFactory(new XSSLSocketFactory(SSLContext.getDefault()
                                                                         .getSocketFactory()));
                //fixme  总响应时间：收集： Application interceptions
//            builder.addInterceptor(new Interceptor() {
//            })

            }

        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return builder;
    }

//    /**
//     * OkHttpClient.new()之后，加入拦截器。收集数据
//     *
//     * @param joinPoint
//     * @return
//     * @throws Throwable
//     */
//    @Around("call(okhttp3.OkHttpClient.new())")
//    public Object onNewClient(ProceedingJoinPoint joinPoint) {
////        LogUtils.d(TAG, "onActivityMethodBefore: " + joinPoint.getSignature().toString());
//
//        OkHttpClient okHttpClient = null;
//        try {
//            okHttpClient = (OkHttpClient) joinPoint.proceed();
//            //加入 AOP拦截
//            okHttpClient = XLogging.install(okHttpClient, XLogging.Level.BODY);
//        } catch (Throwable throwable) {
//            throwable.printStackTrace();
//        }
//        return okHttpClient;
//
//    }

    /**
     * ApmAgent.start()方法之后，初始化Apm
     *
     * @param joinPoint
     */
    @After("execution(* com.meiyou.common.apm.controller.ApmAgent.start(..))")
    public void onApmInit(JoinPoint joinPoint) {
        try {
            if (!ApmAgent.switchOn) return;
            LogUtils.d("onApmInit: success; version: " + Constant.VERSION);
            Object[] args = joinPoint.getArgs();
            Context context = (Context) args[0];
            Context contextInner = context.getApplicationContext();
            ApmConfigManager.getInstance().init(contextInner);
            //收集数据
            ApmSyncManager.getInstance().run(contextInner);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
