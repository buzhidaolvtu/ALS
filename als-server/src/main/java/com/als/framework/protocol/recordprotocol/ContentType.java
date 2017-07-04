package com.als.framework.protocol.recordprotocol;

/**
 * Created by lvtu on 2017/7/3.
 */
public enum ContentType {
    change_cipher_spec(20),
    alert(21),
    handshake(22),
    application_data(23),
    other(255);

    private int code;

    ContentType(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
