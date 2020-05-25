package com.meiyou.common.apm.db.bean;

import com.meiyou.common.apm.core.Proguard;

import java.util.ArrayList;

/**
* 初始化设备
 */
@Proguard
public class DeviceBean {
    public String did = "";
    public ArrayList<String> dev = new ArrayList<>();
    public ArrayList<String> app = new ArrayList<>();
}