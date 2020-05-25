//package com.meiyou.common.apm.db;
//
//import android.content.Context;
//
//import com.meiyou.common.apm.util.SessionCreator;
//import com.meiyou.sdk.common.database.annotation.Id;
//import com.meiyou.sdk.core.NetWorkStatusUtils;
//
//import java.io.Serializable;
//
///**
// * Ga  持久化保存 对象
// *
// */
//
//public class ApmBean implements Serializable {
//    /**
//     * 数据库用的主键
//     **/
//    @Id(column = "columnId")
//    public int columnId = 0;
//
//    public String sessionId;
//    // "事件名",
//    public String event;
//    //    : 1234, // 以毫秒计
//    public long timestamp = 0;
//    // 取值参照HTTP头部同名字段
//    public String myclient;
//    // 当前身份，取值参照HTTP头部同名字段
//    public int mode;
//    // 账号ID，取值参照HTTP头部同名字段
//    public int uid;
//    // 网络环境，取值参照HTTP头部同名字段
//    public int apn;
//    // 事件ID（可选，一次性事件不需要、时长类事件需要）
//    public String eventID;
//    //额外参数，Json
//    public String attributes;
//
//    public String source;
//
//
//    @Deprecated
//    public int code;
//    @Deprecated
//    public String path;
//    @Deprecated
//    public String param;
//
//
//    public ApmRemote convertRemote() {
//        ApmRemote apmRemote = new ApmRemote();
//        apmRemote.sessionId = sessionId;
//        apmRemote.event = event;
////        apmRemote.attributes = JSON.parse(this.attributes);
//        apmRemote.timestamp = this.timestamp;
//        apmRemote.myclient = myclient;
//        apmRemote.mode = mode;
//        apmRemote.uid = uid;
//        apmRemote.apn = apn;
//        apmRemote.eventID = eventID;
//        apmRemote.source = source;
//
//        return apmRemote;
//    }
//
//    public class ApmRemote implements Serializable {
//        public String sessionId;
//        // "事件名",
//        public String event;
//        //    : 1234, // 以毫秒计
//        public long timestamp = 0;
//        // 取值参照HTTP头部同名字段
//        public String myclient;
//        // 当前身份，取值参照HTTP头部同名字段
//        public int mode;
//        // 账号ID，取值参照HTTP头部同名字段
//        public int uid;
//        // 网络环境，取值参照HTTP头部同名字段
//        public int apn;
//        // 事件ID（可选，一次性事件不需要、时长类事件需要）
//        public String eventID;
//        //需要转为JsonObject给接口；
//        public Object attributes;
//
//        public String source;
////        @Deprecated
////        public int mOrder;
////        @Deprecated
////        public int code;
////        @Deprecated
////        public String path;
////        @Deprecated
////        public String param;
//    }
//
//    /**
//     * 创建默认Bean
//     *
//     * @param context
//     * @return
//     */
//    public static ApmBean createBean(Context context) {
//        ApmBean gaDO = new ApmBean();
//        gaDO.sessionId = SessionCreator.getInstance().getId();
//        gaDO.timestamp = System.currentTimeMillis();
////        gaDO.myclient = ChannelUtil.getMyClient(context);
//        int apn = NetWorkStatusUtils.getNetType(context);
////        gaDO.apn = apn;
////        int userId = (int) FrameworkDocker.getInstance().getFinalUserId();
////        gaDO.uid = userId;
//        return gaDO;
//    }
//}
