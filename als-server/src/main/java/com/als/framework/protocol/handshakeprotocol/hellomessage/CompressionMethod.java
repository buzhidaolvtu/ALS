package com.als.framework.protocol.handshakeprotocol.hellomessage;

/**
 * Created by lvtu on 2017/7/3.
 */
public enum  CompressionMethod {
    Null(0),
    other(255);

    private int code;

    CompressionMethod(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

