package com.als.framework.tools;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

import static com.als.framework.tools.MACUtils.MACAlgorithm.HmacSHA256;

/**
 * Created by lvtu on 2017/6/27.
 */
public class MACUtils {

    public enum MACAlgorithm {
        HmacMD5("HmacMD5"),
        HmacSHA1("HmacSHA1"),
        HmacSHA224("HmacSHA224"),
        HmacSHA256("HmacSHA256"),
        HmacSHA384("HmacSHA384"),
        HmacSHA512("HmacSHA512");

        private String algorithmName;

        MACAlgorithm(String algorithmName) {
            this.algorithmName = algorithmName;
        }

        public String getAlgorithmName() {
            return algorithmName;
        }
    }

    public static String hmac(String key, String... data){
        return hmac(HmacSHA256,key,data);
    }

    public static String hmac(MACAlgorithm macAlgorithm, String key, String... data) {
        if (data == null || data.length == 0) {
            throw new RuntimeException("no data");
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            sb.append(data[i]);
        }
        try {
            Mac mac = Mac.getInstance(macAlgorithm.getAlgorithmName());
            SecretKeySpec spec = new SecretKeySpec(key.getBytes(),macAlgorithm.getAlgorithmName());
            mac.init(spec);
            byte[] bytes = mac.doFinal(sb.toString().getBytes());
            return Base64.encodeBase64String(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
//        byte[] bytes = "我".getBytes(Charset.forName("utf-8"));
//        System.out.println(bytes);

//        String decode = URLDecoder.decode("%E6%88%91", "UTF-8");
//        int i = Integer.parseInt("E6", 16);
//        System.out.println(decode);
//        byte b = (byte)i;
//        System.out.println(i);
//        System.out.println(b);

        byte[] a = {1, 0, 2};
        String s = Base64.encodeBase64String(a);
        System.out.println(s);
    }
}
