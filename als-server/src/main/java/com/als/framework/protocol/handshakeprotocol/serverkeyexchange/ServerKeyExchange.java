package com.als.framework.protocol.handshakeprotocol.serverkeyexchange;

import com.als.framework.protocol.handshakeprotocol.AbstractHandshakeTypeMessage;

/**
 * Created by lvtu on 2017/7/4.
 */
public class ServerKeyExchange extends AbstractHandshakeTypeMessage {
//    select (KeyExchangeAlgorithm) {
//        case dh_anon:
//        ServerDHParams params;
//        case dhe_dss:
//        case dhe_rsa:
//        ServerDHParams params;
//        digitally-signed struct {
//            opaque client_random[32];
//            opaque server_random[32];
//            ServerDHParams params;
//        } signed_params;
//        case rsa:
//        case dh_dss:
//        case dh_rsa:
//        struct {} ;
/* message is omitted for rsa, dh_dss, and dh_rsa */
/* may be extended, e.g., for ECDH -- see [TLSECC] */
}
