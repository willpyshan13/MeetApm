package com.meiyou.common.apm.okhttp.internal;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import okhttp3.Dns;

import static java.lang.System.currentTimeMillis;

public class XDns implements Dns {

    private Dns impl;

    public XDns(Dns dns) {
        this.impl = dns;
    }

    private long dnsTime;

    @Override
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        long startTime = currentTimeMillis();
        List<InetAddress> results;
        try {
            // 实际的DNS查询
            results = impl.lookup(hostname);
        } catch (Exception e) {
//            e.printStackTrace();
            throw e;
        }
        // 记录DNS查询时间
        dnsTime = System.currentTimeMillis() - startTime;
        //hostname=www.baidu.com results=[www.baidu.com/115.239.211.112] 
//        LogUtils.d("DNS lookup: hostname=" + hostname + " results=" + results
//                + " dnsTime=" + dnsTime + "ms");

//        ApmHelper.getInstance().onDns(hostname, dnsTime);
        return results;
    }

    /**
     * 通过 OKHttpClient可以收集到 对应的DNS实例，然后获取；
     *
     * @return
     */
    public long getDns() {
        return dnsTime;
    }
}
