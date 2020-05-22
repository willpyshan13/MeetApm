package com.meiyou.common.apm.db.bean;

import com.meiyou.common.apm.core.Proguard;

import java.util.ArrayList;

/**
* 初始化设备
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 18/1/23
 */
@Proguard
public class DeviceBean {
    public String did = "";
    public ArrayList<String> dev = new ArrayList<>();
    public ArrayList<String> app = new ArrayList<>();
}