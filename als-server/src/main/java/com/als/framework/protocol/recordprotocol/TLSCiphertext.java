package com.als.framework.protocol.recordprotocol;

/**
 * Created by lvtu on 2017/7/3.
 */
public class TLSCiphertext {
    ContentType type;
    ProtocolVersion version;
    int length;
    AbstractCipher fragment;
}
