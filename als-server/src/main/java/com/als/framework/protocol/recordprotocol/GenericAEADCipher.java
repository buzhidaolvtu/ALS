package com.als.framework.protocol.recordprotocol;

/**
 * Created by lvtu on 2017/7/3.
 */
public class GenericAEADCipher extends AbstractCipher {
    static class AeadCiphered {
        byte[] content;//[TLSCompressed.length];
    }

    byte[] nonce_explicit;//SecurityParameters.record_iv_length
    AeadCiphered aeadCiphered;
}
