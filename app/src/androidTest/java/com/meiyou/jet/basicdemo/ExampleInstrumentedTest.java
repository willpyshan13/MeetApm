//package com.meiyou.jet.basicdemo;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.content.pm.ApplicationInfo;
//import android.content.res.AssetManager;
//import android.support.test.InstrumentationRegistry;
//import android.support.test.runner.AndroidJUnit4;
//import android.util.Log;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.io.ByteArrayOutputStream;
//import java.io.File;
//import java.io.InputStream;
//import java.util.Map;
//
//import static org.junit.Assert.assertEquals;
//
///**
// * Instrumented test, which will execute on an Android device.
// *
// * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
// */
//@RunWith(AndroidJUnit4.class)
//public class ExampleInstrumentedTest {
//    private static final String TAG = "ExampleInstrumentedTest";
//
//    @Test
//    public void useAppContext() throws Exception {
//        // Context of the app under test.
//        Context appContext = InstrumentationRegistry.getTargetContext();
//        String packageName = appContext.getPackageName();
//        System.out.println(packageName);
//
//        File filesDir = appContext.getFilesDir();
//        Log.d(TAG, "useAppContext: fileDir:" + filesDir.getAbsolutePath());
//
//        File cacheDir = appContext.getCacheDir();
//        Log.d(TAG, "useAppContext: getCacheFile: " + cacheDir.getAbsolutePath());
//
//
//        File externalCacheDir = appContext.getExternalCacheDir();
//        Log.d(TAG, "useAppContext: externalCacheDir: " + externalCacheDir.getAbsolutePath());
//
//        ApplicationInfo applicationInfo = appContext.getApplicationInfo();
//        Log.d(TAG, "useAppContext: applicationIFno: " + applicationInfo.toString());
//
//        AssetManager assetManager = appContext.getAssets();
//        InputStream open = assetManager.open("test.text");
//
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        int i = -1;
//        while ((i = open.read()) !=-1){
//            outputStream.write(i);
//        }
//
//        String s = outputStream.toString();
//
////        String s = open.toString();
//        Log.d(TAG, "useAppContext: assetManager:  " + s);
//
//
//        assertEquals("com.meiyou.jet.basicdemo", packageName);
//    }
//
//
//    private void testSp(Context appContext) {
//        SharedPreferences preferences = appContext.getSharedPreferences("test", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString("key1", "value1");
//        editor.putString("key2", "value2");
//        editor.commit();
//
//
//        Log.d(TAG, "useAppContext: ");
//
//        Map<String, ?> map = preferences.getAll();
//        for (String s : map.keySet()) {
//            Log.d(TAG, "preferences: key:" + s + " value:" + map.get(s));
//        }
//    }
//    
//    
//    public void changetButton(){
//        Context targetContext = InstrumentationRegistry.getTargetContext();
//
////        
//    }
//}
