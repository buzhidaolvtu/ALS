package com.als.framework.protocol.handshakeprotocol.hellomessage;

import com.als.framework.protocol.ciphersuite.CipherSuite;
import com.als.framework.protocol.recordprotocol.ProtocolVersion;

/**
 * Created by lvtu on 2017/7/4.
 */
public class ClientHello {
    ProtocolVersion client_version;
    Random random;

//    SessionID session_id;
    byte[] session_id;//<0..32>
    CipherSuite cipher_suites;//<2..2^16-2>;
    CompressionMethod compression_methods;//<1..2^8-1>;

//    select (extensions_present) {
//        case false:
//        struct {};
//        case true:
//        Extension extensions<0..2^16-1>;
//    };
    Extension[] extensions;
}
