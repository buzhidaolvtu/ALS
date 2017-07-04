package com.als.framework.protocol.handshakeprotocol.hellomessage;

/**
 * Created by lvtu on 2017/6/27.
 */
public class ALSServerHello {
    String server_version = "1.0";
    String random;
    String session_id;

    public String getServer_version() {
        return server_version;
    }

    public void setServer_version(String server_version) {
        this.server_version = server_version;
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
        return "ServerHello{" +
                "server_version='" + server_version + '\'' +
                ", random='" + random + '\'' +
                ", session_id='" + session_id + '\'' +
                '}';
    }
}
