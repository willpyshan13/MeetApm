//package com.android.okhttpsample.aop;
//
//import android.util.Log;
//
//import com.meiyou.common.apm.XLogging;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//
//import okhttp3.OkHttpClient;
//
//@Aspect
//public class AspectTest {
//
//    private static final String TAG = "AspectTest";
//
//    @Before("execution(* android.app.Activity.on**(..))")
//    public void onActivityMethodBefore(JoinPoint joinPoint) throws Throwable {
//        String key = joinPoint.getSignature().toString();
//        Log.d(TAG, "onActivityMethodBefore: " + key);
//    }
//
//
////    @Before("execution(* okhttp3.OkHttpClient.newCall(..))")
////    public void onNewCall(JoinPoint joinPoint) throws Throwable {
////        OkHttpClient okHttpClient = (OkHttpClient) joinPoint.getThis();
////        okHttpClient = XLogging.install(okHttpClient, XLogging.Level.BODY);
//////        return RealCall.newRealCall(this, request, false /* for web socket */);
////        String args = joinPoint.getArgs().toString();
////        String kind = joinPoint.getKind();
////        String thisStr = joinPoint.getThis().toString();
////        String target = joinPoint.getTarget().toString();
////        String key = joinPoint.getSignature().toString();
////        String format = String.format("%s,%s, %s,%s,%s", args, kind, thisStr, target, key);
////        Log.d(TAG, "onActivityMethodBefore: " + format);
////    }
//
////    @Around("call(* okhttp3.OkHttpClient.newCall(..))")
////    public Object onNewCallPre(ProceedingJoinPoint joinPoint) throws Throwable {
//////        OkHttpClient okHttpClient = (OkHttpClient) joinPoint.getThis();
////        OkHttpClient okHttpClient = (OkHttpClient) joinPoint.getTarget();
////        okHttpClient = XLogging.install(okHttpClient, XLogging.Level.BODY);
////
//////        joinPoint.
//////       return okHttpClient.newCall((Request) joinPoint.getArgs()[0]);
////        OkHttpClient target = (OkHttpClient) joinPoint.getTarget();
////
//////        target.networkInterceptors().add(new XLoggingInterceptor(XLogging.Level.BODY));
//////        target.dns()=new XDns(target.dns());
//////         joinPoint.set$AroundClosure(new AroundClosure() {
//////             @Override
//////             public Object run(Object[] args) throws Throwable {
//////                 return null;
//////             }
//////         });
//////        
////
//////        return joinPoint.proceed(joinPoint.getArgs());
////        long start = System.currentTimeMillis();
////        Object result = joinPoint.proceed();
////        long time = System.currentTimeMillis() - start;
////        Log.d(TAG, "onNewCallPre: " + time);
////        
////        return result;
////
////    }
//
//    @Around("call(okhttp3.OkHttpClient.new())")
//    public Object onNewClient(ProceedingJoinPoint joinPoint) throws Throwable {
//        Log.d(TAG, "onActivityMethodBefore: " + joinPoint.getSignature().toString());
//
////        OkHttpClient okHttpClient = (OkHttpClient) joinPoint.getThis();
//        OkHttpClient okHttpClient = (OkHttpClient) joinPoint.proceed();
//        okHttpClient = XLogging.install(okHttpClient, XLogging.Level.BODY);
//        return okHttpClient;
////        joinPoint.
////       return okHttpClient.newCall((Request) joinPoint.getArgs()[0]);
////        OkHttpClient target = (OkHttpClient) joinPoint.getTarget();
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
//
//    }
//
//
////    @Around("okhttp3.RealCall.new()")
////    public void onNewRealCall(ProceedingJoinPoint joinPoint) throws Throwable {
//////        OkHttpClient okHttpClient = (OkHttpClient) joinPoint.getThis();
//////        okHttpClient = XLogging.install(okHttpClient, XLogging.Level.BODY);
////        RealCall call= (RealCall) joinPoint.getThis();
////
//////        this.client = client;
//////        this.originalRequest = originalRequest;
//////        this.retryAndFollowUpInterceptor = new RetryAndFollowUpInterceptor(client);
////        
////        String key = joinPoint.getSignature().toString();
////        Log.d(TAG, "onActivityMethodBefore: " + key);
////    }
//}
