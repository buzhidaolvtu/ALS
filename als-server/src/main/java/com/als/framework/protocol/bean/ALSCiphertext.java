package com.als.framework.protocol.bean;

/**
 * Created by lvtu on 2017/6/28.
 */
public class ALSCiphertext {

    private String version;
    private String cipher_data;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCipher_data() {
        return cipher_data;
    }

    public void setCipher_data(String cipher_data) {
        this.cipher_data = cipher_data;
    }
}
