package com.als.framework.protocol.changecipherspecprotocol;

/**
 * Created by lvtu on 2017/7/3.
 */
public enum ChangeCipherSpecType {
    change_cipher_spec(1),
    other(255);

    private int code;

    ChangeCipherSpecType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
