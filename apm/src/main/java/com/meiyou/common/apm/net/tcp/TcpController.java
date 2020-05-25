//package com.meiyou.common.apm.net.tcp;
//
//import com.meiyou.framework.util.Base64Str;
//import com.meiyou.pushsdk.PushSDK;
//import com.meiyou.pushsdk.socket.SocketManager;
//
//import org.json.JSONObject;
//
///**
// * APM， TCP 上传通道。
// *请勿删除，反射
// * @since 18/3/13
// */
//
//public class TcpController {
//    public static final int MSG_CMD_APM = 12;
//
//    private static TcpController instance;
//
//    public static TcpController getInstance() {
//        if (instance == null) {
//            instance = new TcpController(
//            );
//        }
//        return instance;
//    }
//
//    public int sendMessage(String data) {
//        try {
//            JSONObject json = new JSONObject();
//            json.put("sn", PushSDK.getInstance().getSocketSN());
//            json.put("cmd", MSG_CMD_APM);
//            json.put("data", data);
//
//            SocketManager mClient = SocketManager.getInstance();
//            String message = new String(Base64Str.encode(json.toString().getBytes()));
//            return mClient.sendMessage(message);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return -1;
//    }
//
//
//}
//
