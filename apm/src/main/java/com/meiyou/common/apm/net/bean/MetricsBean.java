package com.meiyou.common.apm.net.bean;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.meiyou.common.apm.core.Proguard;

import java.util.ArrayList;

/**
 * 本地db 存储
 *
 * @since 18/1/22
 */
@Proguard
@Entity
public class MetricsBean {
    public static final int method_unknown = 0;
    public static final int method_get = 1;
    public static final int method_post = 2;
    public static final int method_put = 3;
    public static final int method_delete = 4;
    public static final int method_head = 5;
    public static final int method_patch = 6;

//    String type = "networkMetrics";

//    String[] actions;

    //hashcode(url);

    // Timing
    // DNS查询->TCP建连->SSL握手->请求->响应->接收
    // (1)DNS查询时间=dnsLookupEndTime-dnsLookupStartTime
    // (2)TCP建连时间=tcpConnectEndTime-tcpConnectStartTime
    // (3)SSL握手时间=sslHandshakeEndTime-sslHandshakeStartTime
    // (4)请求时间=requestEndTime-requestStartTime
    // (5)响应时间=responseStartTime-requestEndTime
    // (6)首包时间=请求时间+响应时间
    // (7)接收时间=responseEndTime-responseStartTime

    /**
     * 数据库用的主键
     **/
    @PrimaryKey(autoGenerate = true)
    public int id = 0;

    //    public String id = "";
    public String url = "";
    /**
     * 请求发送时间
     */
    public long startTime = 0;
    public long endTime = 0;
    /**
     * 总响应时间
     */
    public long totalMills = 0;
    /**
     * 首包时间,安卓不准，IOS准
     */
    public long firstPkg = 0;
    public long sentRequestAtMillis = 0;
    public long receivedResponseAtMillis = 0;
    public long dns = 0;
    public long tcp = 0;
    public long ssl = 0;
    /**
     * HTTP状态码
     */
    public int httpCode = 0;
    /**
     * 本地错误码，IOS 有，安卓没有；
     */
    public int errorCode = 0;
    public String contentType = "";
    /**
     * 网络类型： NetworkUtil.getNetTypeDes()
     */
    public int netType = 0;
    public String host = "";
    public String ip = "";
    //HTTP头部大小
    //response body
    public long responseBodyLength = 0;
    public long requestBodyLength = 0;
    /**
     * 总下载字节数
     */
    public long totalByte = 0;

    public int method = method_unknown;


    /**
     * 转为 上传数据格式
     *
     * @return
     */
    public String[] getData() {
        ArrayList<String> list = new ArrayList<>();
        clearData();
        list.add(url);
        list.add(startTime + "");
        list.add(totalMills + "");
        list.add(firstPkg + "");
        list.add(dns + "");
        list.add(tcp + "");
        list.add(ssl + "");
        list.add(httpCode + "");
        list.add(errorCode + "");
        list.add(contentType);
        list.add(netType + "");
        list.add(responseBodyLength + "");
        list.add(requestBodyLength + "");
        list.add(ip);
        list.add(method + "");
        String[] array = new String[list.size()];
        list.toArray(array);
        return array;
    }

    /**
     * 清洗数据
     */
    private void clearData() {
        if (responseBodyLength < 0) {
            responseBodyLength = 0;
        }

        if (requestBodyLength < 0) {
            requestBodyLength = 0;
        }
    }


    @Override
    public String toString() {
        return String.format("响应时间：%s,DNS: %s, TCP: %s; SSL: %s, 首包时间：%s, url: %s;",  this.totalMills, this.dns, this.tcp, this.ssl, this.firstPkg,this.url);
//        return new Gson().toJson(this);
    }
}
