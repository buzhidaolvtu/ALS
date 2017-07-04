package com.als.framework.protocol.handshakeprotocol.hellomessage;

/**
 * Created by lvtu on 2017/7/4.
 */
public class Extension{
    ExtensionType extension_type;
    byte[] extension_data;//<0..2^16-1>;
}
