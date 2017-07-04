package com.als.framework.protocol.handshakeprotocol.clientkeyexchange;

/**
 * Created by lvtu on 2017/6/27.
 */
public class ALSPreMasterSecret {
    String client_version;
    String random;

    public String getClient_version() {
        return client_version;
    }

    public void setClient_version(String client_version) {
        this.client_version = client_version;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    @Override
    public String toString() {
        return "PreMasterSecret{" +
                "client_version='" + client_version + '\'' +
                ", random='" + random + '\'' +
                '}';
    }
}
