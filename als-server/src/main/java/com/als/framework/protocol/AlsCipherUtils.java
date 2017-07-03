package com.als.framework.protocol;

import com.als.framework.protocol.bean.ALSCiphertext;
import com.als.framework.protocol.bean.GenericBlockCipher;
import com.als.framework.tools.EncryptUtils;
import com.als.framework.tools.MACUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;

/**
 * Created by lvtu on 2017/6/29.
 */
public class AlsCipherUtils {

    private final static ObjectMapper om = new ObjectMapper();

    public static ALSCiphertext encrypt(String secret, String plainText) {
        try {
            GenericBlockCipher gbc = new GenericBlockCipher();
            gbc.setData(plainText);
            gbc.setMac(MACUtils.hmac(secret, plainText));

            String text = om.writeValueAsString(gbc);
            String cipher_data = Base64.encodeBase64String(EncryptUtils.AESEncrypt(secret, EncryptUtils.enpadding(text.getBytes(), (byte) 16)));

            ALSCiphertext alsCiphertext = new ALSCiphertext();
            alsCiphertext.setCipher_data(cipher_data);
            return alsCiphertext;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static GenericBlockCipher decrypt(String secret, ALSCiphertext alsCiphertext) {
        try {
            String cipher_data = alsCiphertext.getCipher_data();
            byte[] bytes = EncryptUtils.depadding16(EncryptUtils.AESDecrypt(secret, Base64.decodeBase64(cipher_data)));
            GenericBlockCipher genericBlockCipher = om.readValue(bytes, GenericBlockCipher.class);
            return genericBlockCipher;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}