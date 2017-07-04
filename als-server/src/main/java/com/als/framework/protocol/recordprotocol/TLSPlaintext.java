package com.als.framework.protocol.recordprotocol;

/**
 * Created by lvtu on 2017/7/3.
 */
public class TLSPlaintext {
    ContentType type;
    ProtocolVersion version;
    int length;//TLSPlaintext.length
    byte[] fragment;

}
