package com.als.framework.protocol.bean;

/**
 * Created by lvtu on 2017/6/27.
 */
public class ClientKeyExchange {

    EncryptedPreMasterSecret exchange_keys;

    public EncryptedPreMasterSecret getExchange_keys() {
        return exchange_keys;
    }

    public void setExchange_keys(EncryptedPreMasterSecret exchange_keys) {
        this.exchange_keys = exchange_keys;
    }

    @Override
    public String toString() {
        return "ClientKeyExchange{" +
                "exchange_keys=" + exchange_keys +
                '}';
    }
}
