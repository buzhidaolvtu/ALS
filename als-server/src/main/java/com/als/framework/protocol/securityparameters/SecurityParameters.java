package com.als.framework.protocol.securityparameters;

import com.als.framework.protocol.BulkCipherAlgorithm;
import com.als.framework.protocol.ConnectionEnd;
import com.als.framework.protocol.PRFAlgorithm;
import com.als.framework.protocol.handshakeprotocol.hellomessage.CompressionMethod;
import com.als.framework.protocol.recordprotocol.CipherType;
import com.als.framework.protocol.recordprotocol.MACAlgorithm;

/**
 * Created by lvtu on 2017/7/4.
 */
public class SecurityParameters {
    ConnectionEnd entity;
    PRFAlgorithm prf_algorithm;
    BulkCipherAlgorithm bulk_cipher_algorithm;
    CipherType cipher_type;
    int enc_key_length;
    int block_length;
    int fixed_iv_length;
    int record_iv_length;
    MACAlgorithm mac_algorithm;
    int mac_length;
    int mac_key_length;
    CompressionMethod compression_algorithm;
//    opaque master_secret[48];
//    opaque client_random[32];
//    opaque server_random[32];
}
