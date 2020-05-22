package com.android.okhttpsample.encrypt;

import android.util.Base64;

import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author vipin.cb , vipin.cb@experionglobal.com <br>
 *         Sep 27, 2013, 5:18:34 PM <br>
 *         Package:- <b>com.veebow.util</b> <br>
 *         Project:- <b>Veebow</b>
 *         <p/>
 */
public class AESCrypt {
    public static final String CHARSET_NAME = "UTF-8";
    private final Cipher cipher;
    private final SecretKeySpec key;
    private AlgorithmParameterSpec spec;
    public static final String SEED_16_CHARACTER = "网络错误，请重试！";

    public AESCrypt() throws Exception {
        // hash password with SHA-256 and crop the output to 128-bit for key
        MessageDigest digest = MessageDigest.getInstance("SHA-384");
        digest.update(SEED_16_CHARACTER.getBytes(CHARSET_NAME));
        byte[] keyBytes = new byte[32];
        byte[] tmpDigest = digest.digest();
        System.arraycopy(tmpDigest, 0, keyBytes, 0, keyBytes.length);
        //LogUtils.d("encrpyt", "digest:" + bytesToHex(digest.digest()));
        //LogUtils.d("encrpyt", "keybytes:" + bytesToHex(keyBytes));
        cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        key = new SecretKeySpec(keyBytes, "AES");
        spec = getIV(tmpDigest);
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();

    }

    public AlgorithmParameterSpec getIV(byte[] digest) {
        byte[] iv = new byte[16];
        //byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, };
        System.arraycopy(digest, 32, iv, 0, iv.length);
        //LogUtils.d("encrpyt", "iv:" + bytesToHex(iv));
        IvParameterSpec ivParameterSpec;
        ivParameterSpec = new IvParameterSpec(iv);
        return ivParameterSpec;
    }

    public String encrypt(String plainText) throws Exception {
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes(CHARSET_NAME));
        String encryptedText = new String(Base64.encode(encrypted,
                Base64.NO_WRAP), CHARSET_NAME);
        return encryptedText;
    }

    public String decrypt(String cryptedText) throws Exception {
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        byte[] bytes = Base64.decode(cryptedText, Base64.NO_WRAP);
        byte[] decrypted = cipher.doFinal(bytes);
        String decryptedText = new String(decrypted, CHARSET_NAME);
        return decryptedText;
    }
}
