package com.als.framework.protocol.alertprotocol;

/**
 * Created by lvtu on 2017/7/3.
 */
public enum AlertLevel {
    warning(1), fatal(2), other(255);

    private int code;

    AlertLevel(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
