package com.android.okhttpsample.wifi;

import android.net.wifi.ScanResult;

import com.google.gson.Gson;

import java.io.Serializable;


public class WifiStatModel implements Serializable {
    public WifiStatModel(ScanResult scanResult) {
        this.SSID = scanResult.SSID;
        this.BSSID = scanResult.BSSID;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public String getSSID() {
        return SSID;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || !(o instanceof WifiStatModel)) {
            return false;
        } else {
            boolean bss = BSSID == null ? ((WifiStatModel) o).getBSSID() == null : BSSID.equals(((WifiStatModel) o).getBSSID());
            boolean ss = SSID == null ? ((WifiStatModel) o).getSSID() == null : SSID.equals(((WifiStatModel) o).getSSID());
            return bss && ss;
        }
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    private String SSID;
    private String BSSID;

    public String getBSSID() {
        return BSSID;
    }

    public WifiStatModel setBSSID(String BSSID) {
        this.BSSID = BSSID;
        return this;
    }

}
