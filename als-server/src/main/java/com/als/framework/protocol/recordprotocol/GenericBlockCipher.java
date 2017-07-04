package com.als.framework.protocol.recordprotocol;

/**
 * Created by lvtu on 2017/7/3.
 */
public class GenericBlockCipher extends AbstractCipher {
    static class BlockCiphered{
        byte[] content;//[TLSCompressed.length];
        byte[] MAC;//[SecurityParameters.mac_length];
        int padding;//[GenericBlockCipher.padding_length];
        int padding_length;
    }


    byte[] IV;//SecurityParameters.record_iv_length
    BlockCiphered blockCiphered;


}

