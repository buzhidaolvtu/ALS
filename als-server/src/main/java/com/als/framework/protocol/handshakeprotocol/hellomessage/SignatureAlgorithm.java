package com.als.framework.protocol.handshakeprotocol.hellomessage;

/**
 * Created by lvtu on 2017/7/4.
 */
public enum SignatureAlgorithm {
    anonymous(0), rsa(1), dsa(2), ecdsa(3), other(255);
    private int code;

    SignatureAlgorithm(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
