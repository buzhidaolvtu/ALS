package com.als.framework.tools;

import java.security.SecureRandom;

/**
 * Created by lvtu on 2017/6/27.
 */
public class RandomUtils {
    /**
     * length是指生成的随机字节数的长度,不是指返回值的长度。<br>
     *
     * <b color=red>这个方法会block线程,不要轻易使用.</b>
     * @param lengthOfBytesWithoutBase64
     * @return 经过base64编码的字符串
     */
    private static final SecureRandom secureRandom = new SecureRandom();
    public static String secureRandomString(int lengthOfBytesWithoutBase64) {
        byte bytes[] = new byte[lengthOfBytesWithoutBase64];
        secureRandom.nextBytes(bytes);
        return org.apache.commons.codec.binary.Base64.encodeBase64String(bytes);
    }
}
