package com.als.framework.protocol.recordprotocol;

/**
 * Created by lvtu on 2017/7/3.
 */
public class TLSCompressed {
    ContentType type;
    ProtocolVersion version;
    int length;//TLSCompressed.length
    byte[] fragment;
}
