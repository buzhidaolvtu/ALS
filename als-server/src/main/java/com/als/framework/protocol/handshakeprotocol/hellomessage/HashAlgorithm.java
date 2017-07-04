package com.als.framework.protocol.handshakeprotocol.hellomessage;

/**
 * Created by lvtu on 2017/7/4.
 */
public enum HashAlgorithm {
    none(0), md5(1), sha1(2), sha224(3), sha256(4), sha384(5),
    sha512(6), other(255);
    private int code;

    HashAlgorithm(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
