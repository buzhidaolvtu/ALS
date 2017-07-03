package com.als.framework.protocol.bean;

/**
 * Created by lvtu on 2017/6/27.
 */
public class ClientHello {
    String client_version;
    String random;
    String session_id;

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

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    @Override
    public String toString() {
        return "ClientHello{" +
                "client_version='" + client_version + '\'' +
                ", random='" + random + '\'' +
                ", session_id='" + session_id + '\'' +
                '}';
    }
}
