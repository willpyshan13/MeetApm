package com.android.okhttpsample.encrypt;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * 加密解密，算法
 *
 * @author Administrator
 */
public class DesUtils {


    @Deprecated
    public static String encrypt(String message, String key) throws Exception {
        Cipher cipher = getCipher(key, Cipher.ENCRYPT_MODE);
        return encodeBase64(cipher.doFinal(message.getBytes("UTF-8")));
    }

    /**
     * @param message msg
     * @param cipher  encrypt cipher
     * @return
     * @throws UnsupportedEncodingException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    public static String encrypt(String message, Cipher cipher)
            throws UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        if (cipher == null) return message;
        return encodeBase64(cipher.doFinal(message.getBytes("UTF-8")));
    }

    @Deprecated
    public static String decrypt(String message, String key) throws Exception {
        byte[] bytesrc = decodeBase64(message);//convertHexString(message);
        Cipher cipher = getCipher(key, Cipher.DECRYPT_MODE);
        byte[] retByte = cipher.doFinal(bytesrc);
        return new String(retByte);
    }

    /**
     * @param message msg
     * @param cipher  decrypt cipher
     * @return
     * @throws Exception
     */
    public static String decrypt(String message, Cipher cipher) throws Exception {
        if (cipher == null) return message;
        byte[] bytesrc = decodeBase64(message);//convertHexString(message);
        byte[] retByte = cipher.doFinal(bytesrc);
        return new String(retByte);
    }

    @NonNull
    public static Cipher getCipher(String key, int encryptMode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, UnsupportedEncodingException, InvalidKeySpecException, InvalidAlgorithmParameterException {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
        cipher.init(encryptMode, secretKey, iv);
        return cipher;
    }

    public static byte[] convertHexString(String ss) {
        byte digest[] = new byte[ss.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }

        return digest;
    }

    public static String toHexString(byte b[]) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String plainText = Integer.toHexString(0xff & b[i]);
            if (plainText.length() < 2)
                plainText = "0" + plainText;
            hexString.append(plainText);
        }

        return hexString.toString();
    }


    @TargetApi(Build.VERSION_CODES.FROYO)
    public static String encodeBase64(byte[] b) {
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    @TargetApi(Build.VERSION_CODES.FROYO)
    public static byte[] decodeBase64(String base64String) {
        return Base64.decode(base64String, Base64.DEFAULT);
    }
}
