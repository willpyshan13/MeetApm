package com.meiyou.common.apm.core;

import com.meiyou.common.apm.net.bean.MetricsBean;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 对内 管理类
 * 收集数据
 *
 * @author zhengxiaobin@xiaoyouzi.com
 * @since 18/1/23
 */

public class ApmHelper {
    private static ApmHelper instance;
    private HashMap<String, Queue<Long>> dnsMap = new HashMap<>();
    private HashMap<String, Queue<Long>> tcpMap = new HashMap<>();
    private HashMap<String, Queue<Long>> sslMap = new HashMap<>();
    /**
     * SSL 队列
     */
    Queue<Long> sslQueue = new LinkedList<Long>();
    Queue<Long> tcpQueue = new LinkedList<Long>();
    Queue<Long> dnsQueue = new LinkedList<Long>();

    HashMap<String, String> socketMap = new HashMap<>();


    private static class SingletonHolder {
        private final static ApmHelper instance = new ApmHelper();
    }

    public static ApmHelper getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * 收集结束
     *
     * @param bean
     */
    public void onEvent(MetricsBean bean) {
        //fixme 设置代理后，dns,tcp，ssl 里面的host是IP地址，导致匹配不到
//        String host = bean.host;
//        bean.dns = getValue(dnsMap, host);
//        bean.tcp = getValue(tcpMap, host);
//        bean.ssl = getValue(sslMap, host);
//        if (socketMap.containsKey(host)) {
//            bean.ip = socketMap.get(host);
//        }
        

        //warning: code  order

        new PostTask(bean).executeOnExecutor();
    }

    @Deprecated
    public void onDns(String hostname, long dnsTime) {
        try {
            //过滤无效的：DNS lookup: hostname=192.168.45.222 results=[/192.168.45.222] dnsTime=0ms
            if (dnsTime != 0) {
                putMap(dnsMap, hostname, dnsTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void putMap(HashMap<String, Queue<Long>> map, String hostname, long value) {
        if (map.containsKey(hostname)) {
            Queue<Long> queue = map.get(hostname);
            //后端进行插入操作
            queue.offer(value);
        } else {
            Queue<Long> queue = new LinkedList<Long>();
            queue.offer(value);
            map.put(hostname, queue);
        }
    }

    private long getValue(HashMap<String, Queue<Long>> map, String hostname) {
        long value = 0;
        //返回第一个元素，并在队列中删除
        Queue<Long> queue = map.get(hostname);
        if (queue != null && queue.size() > 0) {
            value = queue.poll();
        }
        return value;
    }
    @Deprecated
    public void onTcp(SocketAddress endpoint, long connectTime) {
        try {
            // socket=www.baidu.com/115.239.211.112:4
            // 设置代理为： /192.168.45.222:8888
            //需要版本>19
//        String host = ((InetSocketAddress) endpoint).getHostString();
            String hostName = ((InetSocketAddress) endpoint).getHostName();
            String address = ((InetSocketAddress) endpoint).getAddress().getHostAddress();
            // FIXME: 18/2/5 //支持设置代理方式
            socketMap.put(hostName, address);
            putMap(tcpMap, hostName, connectTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Deprecated
    public void onSSL(String host, long handshakeTime) {
        try {
            putMap(sslMap, host, handshakeTime);
//        sslQueue.offer(handshakeTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private class PostTask extends AsyncTaskSerial<String, Void, Void> {
//        private final MetricsBean bean;
//
//        PostTask(MetricsBean bean) {
//            this.bean = bean;
//        }
//
//        @Override
//        protected Void doInBackground(String... strings) {
//            Context context = ApmAgent.getContext();
//            LogUtils.d(bean.toString());
//            HttpDao dao = ApmDbFactory.getInstance(context).getHttpDao();
//            dao.insertBean(bean);
////            ApmDAO.getInstance(context).addApm(bean);
//            return null;
//        }
//    }


}
