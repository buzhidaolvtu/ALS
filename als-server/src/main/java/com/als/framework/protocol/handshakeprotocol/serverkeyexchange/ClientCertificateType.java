package com.als.framework.protocol.handshakeprotocol.serverkeyexchange;

/**
 * Created by lvtu on 2017/7/4.
 */
public enum ClientCertificateType {
    rsa_sign(1), dss_sign(2), rsa_fixed_dh(3), dss_fixed_dh(4),
    rsa_ephemeral_dh_RESERVED(5), dss_ephemeral_dh_RESERVED(6),
    fortezza_dms_RESERVED(20),
    other(255);

    private int code;

    ClientCertificateType(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}



