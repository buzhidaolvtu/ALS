package com.als.framework.protocol.handshakeprotocol;

/**
 * Created by lvtu on 2017/6/28.
 */
public class GenericBlockCipher {
    private String data;
    private String mac;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public String toString() {
        return "GenericBlockCipher{" +
                "data='" + data + '\'' +
                ", mac='" + mac + '\'' +
                '}';
    }
}
