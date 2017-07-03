package com.als.framework.tools;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

/**
 * Created by lvtu on 2017/6/27.
 */
public class EncryptUtils {

    public enum EncryptAlgorithm {
        AES,
        RSA;
    }

    public static byte[] decrypt(byte[] cipher, EncryptAlgorithm encryptAlgorithm, String key) {
        switch (encryptAlgorithm) {
            case AES:
                return decryptUsingAES(cipher, key);
            case RSA:
                return decryptUsingRSA(cipher, key);
            default:
        }
        throw new RuntimeException("not suported");
    }

    public static byte[] AESEncrypt(String key,byte[] value){
        try {
            IvParameterSpec iv = new IvParameterSpec(key.substring(0,16).getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.substring(0,16).getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            return cipher.doFinal(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static byte[] AESDecrypt(String key,byte[] value){
        return decryptUsingAES(value, key);
    }

    public static byte[] decryptUsingAES(byte[] cipherOctet, String key) {
        try {
            IvParameterSpec iv = new IvParameterSpec(key.substring(0, 16).getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.substring(0, 16).getBytes("UTF-8"), "AES");//超过这个大小的暂时不支持

            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            return cipher.doFinal(cipherOctet);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * Decrypt text using private key.
     *
     * @param bytes      :encrypted text
     * @param privateKey :The private key
     * @return plain text
     * @throws java.lang.Exception
     */
    public static byte[] decryptUsingRSA(byte[] bytes, String privateKey) {
        byte[] decryptedText = null;
        try {
            // get an RSA cipher object and print the provider
            final Cipher cipher = Cipher.getInstance("RSA");

            // decrypt the text using the private key
            cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));
            decryptedText = cipher.doFinal(bytes);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return decryptedText;
    }

    private static PublicKey getPublicKey() {
        try {
            X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(null);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(pubKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static PrivateKey getPrivateKey(String privateKey) {
        try {
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(privateKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] enpadding(byte[] bytes, byte length) {
        if (bytes.length % length == 0) {
            byte[] lengthPadding = new byte[length];
            Arrays.fill(lengthPadding, (byte) 0);
            byte[] newArray = new byte[lengthPadding.length + bytes.length];

            System.arraycopy(bytes, 0, newArray, 0, bytes.length);
            System.arraycopy(lengthPadding, 0, newArray, bytes.length, lengthPadding.length);
            return newArray;
        } else {
            int padLength = length - bytes.length % length;
            byte[] padding = new byte[padLength];
            Arrays.fill(padding, (byte) 0);

            byte[] lengthPadding = new byte[length];
            Arrays.fill(lengthPadding, (byte) 0);
            lengthPadding[0] = (byte) padLength;

            byte[] newArray = new byte[bytes.length + padding.length + lengthPadding.length];

            System.arraycopy(bytes, 0, newArray, 0, bytes.length);
            System.arraycopy(padding, 0, newArray, bytes.length, padding.length);
            System.arraycopy(lengthPadding, 0, newArray, bytes.length + padding.length, lengthPadding.length);
            return newArray;
        }
    }

    public static byte[] depadding16(byte[] dataWithPadding) {
        int paddingLength = dataWithPadding[dataWithPadding.length - 16];
        byte[] destArray = new byte[dataWithPadding.length - 16 - paddingLength];
        System.arraycopy(dataWithPadding, 0, destArray, 0, destArray.length);
        return destArray;
    }

    public static void main(String[] args) {
        try {
            String cipher = "238AFVCwIO+jpFtBGlMZKyH+9/TWb1KCTX+necOzH8CsKndn5RD9P7UcptZlbunkhjuERhYVYUlEVceDez2cNRNqr3dIn82jbH5Kwsc3g+P1GNg66Qf32qffknH/ugYLqaK6deCalneA5Rci3rOzp4OhfpGiZyYe+uEaTYPcca+B4IAwvgg82S1r2z8mQgjeVNGZyOrh1pGQWlkcJk9mXwOjgDC9NVaQ35x47+7YWNeL28nPLsdXDa5KSZjOmG3lixwXGlzK1+h/57VCZxJYzArURY18wVJDz8ppVvpv8iqCQjRc8fQXNEbzDW8msHsD8c5rMYZ5F5YHG1343+LwgA==";
            String privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDsCBvU+xgl/D2LFqkZmaGsdjcMtFKMVnuo0XxQL8fmmg8Ua/WSC7jsIcZiabtvnDP86pUBYS3SeAWi0LSW8aWxx0y8sABXUcit1Gm0VhWKeNTAYZ3H5dW4cCOWMeRsprOXI/ra1WTdSca2lfg7HMsd42KqafGsmlNmhM6zceOg0lYPwpJwdDwaFsTE4IPN90YHsm/icVGhRr2gQMh8mPfu9UjfJj6Qc1nEB7bCcsI0WVnHRCdaGe4aCkfv5EzCI+4UmVk1ZBbHVuvNJ6G9EIlGfPGHibJlYyLtmrz+N3+Ch8RP+yzacHoXPJisT1EvxdEpwYrkaWzW8SEFF7h48+l/AgMBAAECggEBANlhJnDiyHzDcNLWjeq76YByZ4TV1mjVhZIXTQx01QB2xoiiXUqibS9cc99TTYMfM2otign5PEBotZgb6xZRe67dxLjKx6Wj9tTRmoCx/6jGW/rKbIwcCteQv9WmIaBHb4M1aeV9PyXmupKSUEnw3MisEMSuShwTDdiPW+c8bs4+5JyRmeL/p2FpV6Qg3a/cXjyc0ENwp8DhRVSGHEPrPhOH7fiDs/WipBo7glT0tXoLyqKr8vG0JJQOTrwVlDwr3CCaSeJssWzpxQdHENDw7co9lewOGRi2qxUvafW51UzIX7IGr2quhPtkRZqsoBQ7prpGZuvPNqxKLAkQiu8VUdECgYEA+T7EQv9CSjwrmvtSz5i0ynPGAgBJtlnn1ZCu607XIOVNqmNk/XpNndqpJbiSOjda0NhAflW1l0ROnNIoKahbwcE3+RcLoj3rjbscpIffy4hmKnrZMNi3Jgr4oL50Pi9DYLbXV6hri/JDRfCh8Wfc3mgYHCzVZJVPJ+SGWZX4K+0CgYEA8m2rF8jU/358JTnR9xUthYHGSYaA5iqM8yYK+hZ1De9BqtluUz4KAVBCCWo1/iY7XJEq4KGffxn9YXWDRIG4ZN6bAUdZOlAlwi3ZUFr5N/tnm9K6GoX/NnDv7zfMXqfouzKS5BOmwat+/Vm8Gv2qhRVusWyf1pEUCfXN07ZWdZsCgYEAiZIAZ+5WW30coF1p1tW1rCz8tKwOA41aLgG+2nhYu3p1RL+5xULVRnSjegOHCoT1JmLGLjUGluiH73z72ApqIuaQC3eR30oIEuGdLJ8mgkPWCEpoSNLFPEU/+BFHPe9+ZApnj6WRPtxID3mDGSi3uzZ/YEXKvsq+twkmspnZMf0CgYEAk+e06t3slbRlqZCNOWICU+HJSDJ3kfWD6RTcvZfV8pp2RfZCCSdgk0yT/Yp/Rn+s19z5e/MfjDwfCsbXP4SlK2VkzHLrTVh4sO+oFg5q3mzT2Y3HXUdDXcOpnxk75sQqiYthcyVKJ/6pi4ij6xO4ZVmeH0FWg2IgB7Ofz/r+5OECgYAj+bT88Wq/m24Eo3tAyL6/LDlnY9cYRr2Uzjy30PJuKmuSfVYY4tkz8FRQN1pU2s6uHsXDz2d213yglnkX+GIEdvdkWmt7ha85lwMQFyYhd6Bx24xf79ba00FR+ZYpqrYs5ZxIIzdU3VGlolOVDA+i2r50QFuLx73qOyrnenVVEw==";
            byte[] decrypt = decrypt(Base64.decodeBase64(cipher), EncryptAlgorithm.RSA, privateKey);
            String s = new String(decrypt, "utf-8");
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
