package com.als.framework.protocol.handshakeprotocol;

/**
 * Created by lvtu on 2017/7/3.
 */
public enum HandshakeType {
    hello_request(0), client_hello(1), server_hello(2),
    certificate(11), server_key_exchange(12),
    certificate_request(13), server_hello_done(14),
    certificate_verify(15), client_key_exchange(16),
    finished(20),
    other(255);

    private int code;

    HandshakeType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
