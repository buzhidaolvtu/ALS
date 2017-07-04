package com.als.framework.protocol.handshakeprotocol.hellomessage;

import com.als.framework.protocol.ciphersuite.CipherSuite;
import com.als.framework.protocol.handshakeprotocol.AbstractHandshakeTypeMessage;
import com.als.framework.protocol.recordprotocol.ProtocolVersion;

/**
 * Created by lvtu on 2017/7/4.
 */
public class ServerHello extends AbstractHandshakeTypeMessage {
    ProtocolVersion server_version;
    Random random;
    //    SessionID session_id;
    byte[] session_id;//
    CipherSuite cipher_suite;
    CompressionMethod compression_method;
    //    select (extensions_present) {
//        case false:
//        struct {};
//        case true:
//        Extension extensions<0..2^16-1>;
//    };
    Extension[] extensions;
}

