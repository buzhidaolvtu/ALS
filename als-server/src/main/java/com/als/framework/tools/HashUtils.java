package com.als.framework.tools;

import org.apache.commons.codec.binary.Base64;

import java.security.MessageDigest;

/**
 * Created by lvtu on 2017/6/27.
 */
public class HashUtils {

    public enum HashAlgorithm {
        MD5("MD5"),
        SHA_1("SHA-1"),
        SHA_224("SHA-224"),
        SHA_256("SHA-256"),
        SHA_384("SHA-384"),
        SHA_512("SHA-512");

        private String algorithmName;

        HashAlgorithm(String algorithmName) {
            this.algorithmName = algorithmName;
        }

        public String getAlgorithmName() {
            return algorithmName;
        }
    }

    public static String hash(HashAlgorithm hashAlgorithm, String... data) {
        if (data == null || data.length == 0) {
            throw new RuntimeException("no data");
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<data.length;i++){
            sb.append(data[i]);
        }
        return hash(sb.toString().getBytes(),hashAlgorithm);
    }

    public static String hash(byte[] data, HashAlgorithm algorithm) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm.getAlgorithmName());
            messageDigest.update(data);
            byte[] digest = messageDigest.digest();
            return Base64.encodeBase64String(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String hash = hash(HashAlgorithm.SHA_256, "abc", "def", "client finished");
        System.out.println(hash);
    }
}
