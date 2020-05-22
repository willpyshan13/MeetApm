package com.android.okhttpsample.gps;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;


import com.android.okhttpsample.ui.MyApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 通过图片的经纬度来完善用户的GPS信息：
 * <p>
 * http://qa.meiyou.com/index.php?m=task&f=view&task=615
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/12/5
 */

public class GpsPhotoController {

    private static final String TAG = "GpsPhotoController";
    private static final long DAY = DateConstant.DAY;
    private static GpsPhotoController instance;

    public static GpsPhotoController getInstance() {
        if (instance == null) {
            instance = new GpsPhotoController();
        }
        return instance;
    }


    public void init() {
        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getGps();
            }
        }, 15 * DateConstant.SECOND);
    }


    public List<GpsModel> getGps() {
        Context ctx = MyApp.getContext();
        List<GpsModel> result = new ArrayList<>();


        //可以手动指定获取的列
//        String[] columns = new String[]{MediaStore.Images.Media.DATE_ADDED,
//                MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID, MediaStore.Images.Media.TITLE, MediaStore.Images.Media.DISPLAY_NAME
//                , MediaStore.Images.Media.LATITUDE, MediaStore.Images.Media.LONGITUDE, MediaStore.Images.Media.DATE_TAKEN};
        ContentResolver contentResolver = ctx.getContentResolver();

        String dateAdded = MediaStore.Images.Media.DATE_ADDED;
        String latitude = MediaStore.Images.Media.LATITUDE;
        String longitude = MediaStore.Images.Media.LONGITUDE;
        String displayName = MediaStore.Images.Media.DISPLAY_NAME;

        //    Media.EXTERNAL_CONTENT_URI和Media.INTERNAL_CONTENT_URI
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        // 通用公试: sql = "select * from table where "+条件+" order by "+排序+" limit "+要显示多少条记录+" offset "+跳过多少条记录
        String selection = dateAdded + "> ? ";
        long earliest = (System.currentTimeMillis() - 15 * DAY)/1000;
        String[] selectionArgs = {earliest + ""};
        String sortOrder = dateAdded + " DESC limit 3";
        //获取指定列
        Cursor cursor = contentResolver.query(uri, null, selection, selectionArgs
        , sortOrder);
        if (cursor != null) {
            Map<String, String> map = null;

            while (cursor.moveToNext()) {
                String[] columnNames = cursor.getColumnNames();
                map = new HashMap<>();
                for (String colnmnName : columnNames) {
                    int columnIndex = cursor.getColumnIndex(colnmnName);
                    String columnValue = cursor.getString(columnIndex);
                    map.put(colnmnName, columnValue);
                }

                GpsModel gpsModel = new GpsModel();
                gpsModel.timestamp = map.get(dateAdded);
                gpsModel.longitude = map.get(longitude);
                gpsModel.latitude = map.get(latitude);
                GpsPref pref = GpsPref.getInstance();
                String fileName = map.get(displayName);
                if (!gpsModel.isEmpty() && !pref.isContain(fileName)) {
                    result.add(gpsModel);
//                    pref.putString(fileName);

                }
            }
            cursor.close();
        }

        return result;
    }


//    public GpsModel getPhotoLocation(String imagePath) {
//        LogUtils.d(TAG, "getPhotoLocation==" + imagePath);
//        GpsModel gpsModel = new GpsModel();
//
//        try {
//            // 拍摄时间
//            ExifInterface exifInterface = new ExifInterface(imagePath);
//            String datetime = exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
//            // 设备品牌
//            String deviceName = exifInterface.getAttribute(ExifInterface.TAG_MAKE);
//            // 设备型号
//            String deviceModel = exifInterface.getAttribute(ExifInterface.TAG_MODEL);
//            String latValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
//            String lngValue = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
//            String latRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
//            String lngRef = exifInterface.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
//            if (latValue != null && latRef != null && lngValue != null && lngRef != null) {
//                try {
//                    gpsModel.latitude = convertRationalLatLonToFloat(latValue, latRef);
//                    gpsModel.longitude = convertRationalLatLonToFloat(lngValue, lngRef);
//                    gpsModel.timestamp = datetime;
//                } catch (IllegalArgumentException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return gpsModel;
//    }
//
//
//    private static float convertRationalLatLonToFloat(
//            String rationalString, String ref) {
//
//        String[] parts = rationalString.split(",");
//
//        String[] pair;
//        pair = parts[0].split("/");
//        double degrees = Double.parseDouble(pair[0].trim())
//                / Double.parseDouble(pair[1].trim());
//
//        pair = parts[1].split("/");
//        double minutes = Double.parseDouble(pair[0].trim())
//                / Double.parseDouble(pair[1].trim());
//
//        pair = parts[2].split("/");
//        double seconds = Double.parseDouble(pair[0].trim())
//                / Double.parseDouble(pair[1].trim());
//
//        double result = degrees + (minutes / 60.0) + (seconds / 3600.0);
//        if ((ref.equals("S") || ref.equals("W"))) {
//            return (float) -result;
//        }
//        return (float) result;
//    }

}
