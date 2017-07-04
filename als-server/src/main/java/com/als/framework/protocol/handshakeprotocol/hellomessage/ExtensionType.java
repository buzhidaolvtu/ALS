package com.als.framework.protocol.handshakeprotocol.hellomessage;

/**
 * Created by lvtu on 2017/7/4.
 */
public enum ExtensionType {

    signature_algorithms(13),
    other(65535);

    private int code;

    ExtensionType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
