package com.android.okhttpsample.wifi;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.okhttpsample.ui.MyApp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * WIFI 统计
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 17/12/29 下午4:08
 */
public class WifiStatController {
    private static final String TAG = "WifiStatController";
    private List<WifiStatModel> mWifiList = new ArrayList<>();

    private WifiStatController() {
    }

    private static WifiStatController instance;

    public static WifiStatController getInstance() {
        if (instance == null) {
            instance = new WifiStatController();
        }
        return instance;
    }

    public void init() {
        uploadWifiList();
    }


    private List getUniqueList(List list) {//去重
        if (list != null && list.size() > 1) {
            ArrayList tempArray = new ArrayList<>();
            Iterator iterator = list.iterator();
            tempArray.add(iterator.next());
            while (iterator.hasNext()) {
                Object scanResult = iterator.next();
                Object removeScanResult = null;
                for (Object newScanResult : tempArray) {
                    if (equarls(scanResult, newScanResult)) {
                        removeScanResult = scanResult;
                        iterator.remove();
                        break;
                    }
                }
                if (removeScanResult == null) {
                    tempArray.add(scanResult);
                }
            }
        }
        return list;
    }

    private boolean equarls(Object oScanResult, Object tScanResult) {
        if (oScanResult instanceof ScanResult && tScanResult instanceof ScanResult) {
            ScanResult os = (ScanResult) oScanResult;
            ScanResult ts = (ScanResult) tScanResult;
            if (null == os.BSSID || null == os.SSID) {
                return false;
            }
            return os.BSSID.equals(ts.BSSID) && os.SSID.equals(ts.SSID);
        } else {
            return oScanResult.equals(tScanResult);
        }
    }

    private synchronized List<ScanResult> getChangeWifiList(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext()
                                                       .getSystemService(Context.WIFI_SERVICE);
        List<ScanResult> wifiList = new ArrayList<>();
        List<ScanResult> temp = wifiManager.getScanResults();

        if (temp == null || temp.isEmpty()) {
            wifiManager.startScan();
            temp = wifiManager.getScanResults();
        }
        if (temp.size() > 0) {
            wifiList = temp;
        }
        
//        if ((wifiList == null || wifiList.isEmpty()) && wifiManager.isWifiEnabled() && wifiManager.getConnectionInfo() != null) {
//            //没权限扫描到列表时取当前连接的wifi
//            WifiInfo currentWifiInfo = wifiManager.getConnectionInfo();
//            if (!TextUtils.isEmpty(currentWifiInfo.getBSSID()) && !TextUtils.isEmpty(currentWifiInfo
//                    .getSSID())) {
//                try {
//                    ScanResult scanResult = ScanResult.class.newInstance();
//                    scanResult.BSSID = currentWifiInfo.getBSSID();
//                    scanResult.SSID = currentWifiInfo.getSSID();
//                    if (wifiList == null) {
//                        wifiList = new ArrayList();
//                    }
//                    wifiList.add(scanResult);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        if (wifiList != null && !wifiList.isEmpty()) {//取差集
//            wifiList = getUniqueList(wifiList);
//            if (mWifiList.size() > 0 && wifiList.size() > 0) {
//                Iterator iterator = wifiList.iterator();
//                while (iterator.hasNext()) {
//                    ScanResult newScanResult = (ScanResult) iterator.next();
//                    for (WifiStatModel statisticsBean : mWifiList) {
//                        if ((null == newScanResult.BSSID || null == newScanResult.SSID) ||
//                                (newScanResult.BSSID.equals(statisticsBean.getBSSID()) && newScanResult.SSID
//                                        .equals(statisticsBean.getSSID()))) {
//                            iterator.remove();
//                            break;
//                        }
//                    }
//                }
//            }
//            if (wifiList.size() > 0) {
//                mWifiList.addAll(genderWifiList(wifiList));
//            }
//        }
        return wifiList;
    }

    private synchronized List<WifiStatModel> genderWifiList(List<ScanResult> scanResults) {
        ArrayList<WifiStatModel> wifiStatisticsBeans = null;
//        for (ScanResult result : scanResults) {
//            
//        }
        List<String> ssidlist = new ArrayList<>();
        if (scanResults != null && !scanResults.isEmpty()) {
            wifiStatisticsBeans = new ArrayList();
            for (ScanResult scanResult : scanResults) {
                String ssid = scanResult.SSID;
                if (!TextUtils.isEmpty(scanResult.BSSID) && !TextUtils.isEmpty(ssid) && !ssidlist.contains(ssid)) {
                    ssidlist.add(ssid);
                    wifiStatisticsBeans.add(new WifiStatModel(scanResult));
                }
            }
        }
        return wifiStatisticsBeans;
    }

//    private static final String SP_AND_GA_KEY = "wifiList" + FrameworkDocker.getInstance()
//                                                                            .getFinalUserId();
//    private static final boolean isSave2Disk = false;

    private void uploadWifiList() {
//        try {
//        if (mWifiList == null) {
//            if (isSave2Disk) {
//
//                String json = ProtocolInterpreter.getDefault()
//                                                 .create(CalendarRouterMainStub.class)
//                                                 .invokePreUtilsGetString(SP_AND_GA_KEY, context);
//                if (TextUtils.isEmpty(json)) {
//                    mWifiList = new ArrayList();
//                } else {
//                    List list = new Gson().fromJson(json, new TypeToken<List<WifiStatisticsModel>>() {
//                    }.getType());
//                    mWifiList = getUniqueList(list);
//                }
//            } else {
//                mWifiList = new ArrayList();
//            }
//        }
        Context context = MyApp.getContext();
//        mWifiList = new ArrayList();
        List<WifiStatModel> wifiList = genderWifiList(getChangeWifiList(context));
        if (wifiList != null && wifiList.size() > 0) {
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("wifi", wifiList);

            Log.d(TAG, "uploadWifiList: " + hashMap.toString());
//            try {
//                ProtocolInterpreter.getDefault().create(IApm.class).onWifi(hashMap);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

//            GaController.getInstance(context).onEvent("/wifi", hashMap);
//            if (isSave2Disk) {
//                ProtocolInterpreter.getDefault()
//                                   .create(CalendarRouterMainStub.class)
//                                   .invokePreUtilsSaveString(SP_AND_GA_KEY, new Gson().toJson(mWifiList), context);
//            }

        }
    }


}
