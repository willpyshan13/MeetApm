package com.android.okhttpsample.encrypt;

import javax.crypto.Cipher;

//import com.meiyou.framework.io.FileStoreProxy;
//import com.meiyou.framework.util.DesUtils;
//import com.meiyou.framework.util.PackageUtil;
//import com.meiyou.sdk.core.LogUtils;
//import com.meiyou.sdk.core.StringUtils;

/**
 * 加解密manager
 * Created by hxd on 16/1/13.
 */
public class EncryptManager {
    private static final String SP_NAME = "-sp-en-pt-g-";
    private Cipher mEnCipher;
    private Cipher mDeCipher;
    private String uniqueKey;//标识 加密的版本,  直接影响加密 key的值

//    public static EncryptManager getInstance() {
//        return Holder.instance;
//    }

//    private static class Holder {
//        static EncryptManager instance = new EncryptManager();
//    }
//
//    private EncryptManager() {
//    }
//
//    /**
//     * @param context   context
//     * @param uniqueKey 不同的uniqueKey导致 使用不同的加密key 建议一直保持一致 不能为空
//     */
//    public void init(@NonNull Context context, @NonNull String uniqueKey) {
//        try {
//            PackageInfo mInfo = PackageUtil.getPackageInfo(context);
//            if (StringUtils.isEmpty(uniqueKey)) {
//                if (mInfo == null) {
//                    this.uniqueKey = "12345678";
//                } else {
//                    this.uniqueKey = mInfo.versionName;
//                }
//            }
//            mEnCipher = DesUtils.getCipher(generateMyKey(), Cipher.ENCRYPT_MODE);
//            mDeCipher = DesUtils.getCipher(generateMyKey(), Cipher.DECRYPT_MODE);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    /**
//     * 该字符串是否已经加密了?
//     * 1.首先查找是否存在该msg
//     * 2.对msg进行encrypt 再查找是否存在
//     *
//     * @param msg 要判断的msg
//     * @return
//     */
//    public boolean useEncrypt(String msg) {

//        String result = FileStoreProxy.getValue(msg, SP_NAME);
//        if (StringUtils.equals(uniqueKey, result)) {
//            return true;
//        }
//        String currentKey = FileStoreProxy.getValue(uniqueKey, SP_NAME);
//        boolean hasKey = StringUtils.isNotEmpty(currentKey);
//        String encryptVersion = FileStoreProxy.getValue(encryptKey(msg, false), SP_NAME);
//        return hasKey && StringUtils.equals(encryptVersion, uniqueKey);
//    }
//
//    private String generateMyKey() {
//        String currentKey = FileStoreProxy.getValue(uniqueKey, SP_NAME);
//        if (StringUtils.isEmpty(currentKey)) {
//            currentKey = UUID.randomUUID().toString().substring(0, 8);//目前不随版本变化 后续如果需要变化随时更改
//            FileStoreProxy.setValue(uniqueKey, currentKey, SP_NAME);
//        }
//        return currentKey;
//    }
//
//    /**
//     * @param msg 待加密字符串
//     * @return
//     */
//    public String encryptKey(String msg) {
//        return encryptKey(msg, true);
//    }
//
//    private String encryptKey(String msg, boolean save) {
//        String result = msg;
//        try {
//            result = DesUtils.encrypt(msg, mEnCipher);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        if (save) {
//            FileStoreProxy.setValue(result, uniqueKey, SP_NAME);
//        }
//        return result;
//    }
//
//    /**
//     * @param msg 待解密字符串
//     * @return
//     */
//    public String decryptKey(String msg) {
//        String result = msg;
//        try {
//            result = DesUtils.decrypt(msg, mDeCipher);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    /**
//     * 使用 AES算法加密  目前用于登陆敏感信息加密
//     * @param content  待加密字符串
//     * @return  已经加密的字符串
//     * @throws Exception  加密错误
//     */
//    public String encryptUseAES(String content) throws Exception {
//        AESCrypt aesCrypt = new AESCrypt();
//        return aesCrypt.encrypt(content);
//    }
//
//    private void test() {
//        String testKey = "123456789abcdefg";
//        String encrypt = EncryptManager.getInstance().encryptKey(testKey);
//        LogUtils.d("EncryptManager", "encrypt  " + testKey + " , " + encrypt);
//        boolean check = EncryptManager.getInstance().useEncrypt(testKey);
//        LogUtils.d("EncryptManager", "check test  " + check);
//        boolean check2 = EncryptManager.getInstance().useEncrypt(encrypt);
//        LogUtils.d("EncryptManager", "check encypt " + check2);
//        String originStr = EncryptManager.getInstance().encryptKey(encrypt);
//        LogUtils.d("EncryptManager", "origin str " + originStr);
//    }
}
