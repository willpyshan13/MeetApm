package com.android.okhttpsample.http;


import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


/**
 * AES 是一种可逆加密算法，对用户的敏感信息加密处理 对原始数据进行AES加密后，在进行Base64编码转化；
 */

public class AESOperator {
    /*
     * 加密用的Key 可以用26个字母和数字组成 此处使用AES-128-CBC加密模式，key需要为16位。
     * 已服务端定义好的值
     */
    private String sKey = "a2e502fa9f48b5cf";//key，可自行修改
    private String ivParameter = "a2e502fa9f48b5cf";//偏移量,可自行修改
    private static AESOperator instance = null;

    public AESOperator() {

    }

    public static AESOperator getInstance() {
        if (instance == null)
            instance = new AESOperator();
        return instance;
    }

    /**
     * 加密
     *
     * @param sSrc
     * @return
     * @throws Exception
     */
    public String encrypt(String sSrc) throws Exception {
        return encryptString(sSrc, sKey, ivParameter);
    }

    public byte[] encrypt(byte[] source) throws Exception {
        return encryptByte(source, sKey, ivParameter);
    }


    private String encryptString(String encData, String secretKey, String vector) throws Exception {
        byte[] bytes = encData.getBytes("UTF-8");
        byte[] data = encryptByte(bytes, secretKey, vector);
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    private byte[] encryptByte(byte[] source, String secretKey, String vector) throws Exception {
        if (secretKey == null) {
            return null;
        }
        if (secretKey.length() != 16) {
            return null;
        }
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        byte[] raw = secretKey.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        IvParameterSpec iv = new IvParameterSpec(vector.getBytes());
        // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte[] encrypted = cipher.doFinal(source);
        return encrypted;
    }


    /**
     * 解密
     *
     * @param sSrc
     * @return
     * @throws Exception
     */

    public String decrypt(String sSrc) throws Exception {
        byte[] encrypted1 = Base64.decode(sSrc, Base64.DEFAULT);// 先用base64解密 
        return decryptBase(encrypted1, sKey, ivParameter);
    }

    public String decrypt(byte[] sSrc) throws Exception {
        return decryptBase(sSrc, sKey, ivParameter);
    }

    /**
     * 解密过程
     *
     * @param sSrc
     * @param key
     * @param ivs
     * @return
     * @throws Exception
     */
    private String decryptBase(byte[] sSrc, String key, String ivs) throws Exception {
        try {
            byte[] raw = key.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(ivs.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = sSrc;
            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original, "utf-8");
            return originalString;
        } catch (Exception ex) {
            return null;
        }
    }


    public static void main(String aregs[]) {
        String content = "啊啊坎大哈的卡扩大2342jndsfsx";

        try {
            AESOperator aes = new AESOperator();
            String  temp="QkTEziH8zuQrYvNkYxncWQvp1WMjxwvx+EVTMEHrQVutJk0TV5jfzK1Z1HcDf+MDnQtlYHyYAk9x\\n0c1TlM/rqD17S37L365dmEIQ5x5q7JU6+e3BwsJwKxTSiLzxxSn9S3pRZBFT4Ms3qqVlLwqo4lr4\\nvRKC5pT+38UhSPNAmVrGofAl6CgtGwsdKx3txQKzgfqEFVGYrf515ZOKUc9jY3bVkc/jWtZh9KLh\\nSgiQO+P0RpZGDHEFfLCUgQcdEozCYnElVBHebBPt0kD8hrR1ccXX3MXycI3cbQvLEezVbaYHyaJd\\nPVxHGPedLAZ97rd3HkTFdRb72kOQs0TImAyo+AAEMJZlMwcXgREmw0W/daWm/p+tG5GOd4mQ8QDl\\naSkr94QIIwM08lM7zYOi9Zjr9VxZTkVTJwmLK2K0vLFPap2tKQLBCQo6IUApJSWLTc9HxbQquGgI\\n24nD/fN87Z6SbttKR3wiJHrkXfh3TbhwKR9e5i07HhRBodfh/vdtmqv+gFfOuRNfYHfQyz+ixFgc\\nga++ym9326/XeWClmSB7iuyJG3Eca52Q3206wbatLwKniUnLpJyPC2HOfCFBoAofy63LaiJO+/n5\\n65fee7wzTFh0Q+oF3IvHk+K6vD2y8co0ongv7ZZYKsBWuEL+d75VQ8TJWp4Rp0XrOhBR4AVrvAcD\\nx4rAvQ30pyFhKvvRc8VkP2boCAavYpUvUvfnIJZyAe4LyP+9n4jisfQoBAQbuUq0fFMMWxbhDsls\\noIQY2RS22ubPjrGsDJyGAskEJ1gavk+AMnDzsbt8bmw6WpIC44ErxjUWTEOgAwhqxVSr+QGkqa2G\\nYB3SgFGshI6I/qebxrLkqEG2Kck0eUuynLf/uS9lzcIuJ1PmurCxIa0Ne2IeNJrFRdV8xqb9Xuwc\\nQZ8D4gAkiXtsv3+sJJH1lUOfFZWWtXS9zJFPOSoXBEWJkQ/zRt+Di8wXVVls2TOV4OWlpex3JK7b\\nCl255CzhQ0hhi7AyAKGpnqvrpBPXgDmIpa5EpWSVR0BwV8kDsRRDkPENIQLXNdtzfkcZyBmPYdWV\\nLrkyAhVha2TAwXH3bG4TO/deHhm1EPMmZ7CMtRAMWf9qgEEBci28ziMF8CfaSYeIslYn04kj2EKs\\n7MkkYi6xQvp+nlBpTNo0UFwtokJ7tJn2qt150+CCJBrs7DczQKjxyicTkuHaU6QkYAeO+k7aTQSo\\nWZ8QHOuKt8Mjf3HXhoeA3H1ZSs11s9Dh1t4YxdVEfX5tDSvySZ74AMjD20yPqFVeDISN8YmiMgqK\\n0aZoKWb5g/aZzmtDxTK1uTueNEDe7bg+ThRoaV01y5zTfahnQpQBiuaNy9OSdR1EqGiwl/SaN20x\\newTHPGsLArFeyK62fxjBJOqGkPY5ylg1tHI7YZIi3kHr7FgH3oRcOf/Kkpt1gYm5nGpKl1DmcAD1\\nd7o2IOjtXZxgopbYQS/aJ4/HK0RnHeKjjNb5cgXaBEyRStkRNdYs3knG+Ee6vGDCz1b2Io2MJ;yfN\\nICGsZa5EASVpgLWI3EyrmrHwOIuIBZuiDZIdr853W0sxLYQzpSwgUo1gNwLtjoxoZAFjvqTB9rJN\\ny2ksHKg4ijeCjIq4C2O7jjJ5nhPHI0HcAFSR1oZ4Ek+mOVgD7Bh26FFiAg3o7A949m0ytxqbSiS6\\ngbsFfmGagO8PE4IQT3Ld7jRXJg9fUGovGJ/IQOwaPJRH8AuR62it8ts8qgWs4RoBJb6VEYL9rz6T\\nDWG8w09j9Tdt0kTKQlXyQE5h5WsOU5DeDQ4JPcUrghvcdHFCswZ6q/BkyffuJ9hnxQcs5OylfNnp\\nzf/r4zlTSDthDP6r8fdGyI9MzZw1n7fpX4wH3GvV8NFZUTo8xrfywM8FGUIdcCxpne8meo6JyCB4\\nJ/UfPerjuyyySNq2pL9IrsNsrIAHcdprgq0P9/kGmTt7f1dwrFqJ2HweaynrR+EzcFeykm8Kp4FC\\nM3MhJhFpMg5qZHrqvdfSrdWrXLrDj4X4VhminzJP4fmYpmaQZfKog5S71KJ83D6ztwv2hrNNl2SJ\\nGJEuylKvVngalGMmorAwawWCAUgLXOQpXmvsdQ3HPLnJn+sCDOfoHuFHLMssa2Cbxi/tKyOo2R8W\\nQ/FYlykRR4RtPyqa5k+LHIHy2OBzzzQi3yzgwhvO3nptXmGM9FesTKCdccFtYeQyvOUsVfZtF0SQ\\nGbYn4yVaennO3bFWWJQ4aoN+OlWpkahDaRzqGhfIPRnQ309VaA85mu48OocZZWw3gbFjn7jw2pqu\\nHH4BJ9dvvEN7DDzbY22wRpZMAmACNoUKUTsmEWGEwYPxGhTiRJ3Wv8TH/nOFj8UUkFVGnS7xl4xq\\nzHIYohw0af2XJQCyDlcDoOC9qaLsDBT2NHOFgL98PVai7SCOS7beMijlmuYnkgUpOD8rq3hKJBuN\\n+yEnTlKE99ja/rYko/VMN57a+H/9XaoftdcFVxt/nuz+Kf+v3MB7ZB5lLdNIRIQDh5BIVYOMR3Kj\\nUo5HSn8OYUaUL0JK6GF3DcNeMwg0A6s5ckejqFZG9fnWp8vrefZTgp6Y7qFQ6SOtv9lOucsxPpSA\\ncbR6OVgQlxyzBd0Xze5a/UNSZpb6cdPibnbr2AddHRosDTBFNXbPVSlPdnuzqJspiZPTDB9S81CC\\nV7UyRsDlAQpAnS4I3rDjzoOJBjSyCQ9sP3nV7Mp/ivJS/aV3rI+3X9Q285oM7mhuMyyX+uvYYvad\\nfgI4tu7HKQjG7rCCPuwS15lm1iFon92l0RbGBbjHxuX9C5s/IM9+v0sL4pwyqKl5sbY77o+JT/nc\\ne3/bhVGu4ifXLA6231S7hvhmIE3MbHMN+ETT7fn0dRR+vLv3wn0CaaqzKDQIhJlZyQUP\\n";
//            String temp="7prXtbnTSDKCMUEXJCVXjpvx/5crbsLTV6TCHkSuYEg=";
//            System.out.println(aes.decrypt(temp));
//            System.exit(0);
//            //加密
            System.out.println("加密前：" + content);
            String encrypt = aes.encrypt(content);
            System.out.println("加密后：" + encrypt);
            //解密
            String decrypt = aes.decrypt(encrypt);
            System.out.println("解密后：" + decrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
