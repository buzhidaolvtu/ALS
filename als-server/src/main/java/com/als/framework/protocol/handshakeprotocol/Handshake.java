package com.als.framework.protocol.handshakeprotocol;

/**
 * Created by lvtu on 2017/7/3.
 */
public class Handshake {
    HandshakeType msg_type;
    int length;
    AbstractHandshakeTypeMessage body;
}
