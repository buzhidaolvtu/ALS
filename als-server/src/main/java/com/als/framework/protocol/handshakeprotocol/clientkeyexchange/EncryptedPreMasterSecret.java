package com.als.framework.protocol.handshakeprotocol.clientkeyexchange;

/**
 * Created by lvtu on 2017/6/27.
 */
public class EncryptedPreMasterSecret {
    String encrypted_pre_master_secret;

    public String getEncrypted_pre_master_secret() {
        return encrypted_pre_master_secret;
    }

    public void setEncrypted_pre_master_secret(String encrypted_pre_master_secret) {
        this.encrypted_pre_master_secret = encrypted_pre_master_secret;
    }

    @Override
    public String toString() {
        return "EncryptedPreMasterSecret{" +
                "encrypted_pre_master_secret='" + encrypted_pre_master_secret + '\'' +
                '}';
    }
}
